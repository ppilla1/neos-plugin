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
public class Status extends AbstractCommand
{
	
	public Status()
	{
		super();
		setToken("status");
	}

	@Override
	public int execute( String[] args, PrintStream ps )
	{
		AssetDirectoryService ads = (AssetDirectoryService)Platform.getService( AssetDirectoryService.SERVICE_ASSET_DIRECTORY);
		
		for( Asset a : ads.getAssetsByType( TrippLiteAsset.class ))
		{
			printStatus( (TrippLiteAsset)a, ps );
		}
		
		return 0;
	} //end execute

	private void printStatus( TrippLiteAsset c, PrintStream ps )
	{
		ps.println("Name: " + c.getName() );
		ps.println("\tHost: " + c.getSettings().getHost() );
		ps.println("\tPort: " + c.getSettings().getPort() );
		ps.println("\tRead: " + c.getSettings().getReadCommunity() );
		ps.println("\tWrite: " + c.getSettings().getWriteCommunity() );
		ps.println("\tStatus: " + c.getHealthStatus() + " - " + c.getHealthStatusDescription() );
		
		
		return;
	} //end printStatus
	
	

	@Override
	public void help(int level, PrintStream ps, String[] args)
	{
		indent(level, ps);
		ps.print("shows the status of all TrippLite Assets\n");
		
		return;
	}
	
} //end class CMDCsr
