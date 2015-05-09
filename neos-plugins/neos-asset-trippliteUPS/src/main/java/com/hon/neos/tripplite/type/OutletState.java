/**
 * Developed by Honeywell, 2012
 * Government Purpose Rights
 * For Official Use Only(FOUO) with Terra Harvest program
 * contract # HHM402-10-C-0050
 */

package com.hon.neos.tripplite.type;

public enum OutletState
{
	ON(1),
	OFF(2),
	REBOOT(3),
	UNKNOWN(4),
	ON_DELAY(5),
	OFF_DELAY(6),
	REBOOT_DELAY(7),
	INVALID(255);
	
	private int fieldValue;
	
	private OutletState( int f )
	{
		fieldValue = f;
	}
	
	public int getFieldValue()
	{
		return fieldValue;
	}
	
	public static OutletState fromInt( int i )
	{
		OutletState id = OutletState.INVALID;
		for( OutletState val : OutletState.values() )
		{
			if( val.getFieldValue() == i )
			{
				id = val;
				break;
			}
		}
		return id;
	}

} //end enum Reporting
