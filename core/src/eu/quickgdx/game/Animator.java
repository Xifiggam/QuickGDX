package eu.quickgdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation helper class. is doing some basic animation either with single files
 * (e.g. move1.png, move2.png, move3.png ..) or with a spritesheet
 *
 * Created by Veit on 08.02.2016.
 */
public class Animator {
    QuickGdx game;

    public Animator(QuickGdx game) {
        this.game = game;
    }

    /**
     * Loads an Animation from single files. Files must be named like path1.png
     * @param path
     * @param frames
     * @param frameDuration
     * @return
     */
    public Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {

            Texture tex = game.getAssetManager().get(path + (i + 1) + ".png");
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            regions[i] = new TextureRegion(tex);
        }
        return new Animation(frameDuration, regions);
    }

    /**
     * Loads an Animation from a Spritesheet
     * @param filename
     * @param frameDuration
     * @param width
     * @param height
     * @return
     */
    public Animation loadAnimation(String filename, float frameDuration, int width, int height) {
        Texture tex = game.getAssetManager().get(filename);
        int h = tex.getHeight()/height;
        int w = tex.getWidth()/width;

        TextureRegion[] regions = new TextureRegion[w*h];

        for (int i = 0; i<w; i++){
            for (int j = 0; j<h; j++){
                tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                regions[i+(j*w)]= new TextureRegion(tex, i*width, j*height, width, height);
            }
        }
        return new Animation(frameDuration, regions);
    }
}
