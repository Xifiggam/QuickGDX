package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import eu.quickgdx.game.mechanics.World;

/**
 * Simple class to give you the ability to add collision objects (via bounds)
 *
 * Created by Veit on 25.08.2016.
 */
public class CollisionObject extends GameObject {
    public CollisionObject(Vector2 position, World world, float width, float height) {
        super(position, world);
        this.setBounds(new Rectangle(position.x, position.y, width, height));
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        return;
    }
}
