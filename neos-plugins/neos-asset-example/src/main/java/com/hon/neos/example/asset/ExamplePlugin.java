/**
 * Developed by Honeywell, 2009
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-09-C-0039
 */

package com.hon.neos.example.asset;

import com.hon.neos.core.plugin.AbstractPlugIn;
import com.hon.neos.core.plugin.PlugInInfo;

/**
 * 
 * @author Brian Adams
 *
 */
public class ExamplePlugin extends AbstractPlugIn
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private ExampleAssetFactory assetType = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor
	 * @param info the PlugInInfo for EmidsUIPlugin
	 */
	public ExamplePlugin( PlugInInfo info )
	{
		super( info );
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * @return the Emids Asset Factory, instantiate if necessary.
	 */
	public ExampleAssetFactory getExampleAssetFactory()
	{
		System.out.println("Example Asset Factory is being acquired from Plug-In.");
		if( assetType == null )
		{
			assetType = new ExampleAssetFactory();
		}
		
		return assetType;
	}

}
