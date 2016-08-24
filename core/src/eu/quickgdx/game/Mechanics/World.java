package eu.quickgdx.game.Mechanics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.Mechanics.Entities.GameObject;
import eu.quickgdx.game.Mechanics.Entities.SkeletonControlledObject;
import eu.quickgdx.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    private SpriteBatch spriteBatch;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    SkeletonControlledObject skeletonControlledObject;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;

        //Add SkeletonControlledObject
        skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f,0f),this);
        gameObjects.add(skeletonControlledObject);

    }

    public void update(float delta) {
        for (GameObject go: gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go: gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

    public void touch(Vector3 touchCoords) {
        skeletonControlledObject.touch(touchCoords);
    }
}
