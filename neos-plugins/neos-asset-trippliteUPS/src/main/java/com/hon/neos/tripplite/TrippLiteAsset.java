/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hon.neos.core.asset.Asset;
import com.hon.neos.core.ioi.AssetIoi;
import com.hon.neos.core.ioiservice.IoiService;
import com.hon.neos.core.platform.Platform;
import com.hon.neos.core.util.Logging;


/**
 * Class TrippLiteAsset implements a NEOS asset for the standard GPS plugin.
 * 
 * @since v1.0
 * @version v1.0
 * @author Chris Black
 * @maturity Red
 */
public class TrippLiteAsset extends Asset
{
	// /////////////////////////////////////////////////////////////////////////
	// Static Fields
	// /////////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -7041865093365357509L;
	private static final String symbolCode = "sfgpes---------";

	// /////////////////////////////////////////////////////////////////////////
	// Private Fields
	// /////////////////////////////////////////////////////////////////////////
	private TrippLiteSettings settings = null;
	private AtomicBoolean connected;

	private InetAddress snmpAgentIP;
	private Snmp snmp;
	private CommunityTarget readTarget;
	private CommunityTarget writeTarget;
	
	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Default constructor
	 */
	public TrippLiteAsset()
	{
		super();
		init(null);
	}

	public TrippLiteAsset( File settingsFile )
	{
		super();
		init(settingsFile);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Public Methods
	// /////////////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hon.neos.core.asset.AbstractAsset#onActivate()
	 */
	@Override
	protected synchronized boolean onActivate()
	{
		boolean ans = false;
		TransportMapping transport;
		

		if (!connected.get())
		{
			
			try
			{
				//setup snmp client
				transport = new DefaultUdpTransportMapping();
				snmp = new Snmp(transport);
				transport.listen();
				
				//setup reference to host name
				snmpAgentIP = InetAddress.getByName( settings.getHost() );
				
				//setup targets
				Address targetAddress = new UdpAddress( snmpAgentIP, settings.getPort() );
				readTarget = new CommunityTarget();
				readTarget.setCommunity(new OctetString( settings.getReadCommunity() ));
				readTarget.setAddress(targetAddress);
				readTarget.setRetries(2);
				readTarget.setTimeout(1500);
				readTarget.setVersion(SnmpConstants.version1);
				writeTarget = new CommunityTarget();
				writeTarget.setCommunity(new OctetString( settings.getWriteCommunity() ));
				writeTarget.setAddress(targetAddress);
				writeTarget.setRetries(2);
				writeTarget.setTimeout(1500);
				writeTarget.setVersion(SnmpConstants.version1);

				
				//NEOS admin junk
				setAvailable(true);
				connected.set(true);
				setHealthStatus(HealthStatus.GOOD, "Active");
				
				//characterize the PDU
				characterize();
				
			}
			catch (IOException e)
			{
				Logging.getDevLog().log( Level.INFO, "Errror setting up SNMP client: " + e );
				setHealthStatus(HealthStatus.BAD, "INACTIVE");
			}
		}
		else
		{
			// already connected
			Logging.getLogLog().log(Level.INFO, getName() + " Already Connected");
		}

		return ans;
	} // end onActivate

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hon.neos.core.asset.AbstractAsset#onDeactivate()
	 */
	@Override
	protected boolean onDeactivate()
	{
		boolean stat = false;

		if (connected.get())
		{
			try
			{
				snmp.close();
			}
			catch (IOException e)
			{
				Logging.getDevLog().log(Level.INFO, "Error closing SNMP client: " + e );
			}
			
			setAvailable(false);
			stat = deleteSelf();
			connected.set(false);
		}
		else
		{
			stat = true;
		}

		return stat;
	} // end onDeactivate

	public boolean deleteSelf()
	{
		boolean stat;
		setHealthStatus(HealthStatus.GOOD, "Inactive");
		getIoiService().getIoiBlackboard().remove(this);
		stat = true;

		return stat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hon.neos.core.asset.AbstractAsset#getIoi()
	 */
	public AssetIoi getIoi()
	{
		return this;
	}

	@Override
	public boolean isBITSupported()
	{
		return true;
	}

	@Override
	public boolean isDataCaptureSupported()
	{
		return false;
	}

	@Override
	public boolean isDataRecordingSupported()
	{
		return false;
	}

	@Override
	protected List<File> onCaptureData( File storageDir )
	{
		return null;
	}

	@Override
	protected void onPerformBIT()
	{
		boolean stat;
		
		if( connected.get() )
		{
			//ping test
			try
			{
				stat = snmpAgentIP.isReachable( 2000 );
			}
			catch (IOException e)
			{
				Logging.getDevLog().log( Level.INFO, "unable to ping SNMP host " + snmpAgentIP + " Error:" + e );
				stat = false;
			}
			//Logging.getDevLog().log( Level.CONFIG, getName() + " Ping Test = " + stat );
			
			//SNMP query
			if( stat )
			{
				if( queryBatteryPercent() == -1 )
				{
					stat = false;
				}
			}
			
			if( stat )
			{
				setHealthStatus(HealthStatus.GOOD, "BIT Successfull");
				setAvailable( true );
			}
			else
			{
				setHealthStatus(HealthStatus.BAD, "BIT Failed");
				setAvailable( false );
			}
		}
		
		return;
	} //end onPerformBIT

	@Override
	protected void onStartRecording()
	{
	}

	@Override
	protected List<File> onStopRecording( File storageDir )
	{
		return null;
	}

	
	/**
	 * probes the device to get several configuration variables
	 */
	private void characterize()
	{	
		//grab the PDU name
		setName("TrippLite_" + queryName() );
		
		return;
	} //end characterize
	
	

	// /////////////////////////////////////////////////////////////////////////
	// Protected Methods
	// /////////////////////////////////////////////////////////////////////////

	protected static final IoiService getIoiService()
	{
		return (IoiService) Platform.getService(IoiService.SERVICE_IOI);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Private Methods
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * this function does initialization work for the constructor.
	 * 
	 * @param settingsFile
	 *           - saved settings file
	 */
	private void init( File settingsFile )
	{
		// create the settings object
		if (settings == null)
		{
			settings = new TrippLiteSettings();
		}

		// load file if it exists
		if (settingsFile != null)
		{
			if (settingsFile.exists())
			{
				if (settingsFile.isFile())
				{
					Logging.getLogLog().log(Level.INFO,
							"Loading saved settings from " + settingsFile);
					settings.read(settingsFile);
				}
				else
				{
					Logging.getLogLog().log(Level.WARNING,
							"Passed a directory, not a file - " + settingsFile);
				}
			}
		}
		else
		{
			Logging.getLogLog()
					.log(Level.INFO, "No Settings file, using defaults");
		}

		setHealthStatus(HealthStatus.DEGRADED, "Inactive");

		setName("TrippLite");
		setSymbolCode(symbolCode);

		connected = new AtomicBoolean(false);

		return;
	} // end init

	public void setSettings( TrippLiteSettings s )
	{
		settings = s;

		return;
	}

	public TrippLiteSettings getSettings()
	{
		return settings;
	}

	/**
	 * hook for shell commands
	 * 
	 * @param ps
	 */
	public void deep( PrintStream ps )
	{
		ps.println("TrippLite, Name = " + getName());
		ps.println("\tHost: " + getSettings().getHost());
		ps.println("\tPort: " + getSettings().getPort());
		ps.println("\tRead: " + getSettings().getReadCommunity());
		ps.println("\tWrite: " + getSettings().getWriteCommunity());

		if( connected.get() )
		{
			//add names
			ps.println("\tName: " + queryName() );
			ps.println("\tOnDC: " + queryOnDCTime() );
			ps.println("\tPercent Remaining: " + queryBatteryPercent() );
		}
		ps.println("END");

		return;
	} // end deep

	
	
	/**
	 * helper method for SNMP Get operation
	 */
	private String queryName()
	{
		String ans = "error";
		OID pduName = new OID( ".1.3.6.1.2.1.33.1.1.5.0" );
		PDU operation = new PDU();
		ResponseEvent res;
		PDU result;
		
		operation.add( new VariableBinding( pduName ));
		operation.setType(PDU.GET);
		
		try
		{
			res = snmp.get(operation, readTarget);
			result = res.getResponse();
			if( result != null )
			{
				ans = result.get(0).getVariable().toString();
				setHealthStatus(HealthStatus.GOOD, "OK");
			}
			else
			{
				//timeout
				Logging.getDevLog().log( Level.WARNING, getName() + " TIMEOUT");
				setHealthStatus( HealthStatus.DEGRADED, "comm timeout");
			}
		}
		catch (IOException e)
		{
			Logging.getDevLog().log(Level.INFO, getName() + " error query PDU Name: " + e );
			setHealthStatus(HealthStatus.BAD, "IOException");
		}
		
		return ans;
	} //end queryPduName
	
	/**
	 * helper method for SNMP Get operation
	 */
	private int queryOnDCTime()
	{
		int ans = -1;
		OID pduName = new OID( ".1.3.6.1.2.1.33.1.2.2.0" );
		PDU operation = new PDU();
		ResponseEvent res;
		PDU result;
		
		operation.add( new VariableBinding( pduName ));
		operation.setType(PDU.GET);
		
		try
		{
			res = snmp.get(operation, readTarget);
			result = res.getResponse();
			if( result != null )
			{
				ans = result.get(0).getVariable().toInt();
				setHealthStatus(HealthStatus.GOOD, "OK");
			}
			else
			{
				//timeout
				Logging.getDevLog().log( Level.WARNING, getName() + " TIMEOUT");
				setHealthStatus( HealthStatus.DEGRADED, "comm timeout");
			}
		}
		catch (IOException e)
		{
			Logging.getDevLog().log(Level.INFO, getName() + " error query Time on DC: " + e );
			setHealthStatus(HealthStatus.BAD, "IOException");
		}
		
		return ans;
	} //end queryOnDCTime
	
	/**
	 * helper method for SNMP Get operation
	 */
	private int queryBatteryPercent()
	{
		int ans = -1;
		OID pduName = new OID( ".1.3.6.1.2.1.33.1.2.4.0" );
		PDU operation = new PDU();
		ResponseEvent res;
		PDU result;
		
		operation.add( new VariableBinding( pduName ));
		operation.setType(PDU.GET);
		
		try
		{
			res = snmp.get(operation, readTarget);
			result = res.getResponse();
			if( result != null )
			{
				ans = result.get(0).getVariable().toInt();
				setHealthStatus(HealthStatus.GOOD, "OK");
			}
			else
			{
				//timeout
				Logging.getDevLog().log( Level.WARNING, getName() + " TIMEOUT");
				setHealthStatus( HealthStatus.DEGRADED, "comm timeout");
			}
		}
		catch (IOException e)
		{
			Logging.getDevLog().log(Level.INFO, getName() + " error query Time on DC: " + e );
			setHealthStatus(HealthStatus.BAD, "IOException");
		}
		
		return ans;
	} //end queryOnDCTime

	/**
	 * returns status of UPS power. 
	 * Returns true when the UPS is running off battery power.
	 * 
	 * @return
	 */
	public boolean isDCPower()
	{
		boolean ans = false;
		
		if( connected.get() )
		{
			if( queryOnDCTime() != 0 )
			{
				ans = true;
			}
		}
		
		return ans;
	}

	public int getBatteryLevel()
	{
		int ans = 100;
		
		if( connected.get() )
		{
			ans = queryBatteryPercent();
		}
		
		return ans;
	}
	
			
} // end class TrippLiteAsset
