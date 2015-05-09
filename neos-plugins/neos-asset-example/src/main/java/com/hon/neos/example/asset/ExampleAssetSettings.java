/**
 * Developed by Honeywell, 2009
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-09-C-0039
 */

package com.hon.neos.example.asset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ExampleAssetSettings extends Properties
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 227791612470418010L;
	private static final String settingsFilename = "ExampleAsset.xml";
	private static final String KEY_SERIAL_PORT = "Serial Port";
	private static final String KEY_BAUD_RATE = "Baud Rate";
	private static final String KEY_PARITY_BIT = "Parity";
	private static final String KEY_DATA_BITS = "Data Bits";
	private static final String KEY_STOP_BIT = "Stop Bit";
	private static final String KEY_FLOW_CONTROL = "Flow Control";

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Default constructor
	 */
	public ExampleAssetSettings()
	{
		super();
		setDefaults();
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Read settings from the provided file.
	 * @param settingsFile the settings file
	 */
	public void read( File settingsFile )
	{
		System.out.println( "Reading config for Example Asset from "
				+ settingsFile.getAbsolutePath() );
		try
		{
			this.loadFromXML( new FileInputStream( settingsFile ) );
		}
		catch( InvalidPropertiesFormatException e )
		{
		}
		catch( FileNotFoundException e )
		{
		}
		catch ( IOException e )
		{
		}
	}

	/**
	 * Write settings to the provided file.
	 * @param settingsFile the settings file
	 */
	public void save( File settingsFile )
	{
		System.out.println( "Saving config for Example Asset to "
				+ settingsFile.getAbsolutePath() + File.separator + settingsFilename );
		FileOutputStream os = null;
		String comment = String.format( "Example Asset Settings, %s",
				Calendar.getInstance().getTime().toString() );
		try
		{
			os = new FileOutputStream( new File( settingsFile, settingsFilename ) );
			storeToXML( os, comment );
		}
		catch( FileNotFoundException e )
		{
		}
		catch( IOException e )
		{
		}
	}

	/**
	 * Get the serial port name.
	 * @return the serial port name
	 */
	public String getSerialPort()
	{
		return getProperty( KEY_SERIAL_PORT );
	}

	/**
	 * Set the serial port name.
	 * @param portName the serial port name
	 */
	public void setSerialPort( String portName )
	{
		setProperty( KEY_SERIAL_PORT, portName );
	}

	/**
	 * Get the baud rate.
	 * @return the baud rate
	 */
	public int getBaudRate()
	{
		return Integer.parseInt( getProperty( KEY_BAUD_RATE ) );
	}

	/**
	 * Set the baud rate.
	 * @param baudRate
	 */
	public void setBaudRate( int baudRate )
	{
		setProperty( KEY_BAUD_RATE, String.valueOf( baudRate ) );
	}

	/**
	 * Get the parity value.
	 * @return the parity value
	 */
	public int getParity()
	{
		return Integer.parseInt( getProperty( KEY_PARITY_BIT ) );
	}

	/**
	 * Set the parity value.
	 * @param parity
	 */
	public void setParity( int parity )
	{
		setProperty( KEY_PARITY_BIT, String.valueOf( parity ) );
	}

	/**
	 * Get the number of data bits.
	 * @return the data bits
	 */
	public int getDataBits()
	{
		return Integer.parseInt( getProperty( KEY_DATA_BITS ) );
	}

	/**
	 * Set the number of data bits.
	 * @param bits the number of bits
	 */
	public void setDataBits( int bits )
	{
		setProperty( KEY_DATA_BITS, String.valueOf( bits ) );
	}

	/**
	 * Get the number of stop bits.
	 * @return the number of stop bits
	 */
	public int getStopBit()
	{
		return Integer.parseInt( getProperty( KEY_STOP_BIT ) );
	}

	/**
	 * Set the number of stop bits.
	 * @param stops
	 */
	public void setStopBit( int stops )
	{
		setProperty( KEY_STOP_BIT, String.valueOf( stops ) );
	}

	/**
	 * Get the flow control value.
	 * @return the flow control value
	 */
	public int getFlowControl()
	{
		return Integer.parseInt( getProperty( KEY_FLOW_CONTROL ) );
	}

	/**
	 * Set the flow control value.
	 * @param flowControl
	 */
	public void setFlowControl( int flowControl )
	{
		setProperty( KEY_FLOW_CONTROL, String.valueOf( flowControl ) );
	}

	///////////////////////////////////////////////////////////////////////////
	// Private Methods
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Set default settings values for an Example asset. 
	 */
	private void setDefaults()
	{
		System.out.println( "Creating default config for Example Asset." );
		this.put( KEY_SERIAL_PORT, "COM2" );
		this.put( KEY_BAUD_RATE, "9600" );
		this.put( KEY_PARITY_BIT, "0"  );
		this.put( KEY_DATA_BITS, "8" );
		this.put( KEY_STOP_BIT, "1" );
		this.put( KEY_FLOW_CONTROL, "0" );
	}
}
