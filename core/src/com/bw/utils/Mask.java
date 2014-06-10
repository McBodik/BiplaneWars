package com.bw.utils;

public class Mask {
	public static final short PLANE = Category.PLANE | Category.BULLET | Category.GROUND;
	public static final short BULLET = Category.BULLET | Category.PLANE | Category.GROUND;
	public static final short GROUND = -1;
}
