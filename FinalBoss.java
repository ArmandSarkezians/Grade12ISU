package ISU;

import java.awt.*;

public class FinalBoss extends Mob{
    private int health;
    private static Image mobImage;

    private int getHealth() {
        return health; }

    protected Image getImage() {
        return mobImage; }

    protected FinalBoss(){
        health = 1000;
        mobImage = Toolkit.getDefaultToolkit().getImage("FinalBoss.png");} // same zombie image for each zombie

}

