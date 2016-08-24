package eu.quickgdx.game.Mechanics.Entities;

import com.badlogic.gdx.math.Vector2;

import eu.quickgdx.game.Mechanics.World;

/**
 * Created by Veit on 07.02.2016.
 */
public abstract class MoveableObject extends GameObject {
    Vector2 direction;
    Float speed;
    Movement movement;
    Float movingTime;

    public MoveableObject(Vector2 position, World world) {
        super(position, world);
        movement = Movement.IDLE;
        movingTime = 0f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        movingTime+=delta;
    }

    void handleMovement(Float delta) {
        this.position.add(direction.nor().scl(speed*delta));
        if(!(direction.nor().scl(speed*delta).isZero())){
            changeMovementTo(Movement.MOVING);
        }
        else{
            changeMovementTo(Movement.IDLE);
        }
    }

    public void changeMovementTo(Movement movement){
        if(this.movement != movement){
            this.movement = movement;
            movingTime = 0f;
        }

    }

    public enum Movement {
        IDLE, MOVING
    }
}
