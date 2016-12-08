package eu.quickgdx.game.mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.entities.CollisionObject;
import eu.quickgdx.game.mechanics.entities.ControlledObject;
import eu.quickgdx.game.mechanics.entities.GameObject;
import eu.quickgdx.game.mechanics.hud.HUD;
import eu.quickgdx.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    public static final float SCALE = 2.5f;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public HUD hud;
    ShapeRenderer sr = new ShapeRenderer();
    ControlledObject controlledObject;

    //Tiled Map Variables
    String level = "level/sampleMap.tmx"; //This is your example Tiled Map.
    TiledMap map;
    TiledMapRenderer tiledMapRenderer;
    int mapWidth;
    int tileWidth;
    int mapHeight;
    int tileHeight;

    public World(GameplayScreen gameplayScreen) {
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;
        loadTiledMap();
        //Add HUD
        this.hud = new HUD(controlledObject, this);



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

        //Debug Renderer
        sr.setProjectionMatrix(gameplayScreen.gameCam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0, 1, 0, 1);
        for (GameObject gameObject : gameObjects) {
            if(gameObject.getBounds()!= null)
                sr.rect(gameObject.getBounds().x, gameObject.getBounds().y, gameObject.getBounds().width, gameObject.getBounds().height);
        }
        sr.end();
    }

    public void renderHUD(float delta, SpriteBatch hudBatch) {
        hudBatch.begin();
        this.hud.render(delta, hudBatch);
        hudBatch.end();
    }

    public void touch(Vector3 touchCoords) {
        controlledObject.touch(touchCoords);
    }

    public void loadTiledMap(){
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        map = new TmxMapLoader().load(level);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map,SCALE); //Just scaled the map - This is not how you should do it obviously!
        mapWidth = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        //load collision map
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if(cell != null) {
                    Object property = cell.getTile().getProperties().get("collision");
                    if (property != null) {
                        gameObjects.add(new CollisionObject(new Vector2((x * SCALE * 16), (y * SCALE * 16)), this, SCALE *16, SCALE * 16));
                    }
                }
            }
        }

        // load objects from map
        MapObjects objects = map.getLayers().get("objects").getObjects();
        // create objects
        for (int i = 0; i < objects.getCount(); i++) {
            MapProperties object = objects.get(i).getProperties();
            String type = object.get("type", String.class);
            if (type.equals("controllableObject")) {
                controlledObject = new ControlledObject(new Vector2((object.get("x", Float.class)*SCALE),(object.get("y", Float.class)*SCALE)),this);
                gameObjects.add(controlledObject);
            }
        }
    }
}
