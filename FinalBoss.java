// Armand Sarkezians
// June 14th 2019
// This is the final boss, spawns every 10 rounds

package ISU;

import java.awt.*;

public class FinalBoss extends Mob{
    private int health; // health of mob
    private static Image mobImage; // image of mob
    private int location; // x location of mob
    private int heightLoc; // y location of mob

    // getters
    protected int getHealth() { return health;}

    protected Image getImage() { return mobImage;}

    protected int getLocation (){
        return location;
    }

    protected int getHeightLoc(){
        return heightLoc;
    }

    // setters
    protected void setHealth(int a){
        health = a;
    }

    protected void setHeightLoc (int a){ heightLoc = a; }

    protected void setLocation (int loc){
        location = loc;
    }


    //This method has no parameters
    // This method has a void return value
    // This method only changes the location of this specific mob, allows for mobs to have different locations
    protected void decreaseLocation (){
        location --;
    }


    protected FinalBoss() {
        health = 500; // same starting health for each finalboss
        mobImage = Toolkit.getDefaultToolkit().getImage("FinalBoss.png"); // same finalboss image for each finalboss
    }
}

