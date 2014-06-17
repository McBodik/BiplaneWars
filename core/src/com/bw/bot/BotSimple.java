package com.bw.bot;

import com.badlogic.gdx.Input.Keys;
import com.bw.actors.plane.PlaneController;


public class BotSimple extends Bot{
	private PlaneController controoler;
	
	public BotSimple(PlaneController planeControoler){
		controoler = planeControoler;
		
		controoler.keyDown(Keys.W);
	}
	
	public void update(){
		if(controoler.isFlying()){
			controoler.keyUp(Keys.W);
		}
		
		updateNearestObstacles(controoler.getCurrentPosition());
		
		if((obstacles & BOTTOM) == BOTTOM){
			controoler.keyDown(Keys.W);
		} else
		if((obstacles & TOP) == TOP){
			controoler.keyDown(Keys.S);
		} else
		
		if((obstacles & RIGHT) == RIGHT || (obstacles & LEFT) == LEFT){
			controoler.keyDown(Keys.W);
		} else {
			controoler.keyUp(Keys.W);
			controoler.keyUp(Keys.S);
		}
		
		
	}
	
}
