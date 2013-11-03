package org.ixming.utils;

/**
 * utilities of number
 * @author Yin Yong
 * @version 1.0
 */
public class NumberUtil {

	private NumberUtil() { /*no instance*/ }
	
	static final byte BYTE_ZERO = (byte) 0;
	static final short SHORT_ZERO = (short) 0;
	static final int INT_ZERO = 0;
	static final long LONG_ZERO = 0L;
	static final float FLOAT_ZERO = 0.0F;
	static final double DOUBLE_ZERO = 0.0D;
	
	public static byte getByte(String string) {
		return getByte(string, BYTE_ZERO);
	}
	
	public static byte getByte(String string, byte defVal) {
		try {
			return Byte.parseByte(string);
		} catch (Exception e) {
			return defVal;
		}
	}

	public static short getShort(String string) {
		return getShort(string, SHORT_ZERO);
	}
	
	public static short getShort(String string, short defVal) {
		try {
			return Short.parseShort(string);
		} catch (Exception e) {
			return defVal;
		}
	}
	
	public static int getInt(String string) {
		return getInt(string, INT_ZERO);
	}
	
	public static int getInt(String string, int defVal) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return defVal;
		}
	}

	public static long getLong(String string) {
		return getLong(string, LONG_ZERO);
	}
	
	public static long getLong(String string, long defVal) {
		try {
			return Long.parseLong(string);
		} catch (Exception e) {
			return defVal;
		}
	}

	public static float getFloat(String string) {
		return getFloat(string, FLOAT_ZERO);
	}
	
	public static float getFloat(String string, float defVal) {
		try {
			return Float.parseFloat(string);
		} catch (Exception e) {
			return defVal;
		}
	}

	public static double getDouble(String string) {
		return getDouble(string, DOUBLE_ZERO);
	}
	
	public static double getDouble(String string, double defVal) {
		try {
			return Double.parseDouble(string);
		} catch (Exception e) {
			return defVal;
		}
	}
	
	public static String format(double d, int scale) {
		if (scale <= 0) {
			return String.valueOf((int) d);
		}
		return String.format("%." + scale + "f", d);
	}
	
	public static String formatByUnit(int i, int incrementBetweenUnits, String...units) {
		if (incrementBetweenUnits <= 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit incrementBetweenUnits is negative!");
		}
		if (null == units || units.length == 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit 'units' is of a length of at least 1!");
		}
		int unitIndex = 0;
		int nearestVal = i;
		int tmp = 0;
		while ((tmp = nearestVal / incrementBetweenUnits) > 0) {
			if (unitIndex >= units.length - 1) {
				break;
			}
			unitIndex ++;
			nearestVal = tmp;
		}
		return nearestVal + units[Math.min(units.length, unitIndex)];
	}
	
	public static String formatByUnit(double d, double incrementBetweenUnits, int scale, String...units) {
		if (incrementBetweenUnits <= 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit incrementBetweenUnits is negative!");
		}
		if (null == units || units.length == 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit 'units' is of a length of at least 1!");
		}
		int unitIndex = 0;
		double nearestVal = d;
		if (unitIndex < units.length - 1) {
			double tmp = 0;
			while ((int)(tmp = nearestVal / incrementBetweenUnits) > 0) {
				unitIndex ++;
				nearestVal = tmp;
				if (unitIndex >= units.length - 1) {
					break;
				}
			}
		}
		return format(nearestVal, scale) + units[Math.min(units.length, unitIndex)];
	}
	
	public static String formatByUnit(double d, double incrementBetweenUnits,
			double offset, int scale, String...units) {
		if (incrementBetweenUnits <= 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit incrementBetweenUnits is negative!");
		}
		if (null == units || units.length == 0) {
			throw new IllegalArgumentException("NumberUtil.formatByUnit 'units' is of a length of at least 1!");
		}
		int unitIndex = 0;
		double nearestVal = d;
		// |		0		|			 	|
		// |	__________check__________	|
		// |		1		|	  check		|--->return?
		// |		2		|	  check		|--->return?
		// |		3		|	  check		|--->return?
		// |		4		|	  check		|--->return?
		// |	   ...		|	   ....		|
		if (unitIndex < units.length - 1) {
			double tmp = 0;
			while ((int)(tmp = nearestVal / incrementBetweenUnits) > 0) {
				unitIndex ++;
				nearestVal = tmp;
				if (unitIndex >= units.length - 1) {
					break;
				}
			}
		}
		// 1024 * 1024 * 900, if offset is 800, then here nearestVal == 900,
		// 900 > 800, we think it can be up to the next unit
		if (nearestVal > offset) {
			if (unitIndex < units.length - 1) {
				nearestVal = nearestVal / incrementBetweenUnits;
				unitIndex ++;
			}
		}
		return format(nearestVal, scale) + units[Math.min(units.length, unitIndex)];
	}
}
