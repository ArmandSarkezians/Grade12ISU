package ISU;

import java.awt.*;

class Skeleton extends Mob{
    private int health;
    private static Image mobImage;

    protected int getHealth() {
        return health; }

    protected Image getImage() {
        return mobImage; }

    protected Skeleton(){
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Skeleton.png");} // same zombie image for each zombie

}

