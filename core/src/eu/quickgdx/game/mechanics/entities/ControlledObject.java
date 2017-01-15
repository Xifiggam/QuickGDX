package eu.quickgdx.game.mechanics.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eu.quickgdx.game.mechanics.World;

/**
 * Gives you an simple object controlled by the user
 * Created by Veit on 06.02.2016.
 */
public class ControlledObject extends MoveableObject {
    //    private TextureRegion[] regions = new TextureRegion[12];
    private Vector3 touchCoordinates = new Vector3(0, 0, 0);

    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int heading; // 1 - UP, 2 - Right, 3 - Down, 4 - Left
    private Animation idleAnimation;
    private Animation movingUpAnimation;
    private Animation movingDownAnimation;
    private Animation movingSideAnimation;
    private TextureRegion frame;

    private boolean cameraFollow = true; // If this flag is true the camera will follow this Object (Not tested with multiple conrolledObjects)

    public ControlledObject(Vector2 position, World world) {
        super(position, world);
        world.gameplayScreen.gameCam.position.x = position.x;
        world.gameplayScreen.gameCam.position.y = position.y;
        this.bounds = new Rectangle(position.x + 10, position.y, 25, 20);
        System.out.println(this.bounds);
        this.speed = 10f;
        this.hitpoints = 5;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
//        this.texture = world.gameplayScreen.parentGame.getAssetManager().get("gameplay/spritesheet.png");
//        for (int i = 0; i<3; i++){
//            for (int j = 0; j<4; j++){
//                regions[i+(j*3)]= new TextureRegion(texture, i*46, j*64, 46, 64);
//            }
//        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();
        handleMovement(delta);

    }

    @Override
    void handleMovement(Float delta) {
        calcDirection();
        Vector2 newPosition = new Vector2(Math.round(position.x), Math.round(position.y));
        newPosition.add(direction.nor().scl(speed));
        Rectangle newBounds = new Rectangle(newPosition.x + 10, newPosition.y, 25, 20);
        for (int j = 0; j < world.gameObjects.size; j++) {
            GameObject gameObject = world.gameObjects.get(j);
            if (gameObject.bounds != null) {
                if (gameObject.bounds.overlaps(newBounds)) {
                    if (gameObject.getClass() != this.getClass())
                        return;
                }
            }
        }
        this.bounds = newBounds;

        this.position.add(direction.nor().scl(speed));
        if (cameraFollow) {
            cameraFollow(direction.nor().scl(speed));
        }
        if (!direction.nor().isZero()) {
            if (direction.x > 0) {
                heading = 2;
            } else if (direction.x < 0) {
                heading = 4;
            }
            if (direction.y > 0) {
                heading = 1;
            } else if (direction.y < 0) {
                heading = 3;
            }
            movement = Movement.MOVING;
        } else {
            movement = Movement.IDLE;
        }
    }


    /**
     * Calculates the direction Vector
     */
    private void calcDirection() {
        direction = new Vector2(0, 0);
        if (moveUp && !moveDown) {
            direction.y = 1;
        } else if (!moveUp && moveDown) {
            direction.y = -1;
        }

        if (moveLeft && !moveRight) {
            direction.x = -1;
        } else if (!moveLeft && moveRight) {
            direction.x = 1;
        }
        moveDown = moveUp = moveRight = moveLeft = false;
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight = true;
        }

        if (Gdx.input.justTouched()) {
            touch(world.gameplayScreen.gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1)));
        }
    }

    /**
     * Your typical render function
     *
     * @param delta
     * @param spriteBatch heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down, 4 - Left
     */
    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case 1:
                frame = movingUpAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 2:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 3:
                frame = movingDownAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case 4:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            default:
                frame = idleAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
        }
    }

    public void touch(Vector3 touchCoordinates) {
        this.touchCoordinates = touchCoordinates;
    }

    public void cameraFollow(Vector2 vector) {
        world.gameplayScreen.gameCam.translate(vector);
    }
}
