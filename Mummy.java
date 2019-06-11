package ISU;

import java.awt.*;

class Mummy extends Mob{
    private int health;
    private static Image mobImage;

    private int getHealth() {
        return health; }

    protected Image getImage() {
        return mobImage; }

    protected Mummy(){
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Mummy.png");} // same zombie image for each zombie

}


