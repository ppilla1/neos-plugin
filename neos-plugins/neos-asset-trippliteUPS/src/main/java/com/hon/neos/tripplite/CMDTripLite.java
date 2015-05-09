/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite;/**
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-09-C-0039
 */


import java.io.PrintStream;

import com.hon.neos.core.shell.ComplexCommand;
import com.hon.neos.tripplite.shell.Add;
import com.hon.neos.tripplite.shell.Deep;
import com.hon.neos.tripplite.shell.Status;

public class CMDTripLite extends ComplexCommand
{
	public CMDTripLite()
	{
		super();
		setToken("tripplite");
		
		Status s = new Status();
		addSubCmd( s );
		setDefaultCmd( s );
		
		addSubCmd( new Add() );
		addSubCmd( new Deep() );
	}

	@Override
	protected void baseHelp( int level, PrintStream ps)
	{
		indent(level, ps);
		ps.println("TrippLite UPS commands");
		
		return;
	}
	

} //end class CMDCsr
