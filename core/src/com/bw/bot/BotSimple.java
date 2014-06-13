package com.bw.bot;

import com.badlogic.gdx.Input.Keys;
import com.bw.actors.plane.PlaneController;


public class BotSimple {
	private PlaneController controoler;
	
	public BotSimple(PlaneController planeControoler){
		controoler = planeControoler;
		
		controoler.keyDown(Keys.W);
	}
	
	
}
