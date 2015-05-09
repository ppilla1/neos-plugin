/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite.ui;

import com.hon.neos.suit.SettingsView;
import com.hon.neos.suit.SettingsViewFactory;
import com.hon.neos.tripplite.TrippLiteAsset;

public class TrippLiteSettingsViewFactory implements
		SettingsViewFactory<TrippLiteAsset>
{

	@Override
	public Class<TrippLiteAsset> getModelType()
	{
		return TrippLiteAsset.class;
	}

	@Override
	public SettingsView<TrippLiteAsset> createSettingsView( TrippLiteAsset model )
	{
		return new TrippLiteSettingsView( model );
	}

}
