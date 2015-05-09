/**
 * Developed by Honeywell, 2009
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-09-C-0039
 */

package com.hon.neos.example.asset;

import java.io.File;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.hon.neos.core.asset.AssetDirectoryService;
import com.hon.neos.core.asset.AssetFactory;
import com.hon.neos.core.asset.AssetDirectoryService.ScanResults;
import com.hon.neos.core.platform.Platform;
import com.hon.neos.core.util.NEOSFileSystem;


public class ExampleAssetFactory extends AssetFactory<ExampleAsset>
{
	public static final String PRODUCT_NAME = "Example Asset";
	public static final String PRODUCT_DESCRIPTION =
		"This is a simple Asset Plugin example";

	///////////////////////////////////////////////////////////////////////////
	// Private Fields
	///////////////////////////////////////////////////////////////////////////
	private Vector<ExampleAsset> assets = null;

	///////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////
	public ExampleAssetFactory()
	{
		super();
		assets = new Vector<ExampleAsset>();
		Timer timer = new Timer("ExampleAsset Launcher");
		timer.schedule( new CreationTask(), 5000);
	}

	///////////////////////////////////////////////////////////////////////////
	// Public Methods
	///////////////////////////////////////////////////////////////////////////

	@Override
	public String getProductName()
	{
		return PRODUCT_NAME;
	}

	@Override
	public String getProductDescription()
	{
		return PRODUCT_DESCRIPTION;
	}

	@Override
	public Class<ExampleAsset> getProductType()
	{
		System.out.println("They now know I'm only an example");
		return ExampleAsset.class;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Protected Methods
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Create a new Example Asset
	 */
	@Override
	protected ExampleAsset createAsset()
	{
		System.out.println("Creating an Example Asset. This would " +
				"ordinarily be done from a behavior action or from a user " +
				"interface.");
		ExampleAsset asset = new ExampleAsset();
		assets.add( asset );
		return asset;
	}

	/**
	 * Delete the asset and its associated configuration files.
	 * @param uniqueAssetDirectory the directory containing the asset's files
	 * @param asset the asset to delete
	 */
	@Override
	protected void deleteAsset( File uniqueAssetDirectory, ExampleAsset asset )
	{
		File[] configs = uniqueAssetDirectory.listFiles();
		if(configs != null)
		{
			for( File cfg : configs )
			{
				cfg.delete();
			}
		}
		System.out.println( "Deleting Example Asset and its config data from "
				+ uniqueAssetDirectory.getAbsolutePath() );
		assets.remove( asset );		
	}

	@Override
	protected ExampleAsset restoreAsset( File uniqueAssetDirectory )
	{
		ExampleAsset asset = new ExampleAsset();
		asset.initialize( uniqueAssetDirectory );
		assets.add( asset );
		System.out.println( "Restoring Example Asset based on config data from "
				+ uniqueAssetDirectory.getAbsolutePath() );
		return asset;
	}

	@Override
	protected void restoreAsset( File uniqueAssetDirectory, ExampleAsset asset )
	{
		System.out.println( "Restoring Example Asset based on config data from "
				+ uniqueAssetDirectory.getAbsolutePath() );
		asset.initialize( uniqueAssetDirectory );
	
		if (asset != null)
		{
			assets.add( asset );
		}
		else
		{
			throw new RuntimeException("Could not restore Example Asset");
		}
	}

	@Override
	protected void saveAsset( File uniqueAssetDirectory, ExampleAsset asset )
	{
		asset.save( uniqueAssetDirectory );
	}

	@Override
	protected void scanForNewAssets( ScanResults results, Set<ExampleAsset> existing )
			throws Exception
	{
	
	}

	private class CreationTask extends TimerTask
	{
		public void run()
		{
			ExampleAsset asset = createAsset();
			File assetDir = NEOSFileSystem.getUserSettingsDirectory();  
			asset.save( new File( assetDir, "/assets") );
			((AssetDirectoryService) Platform.getService(
					AssetDirectoryService.SERVICE_ASSET_DIRECTORY )).activateAsset(asset);
		}
	}
}
