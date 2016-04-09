/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wotf.game.classes.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author chaos
 * The weapon classes need to be remodeeled in a later version of this game. 
 */
public class Bazooka extends MissileLauncher implements IExplosion {

    public Bazooka(String nm, int pw, int rad, int damage, Sprite weaponSprite, Sprite bulletSprite) {
        super(nm, pw, rad, damage, weaponSprite, bulletSprite);
    }

    /**
     * trigger the activation method of the object
     * @param position position from where it is fired
     * @param mousePos pisition where it fires too
     * @param Wind wind affection
     * @param grav  gravity affection
     */
    @Override
    public void activate(Vector2 position, Vector2 mousePos, Vector2 Wind, double grav) {
        super.activate(position, mousePos, Wind, grav );
    }

    /**
     * trigger the explosion method of the object
     */
    @Override
    public void explode() {
        
        // radius wordt bepaald door de radius functie hieronder
        int useMeAsRadius = radius();
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * size of the explosion
     * @return the radius of the explosion
     */
    @Override
    public int radius() {
        int rad = super.getBlastRadius();
        float power = super.getPower();
        Random r = new Random();
        int newRad = r.nextInt(rad) + 1;
        float newPower = r.nextFloat() * power;
        return (newRad + (int) (Math.random() * newPower));
    }

    /**
     * Method to cotnrol the blast and the objects affected by this explosion
     * @param distance distance of object to the explosion centre
     * @return the force an object is affected by
     */
    @Override
    public int blastControl(int distance) {
       /* //returns int with the power an object gets pushed backwards
        //direction is always from center of explosion through unit 
        //e.g. if unit on top, unit gets blown upwards
        //if unit on left, unit gets blown left etc. 

        int blastradius = super.getBlastRadius();
        float power = super.getPower();
        float force = power + blastradius;

        if (distance < force) {
            force -= distance;
            //working with vectors, we'd need:
            //explosion coords (1), and unit coords (2) for finding angle of explosion--> Tan2(x1-x2, y1-y2)*180/PI
            //force X --> force*cos(angle)      force Y --> force*sin(angle)
            return force;
        }
        return 0;*/
       throw new NotImplementedException();
    }

}
