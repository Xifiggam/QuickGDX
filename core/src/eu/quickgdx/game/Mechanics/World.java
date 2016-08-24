package eu.quickgdx.game.mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.hud.HUD;
import eu.quickgdx.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public HUD hud;
    ControlledObject controlledObject;

    //Tiled Map Variables
    String level = "level/sampleMap.tmx"; //This is your example Tiled Map.
    TiledMap map;
    TiledMapRenderer tiledMapRenderer;

    public World(GameplayScreen gameplayScreen) {
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;

        //Add ControlledObject
        controlledObject = new ControlledObject(new Vector2(0f,0f),this);
        gameObjects.add(controlledObject);

        //Add HUD
        controlledObject.setHitpoints(10); //So you can see something.
        this.hud = new HUD(controlledObject, this);

        //Load TiledMap
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        map = new TmxMapLoader().load(level);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map,2.5f); //Just scaled the map - This is not how you should do it obviously!


    }

    public void update(float delta) {
        for (GameObject go: gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        tiledMapRenderer.setView(gameplayScreen.gameCam);
        tiledMapRenderer.render();
        spriteBatch.begin();
        for (GameObject go: gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

    public void renderHUD(float delta, SpriteBatch hudBatch) {
        hudBatch.begin();
        this.hud.render(delta, hudBatch);
        hudBatch.end();
    }

    public void touch(Vector3 touchCoords) {
        controlledObject.touch(touchCoords);
    }
}
