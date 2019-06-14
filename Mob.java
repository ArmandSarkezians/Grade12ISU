package ISU;

import java.awt.*;

abstract class Mob {

    abstract Image getImage ();

    abstract boolean getDead();

    abstract void setDead(boolean a);

    abstract void setLocation (int loc);

    abstract int getLocation ();

    abstract void decreaseLocation ();

    abstract int getHeightLoc();

    abstract void setHeightLoc(int a);

    abstract int getHealth();

    abstract void setHealth(int a);


}
