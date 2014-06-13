package com.bw;

import com.badlogic.gdx.Game;
import com.bw.screen.BattlefieldScreen;

public class BiplaneWars extends Game {

	private static BiplaneWars instance = null;

	private BiplaneWars() {
	};

	public static BiplaneWars getInstance() {
		if (instance == null)
			instance = new BiplaneWars();
		return instance;
	}

	@Override
	public void create() {
		setScreen(new BattlefieldScreen());
	}
}
