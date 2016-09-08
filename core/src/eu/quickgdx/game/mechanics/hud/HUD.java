package eu.quickgdx.game.mechanics.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.text.DecimalFormat;

import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.entities.ControlledObject;

/**
 * Created by Veit on 23.02.2016.
 */
public class HUD {
    World world;
    ControlledObject object;
    static BitmapFont textFont;
    private GlyphLayout layout = new GlyphLayout();
    boolean multiplierUp = false;
    float multiplierTime = 1f;
    DecimalFormat df = new DecimalFormat("#.##");
    Texture tex;

    public HUD(ControlledObject object, World world) {
        this.object = object;
        this.world = world;
        textFont = world.gameplayScreen.parentGame.getAssetManager().get("fonts/RabbidHighwaySignII.fnt", BitmapFont.class);
        tex = world.gameplayScreen.parentGame.getAssetManager().get("hud/life_small.png");
    }

    public void render(float delta, SpriteBatch hudBatch) {

        for(int i = 0; i<object.getHitpoints(); i++){
            hudBatch.draw(tex,(62+10*i+tex.getWidth()*i), QuickGdx.GAME_HEIGHT-tex.getHeight()-68);
        }
        layout.setText(textFont, "Position: " + object.getPosition().x + " | " + object.getPosition().y);
        textFont.draw(hudBatch, layout, QuickGdx.GAME_WIDTH / 2 - layout.width / 2, QuickGdx.GAME_HEIGHT-layout.height-650);
    }
}
