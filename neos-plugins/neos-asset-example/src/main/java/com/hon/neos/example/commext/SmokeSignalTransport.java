package com.hon.neos.example.commext;

import java.nio.ByteBuffer;

import com.hon.neos.core.ccomm.Address;
import com.hon.neos.core.ccomm.CCommException;
import com.hon.neos.core.ccomm.l2.BaseLinkFrame;
import com.hon.neos.core.ccomm.l2.LinkFrame;
import com.hon.neos.core.ccomm.l2.LinkLayer;
import com.hon.neos.core.ccomm.l2.LinkLayerListener;
import com.hon.neos.core.ccomm.transport.AbstractTransport;
/**
 * Class SmokeSignalTransport is an ultra-simple example of extension of a
 * transport layer. Within a transport layer implementation, more advanced
 * handling of link layer traffic may be done, such as dealing with multi-
 * packet fragmentation and sequencing.
 *  
 * @author Chris Black
 *
 */
public class SmokeSignalTransport extends AbstractTransport
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private LinkLayer linkLayer = null;
	private LinkLayerHandler handler = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	public SmokeSignalTransport( LinkLayer linkLayer )
	{
		super();
		handler = new LinkLayerHandler();
		this.linkLayer = linkLayer;
		this.linkLayer.addLinkLayerListener( handler );
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	@Override
	public LinkLayer getLinkLayer()
	{
		return linkLayer;
	}

	@Override
	public int send( ByteBuffer data, Address addr ) throws CCommException
	{
		BaseLinkFrame frame = new BaseLinkFrame();
		frame.setPayload( data );
		System.out.print( "Telling link layer to send " );
		System.out.println( new String( data.array() ) );
		return linkLayer.send(frame, addr);
	}

	///////////////////////////////////////////////////////////////////////////
	// Private Inner Classes
	///////////////////////////////////////////////////////////////////////////
	private class LinkLayerHandler implements LinkLayerListener
	{

		@Override
		public void activated( LinkLayer link )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dataReceived( Address addr, LinkFrame frame )
		{
			System.out.print( "Link layer has received new data: " );
			System.out.println( new String( frame.getPayload().array() ) );
		}

		@Override
		public void dataSent( Address addr, LinkFrame frame )
		{
			System.out.print( "Link layer has sent " );
			System.out.println( new String( frame.getPayload().array() ) );
		}

		@Override
		public void deactivated( LinkLayer link )
		{
			// TODO Auto-generated method stub
			
		}
		
	}
}
