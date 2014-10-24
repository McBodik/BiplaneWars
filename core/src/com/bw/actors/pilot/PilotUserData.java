package com.bw.actors.pilot;


public class PilotUserData {
	private PilotActor pilotActor;
	
	
	public PilotUserData(PilotActor pilotActor) {
		this.pilotActor = pilotActor;
	}
	
	public PilotActor getPilotActor(){
		return pilotActor;
	}
}
