// Armand Sarkezians
// June 14th 2019
// This is the parent class for all monsters, used in order to make the linked list in the main class vague in terms of which object it uses


package ISU;

import java.awt.*;

abstract class Mob {

    abstract Image getImage ();

    abstract void setLocation (int loc);

    abstract int getLocation ();

    abstract void decreaseLocation ();

    abstract int getHeightLoc();

    abstract void setHeightLoc(int a);

    abstract int getHealth();

    abstract void setHealth(int a);


}
