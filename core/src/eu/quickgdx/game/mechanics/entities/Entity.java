package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.mechanics.entities.components.EntityComponent;
import eu.quickgdx.game.mechanics.entities.components.PositionComponent;
import eu.quickgdx.game.mechanics.entities.components.TextureComponent;

/**
 * Created by Veit on 03.05.2017.
 */
public class Entity {

    private HashMap<Class, EntityComponent> componentList = new HashMap<Class, EntityComponent>();
    public World world;

    public Entity(World world) {
        this.world = world;
    }

    public int addComponent(EntityComponent component){
        if(componentList.containsKey(component.getClass()))
            return 1;
        this.componentList.put(component.getClass(),component);
        if(world.componentEntityHashMap.containsKey(component.getClass())) {
            world.componentEntityHashMap.get(component.getClass()).add(this);
        }
        else {
            Array<Entity> entities = new Array<Entity>();
            entities.add(this);
            world.componentEntityHashMap.put(component.getClass(),entities);
        }
        return 0;
    }

    public int removeComponent(EntityComponent component){ //Empty Arrays are not removed. Is this practical?
        if(!componentList.containsKey(component.getClass()))
            return 1;
        this.componentList.remove(component.getClass());
        if(world.componentEntityHashMap.containsKey(component.getClass())) {
            world.componentEntityHashMap.get(component.getClass()).removeValue(this, false);
        }
        return 0;
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        spriteBatch.draw(((TextureComponent)componentList.get(TextureComponent.class)).texture, ((PositionComponent)componentList.get(PositionComponent.class)).position.x,((PositionComponent)componentList.get(PositionComponent.class)).position.y);
    }
}
