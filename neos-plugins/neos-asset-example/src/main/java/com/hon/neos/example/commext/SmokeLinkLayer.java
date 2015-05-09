package com.hon.neos.example.commext;

import java.io.File;
import java.nio.ByteBuffer;

import com.hon.neos.core.ccomm.Address;
import com.hon.neos.core.ccomm.CCommException;
import com.hon.neos.core.ccomm.l2.AbstractLinkLayer;
import com.hon.neos.core.ccomm.l2.BaseLinkFrame;
import com.hon.neos.core.ccomm.l2.LinkFrame;

/**
 * This class implements a very simple framing link layer protocol built on a
 * {@link SmokeSignalCommLink}. It provides for framing (in this case, payload
 * only) which <i>could</i> include a start flag sequence, address field,
 * payload length, the payload, and a CRC.
 * 
 * This LinkLayer uses the BaseLinkFrame as its framing object since
 * the contents are the payload only.
 * 
 * @author Chris Black
 */
public class SmokeLinkLayer extends AbstractLinkLayer
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private int timeoutMs = 1000;
	private int retryTimes = 3;
	private LinkStatus status = LinkStatus.LOST;
	private int mtu = 1500;
	
	private CommLinkMgr linkManager = null;
	private Thread receiveThread = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	public SmokeLinkLayer()
	{
		super();
		initialize( null );	// No config file used in this example
	}

	public SmokeLinkLayer( int mtu )
	{
		super();
		this.mtu = mtu;
		initialize( null );	// No config file used in this example
	}

	public SmokeLinkLayer( int mtu, int timeout )
	{
		super();
		this.mtu = mtu;
		this.timeoutMs = timeout;
		initialize( null );	// No config file used in this example
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	@Override
	public void activate()
	{
		if( myCL.connect() )
		{
			status = LinkStatus.OK;
			receiveThread = new Thread( linkManager );
			receiveThread.run();
			fireActivated();
		}
	}

	@Override
	public void deactivate()
	{
		if( myCL.disconnect() )
		{
			status = LinkStatus.LOST;
			try
			{
				receiveThread.join( 5000 );
			}
			catch ( InterruptedException e )
			{
				System.out.println( "Exception while ending thread: "
						+ e.getMessage() );
			}
			fireDeactivated();
		}
	}

	@Override
	public LinkStatus getLinkStatus()
	{
		return status;
	}

	@Override
	public int getMTU()
	{
		return mtu;
	}

	@Override
	public void initialize( File dir )
	{
		status = LinkStatus.LOST;
		myCL = new SmokeSignalCommLink();
		linkManager = new CommLinkMgr();
	}

	@Override
	public boolean isActivated()
	{
		return myCL.isConnected();
	}

	@Override
	public int send( LinkFrame frame, Address addr )
			throws CCommException
	{
		int bytesSent = -1;
		for( int tries = 0; tries < retryTimes && bytesSent == -1; tries++ )
		{
			bytesSent = myCL.send( frame.getPayload() );
		}
		fireDataSent( frame, addr );
		return bytesSent;
	}

	@Override
	public void setRetry( int r )
	{
		this.retryTimes = r;
	}

	@Override
	public boolean supportAddressing()
	{
		return false;
	}

	///////////////////////////////////////////////////////////////////////////
	// Private Methods
	///////////////////////////////////////////////////////////////////////////
	private void process( ByteBuffer buffer )
	{
		// Parse and deal with incoming data. For this ultra-simple example,
		// we're just grabbing the whole incoming buffer and calling it good.
		String incoming = new String( buffer.array() );
		System.out.println( "LinkLayer received " + incoming );
		LinkFrame frame = new BaseLinkFrame();
		frame.setPayload( buffer );
		fireDataReceived( frame, null );
	}

	///////////////////////////////////////////////////////////////////////////
	// Private Inner Classes
	///////////////////////////////////////////////////////////////////////////
	private class CommLinkMgr implements Runnable
	{
		/**
		 * threaded method
		 */
		public void run()
		{
			ByteBuffer buffer;
			
			while( isActivated() )
			{
				buffer = myCL.read( timeoutMs );
				
				process( buffer );
				
			} //end while( activated
			return;
		} //end run
	}
}
