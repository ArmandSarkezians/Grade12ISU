package ISU;

import java.awt.*;

class Mummy extends Mob{
    private int health;
    private static Image mobImage;
    private int location;

    protected int getHealth() {
        return health;}

    protected Image getImage() {
        return mobImage;}

    protected int decreaseLocation(){
        return location - 1;
    }

    protected Mummy() {
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Mummy.png"); // same zombie image for each zombie
        location = 1000;
    }
}


