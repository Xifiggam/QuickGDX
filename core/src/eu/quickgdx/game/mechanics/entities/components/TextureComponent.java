package eu.quickgdx.game.mechanics.entities.components;

import com.badlogic.gdx.graphics.Texture;

public class TextureComponent extends EntityComponent {
    public Texture texture;

    public TextureComponent(Texture texture) {
        super();
        this.texture = texture;
    }
}
