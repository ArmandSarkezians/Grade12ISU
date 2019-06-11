package ISU;

import java.awt.*;

public class MiniBoss extends Mob {

    private int health;
    private static Image mobImage;

    private int getHealth() {
        return health; }

    protected Image getImage() {
        return mobImage; }

    protected MiniBoss(){
        health = 1000;
        mobImage = Toolkit.getDefaultToolkit().getImage("MiniBoss.png");} // same zombie image for each zombie

}

