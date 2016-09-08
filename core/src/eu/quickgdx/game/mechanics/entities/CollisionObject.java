package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.mechanics.World;

/**
 * Created by Veit on 25.08.2016.
 */
public class CollisionObject extends GameObject {
    public CollisionObject(Vector2 position, World world, float length, float width) {
        super(position, world);
        this.setBounds(new Rectangle(position.x, position.y, length, width));
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        return;
    }
}
