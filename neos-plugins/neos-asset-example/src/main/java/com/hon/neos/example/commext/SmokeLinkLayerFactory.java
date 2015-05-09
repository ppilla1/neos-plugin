package com.hon.neos.example.commext;

import com.hon.neos.core.ccomm.l2.LinkLayerFactory;

public class SmokeLinkLayerFactory extends LinkLayerFactory<SmokeLinkLayer>
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private static final String PRODUCT_NAME = "Smoke Signal Link Layer";
	private static final String PRODUCT_DESC =
		"This is a link layer extension for smoke signal-based communications.";
	private static SmokeLinkLayerFactory instance = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Private Default Constructor
	 */
	private SmokeLinkLayerFactory()
	{
		super();
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Get the singleton instance of the SmokeLinkLayerFactory
	 * @return
	 */
	public static SmokeLinkLayerFactory getInstance()
	{
		if( instance == null )
		{
			instance = new SmokeLinkLayerFactory();
		}
		return instance;
	}

	@Override
	public SmokeLinkLayer createLinkLayer( int mtu )
	{
		return new SmokeLinkLayer( mtu );
	}

	@Override
	public SmokeLinkLayer createLinkLayer( int mtu, int timeout )
	{
		return new SmokeLinkLayer( mtu, timeout );
	}

	@Override
	public String getProductDescription()
	{
		return PRODUCT_DESC;
	}

	@Override
	public String getProductName()
	{
		return PRODUCT_NAME;
	}

	@Override
	public Class<SmokeLinkLayer> getProductType()
	{
		return SmokeLinkLayer.class;
	}
}
