/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import com.hon.neos.core.util.Logging;

public class TrippLiteSettings extends Properties
{
	private static final long serialVersionUID = 8726707276797037286L;
	private static final String KEY_HOST = "HostName";
	private static final String KEY_PORT = "Port";
	private static final String KEY_READ = "ReadCommunity";
	private static final String KEY_WRITE = "WriteCommunity";
	
	public TrippLiteSettings()
	{
		super();
		setDefaults();
	}

	public void setHost( String host )
	{
		put( KEY_HOST, host );
	}

	public String getHost()
	{
		return (String) get( KEY_HOST );
	}

	public void setPort( int port )
	{
		put( KEY_PORT, String.valueOf( port ) );
	}

	public int getPort()
	{
		return Integer.parseInt( (String) get(KEY_PORT) );
	}

	public String getReadCommunity()
	{
		return (String)get(KEY_READ);
	}
	public void setReadCommunity( String x )
	{
		put( KEY_READ, x );
	}
	public String getWriteCommunity()
	{
		return (String)get(KEY_WRITE);
	}
	public void setWriteCommunity( String x )
	{
		put( KEY_WRITE, x );
	}
	
	
	/**
	 * saves current settings for a file in XML
	 * @param settingsFile - file to save to
	 */
	public void save( File settingsFile )
	{
		FileOutputStream os = null;
		String comment = String.format( "AP79XX Asset Settings, %s",
				Calendar.getInstance().getTime().toString() );
		try
		{
			os = new FileOutputStream( settingsFile );
			storeToXML( os, comment );
		}
		catch( Exception e )
		{
			Logging.getLogLog().log( Level.INFO, "Error saving settings: " + e );
		}
	} //end save

	/**
	 * reads in settings from a passed file
	 * @param settingsFile
	 */
	public void read( File settingsFile )
	{
		try
		{
			loadFromXML( new FileInputStream( settingsFile ) );
		}
		catch( Exception e )
		{
			Logging.getLogLog().log( Level.INFO, "Error reading settings: " + e );
		}
		
		return;
	} //end read

	private void setDefaults()
	{
		setHost("127.0.0.1");
		setPort(161);
		setReadCommunity("public");
		setWriteCommunity("private");
		
		return;
	} //end setDefaults
	
} //end class AP79XXSettings
