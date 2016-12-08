package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.states.State;
import eu.quickgdx.game.mechanics.World;

/**
 * Created by Veit on 06.02.2016.
 */
public abstract class GameObject {
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Array<State> getStates() {
        return states;
    }

    public void setStates(Array<State> states) {
        this.states = states;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    Vector2 position;
    int hitpoints;
    Rectangle bounds;
    World world;
    Texture texture;
    Array<State> states;


    public GameObject(Vector2 position, World world) {
        this.position = position;
        this.world = world;
        states = new Array<State>();
    }

    public void removeState(State state){
        this.states.removeValue(state, false);
}

    public void addState(State state){
        if(state.stackable){
            states.add(state);
        }
        else if(!states.contains(state, false)){
            states.add(state);
        }
    }

    public void update(float delta){
        for (State state: states) {
            state.update(delta);
        }
    };
    public abstract void render(float delta, SpriteBatch spriteBatch);

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

}
