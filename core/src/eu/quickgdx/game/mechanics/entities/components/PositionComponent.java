package eu.quickgdx.game.mechanics.entities.components;

import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends EntityComponent {
    public Vector2 position;

    public PositionComponent(Vector2 position) {
        super();
        this.position = position;
    }
}
