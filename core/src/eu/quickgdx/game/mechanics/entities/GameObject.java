package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import eu.quickgdx.game.mechanics.states.State;
import eu.quickgdx.game.mechanics.World;

/**
 * Basic game object class .. all other objects inside the game world should consider, to inherit
 * this class. The GameObject gives you basic functionalit as:
 *  - position inside the world
 *  - hitpoints (but not maintenance of hitpoints)
 *  - states - (holds a list of state, minimal state management is already done here, e.g. adding and updating states)
 * Created by Veit on 06.02.2016.
 */
public abstract class GameObject {

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

    public void removeState(State state) {
        this.states.removeValue(state, false);
    }

    /**
     * Adds a new state to the state array. Stackable states will be added without check,
     * state objects which are not stackable, will only be added if they are not already in the array
     * @param state
     */
    public void addState(State state) {
        if (state.stackable) {
            states.add(state);
        } else if (!states.contains(state, false)) {
            states.add(state);
        }
    }

    /**
     * Basic update method. updates all states by default (with delta time)
     * @param delta
     */
    public void update(float delta) {
        for (State state : states) {
            state.update(delta);
        }
    }

    /**
     * here happens the render magic. Each concrete object has to decide how it is done
     * @param delta
     * @param spriteBatch
     */
    public abstract void render(float delta, SpriteBatch spriteBatch);

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

}
