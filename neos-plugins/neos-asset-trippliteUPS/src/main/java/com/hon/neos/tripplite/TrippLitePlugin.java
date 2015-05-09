/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */
package com.hon.neos.tripplite;



import com.hon.neos.core.plugin.AbstractPlugIn;
import com.hon.neos.core.plugin.PlugInInfo;
import com.hon.neos.core.shell.Command;

/**
 * @author e233922
 *
 */
public class TrippLitePlugin extends AbstractPlugIn
{
	private TrippLiteAssetFactory trippLiteAssetFactory = null;

	/**
	 * @param plugin
	 */
	public TrippLitePlugin( PlugInInfo plugin )
	{
		super( plugin );
	}

	/**
	 * @return The GpsAssetType, instantiate it if necessary.
	 */
	public synchronized TrippLiteAssetFactory getTrippLiteAssetFactory()
	{
		if( this.trippLiteAssetFactory == null )
		{
			trippLiteAssetFactory = new TrippLiteAssetFactory();
		}
		return this.trippLiteAssetFactory;
	} //end getGpsAssetFactory
	
	public Command getTrippLiteCommand()
	{
		return new CMDTripLite();
	}
	
} //end class TrippLitePlugin
