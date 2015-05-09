package com.hon.neos.example.commext;

import java.nio.ByteBuffer;

import com.hon.neos.core.ccomm.l1.AbstractCommLink;

public class SmokeSignalCommLink extends AbstractCommLink
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private boolean connected = false;

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	@Override
	public boolean connect()
	{
		connected = true;
		System.out.println( "Smoke signal connected" );
		return true;
	}

	@Override
	public boolean disconnect()
	{
		connected = false;
		System.out.println( "Smoke signal disconnected" );
		return true;
	}

	@Override
	public boolean isConnected()
	{
		return connected;
	}

	@Override
	public ByteBuffer read()
	{
		ByteBuffer buffer = null;
		String incomingMsg = "<Bytes from smoke signal HW interface>";
		byte[] bytes = incomingMsg.getBytes();
		buffer = ByteBuffer.wrap( bytes );
		System.out.println( "Read '" + incomingMsg + "' via smoke signal" );
		return buffer;
	}

	@Override
	public ByteBuffer read( int timeoutMs )
	{
		ByteBuffer buffer = null;
		String incomingMsg =
			"<Bytes from smoke signal HW interface, with timeout>";
		byte[] bytes = incomingMsg.getBytes();
		buffer = ByteBuffer.wrap( bytes );
		System.out.println( "Read '" + incomingMsg + "' via smoke signal" );
		return buffer;
	}

	@Override
	public int send( ByteBuffer buffer )
	{
		System.out.println( "<Bytes sent over smoke signal HW interface>" );
		// Do what is needed to send the buffer contents over the HW interface.
		// Return the number of bytes actually sent.
		return buffer.remaining();
	}
}
