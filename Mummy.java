package ISU;

import java.awt.*;

class Mummy extends Mob{
    private int health;
    private static Image mobImage;
    private int location;
    private int heightLoc;
    private boolean firstDead = false;

    protected boolean getDead (){
        return firstDead;
    }

    protected void setDead(boolean a){
        firstDead = a;
    }

    protected int getHealth() {
        return health;}

    protected void setHealth(int a){
        health = a;
    }

    protected Image getImage() {
        return mobImage;}

    protected void setLocation (int loc){
        location = loc;
    }

    protected int getLocation (){
        return location;
    }

    protected void decreaseLocation (){
        location --;
    }

    protected int getHeightLoc(){
        return heightLoc;
    }

    protected void setHeightLoc (int a){
        heightLoc = a;

    }

    protected Mummy() {
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Mummy2.png"); // same zombie image for each zombie
        location = 1000;
    }
}


