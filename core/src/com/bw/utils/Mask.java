package com.bw.utils;

public class Mask {
	public static final short GROUND = -1;
	public static final short PLANE = Category.PLANE | Category.BULLET | Category.PILOT | Category.GROUND;
	public static final short BULLET = Category.BULLET | Category.PLANE | Category.PILOT | Category.GROUND;
	public static final short PILOT = Category.BULLET | Category.PLANE | Category.GROUND;
}
