/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite;

import java.io.File;
import java.util.Set;

import com.hon.neos.core.asset.AssetDirectoryService.ScanResults;
import com.hon.neos.core.asset.AssetFactory;

///////////////////////////////////////////////////////////////////////////////
//Class GpsAssetType
///////////////////////////////////////////////////////////////////////////////
/**
 * Class GpsAssetType implements a NEOS standard plugin that interfaces a
 * GpsAssetType receiver to the NEOS platform. It provides position information
 * to the system, operator, and to other assets/plugins via an IOI.
 * <P>
 * <P>
 * 
 * @since v1.0
 * @version v1.0
 * @author Chris Black
 * @maturity Red
 */
public class TrippLiteAssetFactory extends AssetFactory<TrippLiteAsset>
{
	// /////////////////////////////////////////////////////////////////////////
	// Private Fields
	// /////////////////////////////////////////////////////////////////////////
	private static final String TYPE_NAME = "TrippLite UPS";
	private static final String DESCRIPTION = "SNMP Control";
	private static final String CONFIG_FILENAME = "Config.xml";

	// /////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Default Constructor.
	 */
	public TrippLiteAssetFactory()
	{
		super();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Public Methods
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * @see com.hon.neos.core.asset.AssetType#getTypeName()
	 */
	public String getTypeName()
	{
		return TYPE_NAME;
	}

	@Override
	public String getProductDescription()
	{
		return TrippLiteAssetFactory.DESCRIPTION;
	}

	@Override
	public String getProductName()
	{
		return TrippLiteAssetFactory.TYPE_NAME;
	}

	@Override
	public Class<TrippLiteAsset> getProductType()
	{
		return TrippLiteAsset.class;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Protected Methods
	// /////////////////////////////////////////////////////////////////////////
	@Override
	protected TrippLiteAsset createAsset() throws Exception
	{
		return new TrippLiteAsset();
	}

	@Override
	protected void deleteAsset( TrippLiteAsset asset) throws Exception
	{
		asset.deleteSelf();
	}

	@Override
	protected TrippLiteAsset restoreAsset(File uniqueAssetDirectory) throws Exception
	{
		TrippLiteAsset asset = new TrippLiteAsset();
		restoreAsset(uniqueAssetDirectory, asset);
		return asset;
	}

	@Override
	protected void restoreAsset(File uniqueAssetDirectory, TrippLiteAsset asset)
			throws Exception
	{
		TrippLiteSettings settings = new TrippLiteSettings();
		settings.read( new File( uniqueAssetDirectory, CONFIG_FILENAME ) );
		asset.setSettings( settings );
	}

	@Override
	protected void saveAsset(File uniqueAssetDirectory, TrippLiteAsset asset)
			throws Exception
	{
		TrippLiteSettings settings = asset.getSettings();
		
		settings.save( new File( uniqueAssetDirectory, CONFIG_FILENAME ) );
	}

	@Override
	protected void scanForNewAssets(ScanResults results,
			Set<TrippLiteAsset> existing) throws Exception
	{
		// TODO Auto-generated method stub
	}
	
} //end GpsNetAssetFactory
