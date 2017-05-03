package eu.quickgdx.game.mechanics.entities.components;

/**
 * Created by Veit on 03.05.2017.
 */
public abstract class EntityComponent {

    public static Class componentClass;

    public EntityComponent() {
        this.componentClass = this.getClass();
    }
}

