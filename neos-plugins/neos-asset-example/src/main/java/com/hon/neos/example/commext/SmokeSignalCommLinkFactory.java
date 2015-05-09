package com.hon.neos.example.commext;

import com.hon.neos.core.ccomm.l1.CommLinkFactory;

/**
 * Class SmokeSignalCommLinkFactory
 * @author Chris Black
 *
 */
public class SmokeSignalCommLinkFactory
	extends CommLinkFactory<SmokeSignalCommLink>
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private static final String PRODUCT_NAME = "Smoke Signal CommLink";
	private static final String PRODUCT_DESC =
		"This is an extension for smoke signal-based communications.";
	private static SmokeSignalCommLinkFactory instance = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Private Default Constructor
	 */
	private SmokeSignalCommLinkFactory()
	{
		super();
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Static factory method
	 * @return the singleton instance of this factory
	 */
	public static SmokeSignalCommLinkFactory getInstance()
	{
		if( instance == null )
		{
			instance = new SmokeSignalCommLinkFactory();
		}
		return instance;
	}

	@Override
	public SmokeSignalCommLink createCommLink()
	{
		return new SmokeSignalCommLink();
	}

	@Override
	public String getProductDescription()
	{
		// TODO Auto-generated method stub
		return PRODUCT_DESC;
	}

	@Override
	public String getProductName()
	{
		return PRODUCT_NAME;
	}

	@Override
	public Class<SmokeSignalCommLink> getProductType()
	{
		return SmokeSignalCommLink.class;
	}
	
}