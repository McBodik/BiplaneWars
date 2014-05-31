package com.bw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bw.actors.plane.PlaneActor;
import com.bw.actors.plane.types.General;

public class BattlefieldScreen implements Screen {

	World world;
	Box2DDebugRenderer renderer;
	OrthographicCamera camera;
	PlaneActor plane;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		plane.update();
		world.step(1 / 60f, 8, 3);
		renderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 10;
		camera.viewportHeight = height / 10;
		camera.update();
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();

		ChainShape rectangle = new ChainShape();
		rectangle.createChain(new Vector2[] {

				new Vector2(-Gdx.graphics.getWidth() / 20f, Gdx.graphics
						.getHeight() / 20f),
				new Vector2(-Gdx.graphics.getWidth() / 20f, -23),
				new Vector2(Gdx.graphics.getWidth() / 20f, -23),
				new Vector2(Gdx.graphics.getWidth() / 20f, Gdx.graphics
						.getHeight() / 20f - 5),
				new Vector2(-Gdx.graphics.getWidth() / 20f, Gdx.graphics
						.getHeight() / 20f - 5) });

		BodyDef ground = new BodyDef();
		ground.type = BodyType.StaticBody;
		ground.position.set(0, 0);
		FixtureDef fd = new FixtureDef();
		fd.shape = rectangle;
		fd.restitution = 0f;
		fd.friction = 0.5f;

		world.createBody(ground).createFixture(fd);

		plane = new PlaneActor(world, new General());
		Gdx.input.setInputProcessor(plane.getPlaneController());
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
