/**
 * Developed by Honeywell, 2009
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-09-C-0039
 */

package com.hon.neos.example.asset;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import com.hon.neos.core.asset.Asset;
import com.hon.neos.core.asset.AssetStatus;
import com.hon.neos.core.asset.AssetStatus.Summary;
import com.hon.neos.core.util.Logging;


/**
 * \
 * @author Brian Adams
 *
 */
 public class ExampleAsset extends Asset 
{
	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 4382266410777171630L;
	private AssetStatus myStatus = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	public ExampleAsset()
	{
		super();
		
		setName( "Example Asset" );
		myStatus = new AssetStatus( getName(),
									AssetStatus.Summary.OFF,
									"Example Asset inactive." );
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////



	@Override
	public boolean isBITSupported()
	{
		return false;
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

	///////////////////////////////////////////////////////////////////////////
	// Protected Methods
	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean onActivate()
	{
		System.out.println("Example Asset is activating.");
		Logging.getDevLog().log( Level.SEVERE, "onActivate called");
		//this is where the magic starts
		myStatus.setSummary( Summary.GOOD );
		myStatus.setStatusMessage( "Example Asset activated." );
		return true;
	}

	@Override
	protected boolean onDeactivate()
	{
		System.out.println("Example Asset is deactivating.");
		Logging.getDevLog().log( Level.SEVERE, "onDeactivate called");
		
		//this is where you stop your magic
		myStatus.setSummary( Summary.OFF );
		myStatus.setStatusMessage( "Example Asset deactivated." );		
		return true;
	}

	@Override
	protected List<File> onCaptureData(File storageDir)
	{
		return null;
	}

	@Override
	protected AssetStatus onPerformBIT()
	{
		return getStatus();
	}

	@Override
	protected void onStartRecording()
	{
	}

	@Override
	protected List<File> onStopRecording(File storageDir)
	{
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// Private Methods
	///////////////////////////////////////////////////////////////////////////
	@Override
	public AssetStatus getStatus()
	{
		return myStatus;
	}

	public void initialize(File uniqueAssetDirectory) 
	{
		System.out.println("Initialize called, your implementation may vary in how this is done.");
	}

	public void save(File uniqueAssetDirectory) 
	{
		System.out.println("Example Asset is saving config to "
				+ uniqueAssetDirectory.getAbsolutePath() 
				+ ". Your implementation may vary in how this is done.");
		ExampleAssetSettings settings = new ExampleAssetSettings();
		settings.save( uniqueAssetDirectory );
	}

	
} //end class ExampleAsset
