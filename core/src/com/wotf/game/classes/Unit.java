package com.wotf.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.wotf.game.classes.Items.Item;
import com.wotf.game.GameStage;

/**
 * Created by Wessel on 14/03/2016.
 */
public class Unit extends Actor {
    private float angle;
    public float force = 3f;
    private Vector2 acceleration;
    private TextureRegion unitStand;

    public float speed = 50 * 2;
    public Vector2 velocity = new Vector2();
    boolean moveRight;

    private int health;
    private String name;

    public Sprite sprite;
    private Vector2 position;

    private Item weapon;
    private Team team;

    // Font is used for displaying name and health
    private static BitmapFont font = new BitmapFont();

    public Unit(String name, int health, Team team) {
        this.name = name;
        this.health = health;
        this.team = team;

        moveRight = true;

        Texture spriteSheet = new Texture(Gdx.files.internal("unit.png"));
        
        font.setColor(Color.BLACK);

        sprite = new Sprite(spriteSheet);
        unitStand = sprite;
        //sprite.setRegion(spriteSheet);

        this.setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());

        // Temporary input listener
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.RIGHT) {
                    moveRight = true;
                }

                if (keycode == Keys.LEFT) {
                    moveRight = false;
                }

                if (keycode == Keys.UP) {
                    jump();
                }

                //switching between weapons
                if (keycode == Keys.NUM_1) {
                    // TODO: switch weapon A
                    // In Team's weaponlist choose weapon cooresponding to numbers & make weapon activated
                    Item i = team.selectItem(0);
                    if (i != null) {
                        weapon = i;
                        //TODO: WHAT BUTTON PRESSED TO FIRE WEAPON???????
                        // Wessel: Probably the spacebar button, like in worms
                        //FIRE & USE LOGIC + initiate projectile funtion --> TODO JIP z'n PROJECTIEL FUNCTIE
                        useItem();

                        System.out.println("GRENADE");
                    } else {
                        System.out.println("Selected weapon not found");
                    }
                }

                if (keycode == Keys.NUM_2) {
                    // TODO: switch weapon B
                    // In Team's weaponlist choose weapon cooresponding to numbers
                    // make weapon activated
                    Item i = team.selectItem(1);
                    if (i != null) {
                        weapon = i;
                        //TODO: WHAT BUTTON PRESSED TO FIRE WEAPON???????
                        //FIRE & USE LOGIC + initiate projectile funtion --> TODO JIP z'n PROJECTIEL FUNCTIE
                        useItem();

                        System.out.println("BAZOOKA");
                    } else {
                        System.out.println("Selected weapon not found");
                    }
                }

                if (keycode == Keys.SPACE) {
                    // TODO: Logic for firing weapon
                    // should be moved when shooting logic requires more than a single button down event
                    System.out.println("Firing " + weapon.getName());
                    useItem();
                }

                return true;
            }
        });
    }

    public Unit(String name, int health, Team team, Vector2 position) {
        this(name, health, team);
        this.position = position;
        this.velocity = new Vector2(0, 0);
    }

    public int getHealth() {
        return health;
    }

    public void increaseHealth(int amount) {
        health += amount;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
        // TODO: checking if health <= 0
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns the name associated with this unit
     * @return String containing the name of this unit
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a rectangle representing the bounds of unit
     * @return Rectangle based on X, Y, Width and Height of unit
     */
    public Rectangle getBounds() {
        return this.sprite.getBoundingRectangle();
    }

    /**
     * Spawns a unit at the specified location
     * @param position Position to spawn the unit at
     */
    public void spawn(Vector2 position) {
        // logic for spawning
        this.setPosition(position.x, position.y);
        this.position = position;
        positionChanged();
    }

    @Override
    public void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);

        // Draws the name and current health of the unit above its sprite
        font.draw(batch, String.format("%s (%d)", name, health), getX(), getY() + getHeight() + 20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setRegion(getFrame(delta));
        updateJump();
    }

    public void jump() {
        System.out.println("V= " + velocity.x);
        float nextX;

        if (moveRight) {
            nextX = position.x + 20;
        } else {
            nextX = position.x - 20;
        }

        setAngle(position, new Vector2(nextX, position.y + 20));
        setAcceleration(9.8);
        setVelocity(force);
    }

    public void updateJump() {
        if (acceleration == null) {
            return;
        }

        float delta = Gdx.graphics.getDeltaTime();
        
        //keep old position to change rotation of object
        Vector2 oldPos = position.cpy();

        position.x += velocity.x;
        position.y += velocity.y;

        setAngle(oldPos, position);

        velocity.x += acceleration.x * delta;
        velocity.y += acceleration.y * delta;

        //System.out.println(velocity.toString());
        this.setPosition(position.x, position.y);
        positionChanged();
        
        if(!moveRight){
            boolean isSolidX = ((GameStage) getStage()).isPixelSolid((int) position.x - 1, (int) position.y);
            if (isSolidX) {
                velocity.x = 0;
            } 
        }else{
            boolean isSolidX = ((GameStage) getStage()).isPixelSolid((int) position.x + (int)(sprite.getWidth() / 2), (int) position.y);
            if (isSolidX) {
                velocity.x = 0;
            } 
        }

        boolean isSolidY = ((GameStage) getStage()).isPixelSolid((int) position.x, (int) position.y - 1);
        if (isSolidY) {
            velocity.y = 0;
        }
        
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void useItem() {
        //TODO: Jip projectiel functie koppelen aan impact en daarmee de activate aanroepen
        //wapens ontploffen nu bij activate (suicide bombers)
        weapon.activate();
        team.decreaseItemAmount(weapon, 1);
    }

    /**
     * Calculate and set the angle by 2 vectors.
     *
     * @param startPos first x,y position.
     * @param destPos second x, y position.
     */
    private void setAngle(Vector2 startPos, Vector2 destPos) {
        angle = (float) Math.toDegrees(
                Math.atan2(
                        destPos.y - startPos.y,
                        destPos.x - startPos.x
                )
        );
    }

    private void setAcceleration(double gravity) {
        acceleration = new Vector2();
        //account for gravity
        acceleration.y -= gravity;
    }

    private void setVelocity(float force) {
        final double DEG2RAD = Math.PI / 180;
        double ang = angle * DEG2RAD;

        velocity = new Vector2(
                (float) (force * Math.cos(ang)),
                (float) (force * Math.sin(ang))
        );
    }
    /**
     * 
     * @param dt
     * @return 
     */
    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = unitStand;
        //if unit is turn left and the texture isnt facing left... flip it.
        if (!moveRight && !region.isFlipX()) {
            region.flip(true, false);
            moveRight = false;
        } //if unit is turn right and the texture isnt facing right... flip it.
        else if (moveRight && region.isFlipX()) {
            region.flip(true, false);
            moveRight = true;
        }
        return region;
    }
}
