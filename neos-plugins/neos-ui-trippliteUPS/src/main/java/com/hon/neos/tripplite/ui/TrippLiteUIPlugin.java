/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite.ui;

import com.hon.neos.core.plugin.AbstractPlugIn;
import com.hon.neos.core.plugin.PlugInInfo;

public class TrippLiteUIPlugin extends AbstractPlugIn
{
	private static TrippLiteSettingsViewFactory settingsViewFactory = null;

	public TrippLiteUIPlugin( PlugInInfo info )
	{
		super(info);
		// TODO Auto-generated constructor stub
	}

	public TrippLiteSettingsViewFactory getTrippLiteSettingsViewFactory()
	{
		if( settingsViewFactory == null )
		{
			settingsViewFactory = new TrippLiteSettingsViewFactory();
		}
		return settingsViewFactory;
	} 
	
} //end class TrippLiteUIPlugin
