package ISU;

import java.awt.*;

class Shark extends Mob{
    private int health;
    private static Image mobImage;

    private int getHealth() {
        return health; }

    protected Image getImage() {
        return mobImage; }

    protected Shark(){
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Shark.png");} // same zombie image for each zombie

}


