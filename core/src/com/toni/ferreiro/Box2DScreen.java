package com.toni.ferreiro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen{

    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body player1Body;
    private Fixture player1Fixture;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true); //en la tierra es -9.8
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BodyDef player1Def = createPlayerBodyDef();
        player1Body = world.createBody(player1Def);

        PolygonShape player1Shape = new PolygonShape(); //CircleShape si fuera un circulo
        player1Shape.setAsBox(1, 1); //en metros
        player1Fixture = player1Body.createFixture(player1Shape, 1);
        player1Shape.dispose();

    }

    private BodyDef createPlayerBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 10);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        world.destroyBody(player1Body); //no vale con dispose
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 6, 2);

        camera.update();
        renderer.render(world, camera.combined);
    }
}
