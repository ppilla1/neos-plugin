/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */
package com.hon.neos.tripplite.shell;

import java.io.PrintStream;

import com.hon.neos.core.asset.AssetDirectoryService;
import com.hon.neos.core.platform.Platform;
import com.hon.neos.core.shell.AbstractCommand;
import com.hon.neos.tripplite.TrippLiteAsset;
import com.hon.neos.tripplite.TrippLiteSettings;

/**
 * this command will list out the status for all CSR links
 * 
 * @author Brian Adams
 *
 */
public class Add extends AbstractCommand
{
	
	public Add()
	{
		super();
		setToken("add");
	}

	@Override
	public int execute( String[] args, PrintStream ps )
	{
		AssetDirectoryService ads = (AssetDirectoryService)Platform.getService( AssetDirectoryService.SERVICE_ASSET_DIRECTORY);
		TrippLiteAsset a;
		TrippLiteSettings as;
		
		if( args.length == 6 )
		{
			a = (TrippLiteAsset)ads.addAsset( TrippLiteAsset.class );
			if( a != null )
			{
				a.setName( args[1] );
				
				as = a.getSettings();
				as.setHost( args[2] );
				as.setPort( Integer.parseInt(args[3]) );
				as.setReadCommunity( args[4] );
				as.setWriteCommunity( args[5] );
				
				a.setSettings( as );
			}
			else
			{
				ps.println("ERROR CREATING AP79XX Asset");
			}
			
		}
		else
		{
			ps.println("Syntax error");
			help(0, ps, null);
		}
		
		return 0;
	} //end execute

	@Override
	public void help(int level, PrintStream ps, String[] args)
	{
		indent(level, ps);
		ps.print("Usage: add [name] [host] [port] [readCommunity] [writeCommunity]\n");
		indent(level, ps);
		ps.print("name - free form name for asset\n");
		indent(level, ps);
		ps.print("host - hostname of server\n");
		indent(level, ps);
		ps.print("port - port use\n");
		indent(level, ps);
		ps.print("readCommunity - SNMP Read Community Name\n");
		indent(level, ps);
		ps.print("writeCommunity - SNMP Write Community Name\n");
		
		return;
	}
	
} //end class CMDCsr
