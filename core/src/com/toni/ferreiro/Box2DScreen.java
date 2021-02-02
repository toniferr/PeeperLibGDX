package com.toni.ferreiro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen{

    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body player1Body, sueloBody, pinchoBody;
    private Fixture player1Fixture, sueloFixture, pinchoFixture;

    private boolean debeSaltar, player1Saltando, player1Vivo = true;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true); //en la tierra es -9.8, con true si está en reposos ya no actualiza
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9); //proporcion 16/9 con alto de 4 metros
        camera.translate(0,1); //mueve la camara 1 metro hacia abaja

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) ||
                        (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))){
                    if (Gdx.input.isTouched()){
                        debeSaltar = true;
                    }
                    player1Saltando = false;
                }


                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("spike")) ||
                        (fixtureA.getUserData().equals("spike") && fixtureB.getUserData().equals("player"))){
                    player1Vivo = false;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if (fixtureA == player1Fixture && fixtureB == sueloFixture) {
                    //no puedes hacer saltar();
                    player1Saltando = true;
                }

                if (fixtureA == sueloFixture && fixtureB == player1Fixture) {
                    //no puedes hacer saltar();
                    player1Saltando = true;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        player1Body = world.createBody(createPlayerBodyDef());
        sueloBody = world.createBody(createSueloBodyDef());
        pinchoBody = world.createBody(createPinchoBodyDef(6f));


        PolygonShape player1Shape = new PolygonShape(); //CircleShape si fuera un circulo
        player1Shape.setAsBox(0.5f, 0.5f); //lo que mide en metros (es la mitad)
        player1Fixture = player1Body.createFixture(player1Shape, 4);
        player1Shape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture = sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        pinchoFixture = createPinchoFixture(pinchoBody);

        player1Fixture.setUserData("player");
        sueloFixture.setUserData("floor");
        pinchoFixture.setUserData("spike");

    }

    private BodyDef createPinchoBodyDef(float x) {
        BodyDef def = new BodyDef();
        def.position.set(x, 0.5f);
        return def;
    }

    private BodyDef createSueloBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, -1);
        def.type = BodyDef.BodyType.StaticBody; //se puede omitir, por defecto es static
        return def;
    }

    private BodyDef createPlayerBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 10);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    private Fixture createPinchoFixture(Body pinchoBody) {
        Vector2[] vertices = new Vector2[3]; //8 vertices máx.
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        Fixture fix = pinchoBody.createFixture(shape, 1);
        shape.dispose();
        return fix;
    }

    @Override
    public void dispose() {
        sueloBody.destroyFixture(sueloFixture);
        player1Body.destroyFixture(player1Fixture);
        pinchoBody.destroyFixture(pinchoFixture);
        world.destroyBody(player1Body); //no vale con dispose
        world.destroyBody(sueloBody);
        world.destroyBody(pinchoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (debeSaltar){
            debeSaltar = false;
            saltar();
        }

        if (Gdx.input.justTouched() && !player1Saltando){
            debeSaltar = true;
        }

        if (player1Vivo) {
            float velocidadY = player1Body.getLinearVelocity().y;
            player1Body.setLinearVelocity(5, velocidadY);
        }

        world.step(delta, 6, 2); //6 y 2 por documentacion

        camera.update();
        renderer.render(world, camera.combined);
    }

    private void saltar() {
        Vector2 position = player1Body.getPosition();
        player1Body.applyLinearImpulse(0,20, position.x, position.y, true);
    }
}
