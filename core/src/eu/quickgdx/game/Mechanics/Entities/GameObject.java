package eu.quickgdx.game.Mechanics.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.Rectangle;

import eu.quickgdx.game.Mechanics.States.State;
import eu.quickgdx.game.Mechanics.World;

/**
 * Created by Veit on 06.02.2016.
 */
public abstract class GameObject {
    Vector2 position;
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



}