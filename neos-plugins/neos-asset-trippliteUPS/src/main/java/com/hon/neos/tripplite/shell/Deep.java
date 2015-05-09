/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */
package com.hon.neos.tripplite.shell;

import java.io.PrintStream;

import com.hon.neos.core.asset.Asset;
import com.hon.neos.core.asset.AssetDirectoryService;
import com.hon.neos.core.platform.Platform;
import com.hon.neos.core.shell.AbstractCommand;
import com.hon.neos.tripplite.TrippLiteAsset;

/**
 * this command will list out the status for all GPS links
 * 
 * @author Brian Adams
 *
 */
public class Deep extends AbstractCommand
{
	
	public Deep()
	{
		super();
		setToken("deep");
	}

	@Override
	public int execute( String[] args, PrintStream ps )
	{
		AssetDirectoryService ads = (AssetDirectoryService)Platform.getService( AssetDirectoryService.SERVICE_ASSET_DIRECTORY);
		
		for( Asset a : ads.getAssetsByType( TrippLiteAsset.class ))
		{
			if( args.length == 2 )
			{
				if( a.getName().equals( args[1] ) )
				{
					((TrippLiteAsset)a).deep( ps );
				}
			}
			else
			{
				((TrippLiteAsset)a).deep( ps );
			}
		}
		
		return 0;
	} //end execute


	@Override
	public void help(int level, PrintStream ps, String[] args)
	{
		indent(level, ps);
		ps.print("shows the deep status of a TrippLite assets\n");
		indent(level, ps);
		ps.print("Usage: deep {name}\n");
		
		return;
	}
	
} //end class CMDCsr
