package ISU;

import java.awt.*;

class Zombie extends Mob{
    private int health;
    private static Image mobImage;

    protected int getHealth() {
        return health;}

    protected Image getImage() {
        return mobImage;}

    protected Zombie(){
        health = 100;
        mobImage = Toolkit.getDefaultToolkit().getImage("Zombie.png");} // same zombie image for each zombie

}
