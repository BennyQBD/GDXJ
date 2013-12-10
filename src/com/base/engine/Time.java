package com.base.engine;

public class Time 
{
	private static final long SECOND = 1000000000L;
	
	private static double delta;
	
	public static double getTime()
	{
		return ((double)System.nanoTime() / (double)Time.SECOND);
	}
	
	public static double getDelta()
	{
		return delta;
	}
	
	public static float getDeltaf()
	{
		return (float)delta;
	}
	
	public static void setDelta(double delta)
	{
		Time.delta = delta;
	}
}
