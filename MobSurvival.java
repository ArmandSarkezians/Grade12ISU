//Armand Sarkezians
// May 18th 2019
// Mob Survival game

package ISU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class MobSurvival extends JPanel implements MouseListener, KeyListener{
    private int screen = 1; // which screen is being shown, 1 for main, 2 for information, 3 for game
    private int x = 100; // x position of player
    private int y = 300; // y position of player
    private int round = 1; // round number
    private  LinkedList <Mob> listOfMobs = new LinkedList <> (); // Linked List of mobs, size is 1 for round 1, 2 for round 2, 3 for round 3, etc.
    private int health = 25;


    private MobSurvival (){ // constructor
        setPreferredSize(new Dimension (1000, 600));
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        if (screen == 1) {
            Image menu = Toolkit.getDefaultToolkit().getImage("main.png"); // main image
            g.drawImage(menu, 0, 0, this);
        }else if (screen == 2){ // I cant create a new method to draw this? It doesnt work for some reason
            g.drawImage (Toolkit.getDefaultToolkit().getImage("Information.png"), 0, 0,  this);
            g.drawImage (Toolkit.getDefaultToolkit().getImage("Back.png"), 900, 540, this);
        }else if (screen == 4){
            g.drawImage (Toolkit.getDefaultToolkit().getImage("sara.jpg"), 0, 0, 282, 502,  this);
            g.drawString ("There's no way to get out of this page u gotta restart the program", 0, 580);
        }
    }

    //Must have this for Mouse Listener to work
    public void mousePressed(MouseEvent e) {}

    //Must have this for Mouse Listener to work
    public void mouseReleased(MouseEvent e) {}

    //Must have this for Mouse Listener to work
    public void mouseEntered(MouseEvent e) {}

    //Must have this for Mouse Listener to work
    public void mouseExited(MouseEvent e) {}

    //Must have this for Mouse Listener to work
    public void keyReleased (KeyEvent e){}

    //Must have this for Mouse Listener to work
    public void keyTyped (KeyEvent e){}

    public void mouseClicked(MouseEvent e) {
        int xPos = e.getX();
        int yPos = e.getY();
        System.out.println(xPos + ":" + yPos);
        if (screen == 1) {
            if (xPos >= 47 && xPos <= 508 && yPos >= 283 && yPos <= 556){ // Play Button
                screen = 3;
                clear();
                startMain();
                startMobs();
            }else if (xPos >= 509 && xPos <= 949 && yPos >= 283 && yPos <= 556){ // Information Button
                screen = 2;
                repaint();
            }else if(xPos >= 857 && xPos <= 959 && yPos >= 67 && yPos <= 80){ // sara button
                screen = 4;
                repaint();
            }

        }else if (screen == 2){ // information screen
            if (xPos >= 900 && xPos <= 1000 && yPos >= 540 && yPos <= 600){ // Back Button
                screen = 1;
                repaint();
            }

        }else if (screen == 3){
            startShoot();
        }else if (screen == 4){
            repaint();
        }
    }

    private void startMain(){
        Thread t = new Thread (new DrawMain());
        t.start();
    }

    private void startMobs(){
        Thread t = new Thread(new SpawningMobs());
        t.start();
    }

    private void startShoot(){
        Thread t = new Thread(new Shooting());
        t.start();
    }

    public void keyPressed (KeyEvent e){
        int m = e.getKeyCode();
        if ((m == 'W' || m == KeyEvent.VK_UP) && y >= 0){
            y-=4;
        }else if ((m == 'S' || m == KeyEvent.VK_DOWN) && y <= 530){
            y+=4;
        }else if ((m == 'D' || m == KeyEvent.VK_RIGHT) && x <= 950){
            x+=4;
        }else if ((m == 'A' || m == KeyEvent.VK_LEFT) && x >= 0){
            x-=4;
        }
    }


    public class SpawningMobs implements Runnable{
        public void run(){
            while (true){
                Graphics g = getGraphics();
                g.drawString ("Health: " + health + "\t Round: " + round, 10, 600);
                int numOfMobsRandom;
                if ((round - 2) <= 1){
                    numOfMobsRandom = (int)(Math.random() * (round - 1) + 1); // between rounds 1 and 3 theres a random chance to get 1, 2, or 3 mobs (meaning round 3 can have only 1 mob)
                }else{
                    numOfMobsRandom = (int)(Math.random() * (round - (round - 2)) + (round - 2)); // As an example, if the round is 20, you can get 20 mobs, 19 mobs or 18 mobs, no less (as that would be too easy)
                }
                for (int numOfMobs = 0; numOfMobs < numOfMobsRandom; numOfMobs++){ // Fins out how many mobs need to be alive for each round
                    int random = (int)(Math.random() * (5 - 1) + 1);
                    if (random == 1){
                        listOfMobs.add (new Zombie ());
                    }else if (random == 2){
                        listOfMobs.add (new Skeleton ());
                    }else if (random == 3){
                        listOfMobs.add (new Mummy ());
                    }else if (random == 4){
                        listOfMobs.add (new Shark ());
                    }
                }

                if ((round % 5 == 0) && (round % 10 != 0)){ // for mini boss (only every 5 rounds)
                    listOfMobs.add (new MiniBoss ());
                }else if (round %  10 == 0){ // for final boss (every 1 rounds)
                    listOfMobs.add (new FinalBoss ());
                }


                g.setColor(Color.WHITE);


                int [] randomLocation = new int [listOfMobs.size()];
                for (int z = 0; z < randomLocation.length; z++){
                    randomLocation [z] = (int)(Math.random() * ((1000 + round * 50) - 900) + 900);
                    listOfMobs.get(z).setLocation(randomLocation[z]);
                    listOfMobs.get(z).setHeightLoc((int)(Math.random() * (500 - 30) + 30));
                }



                int largest = 0;
                for (int z = 0; z < randomLocation.length; z++){
                    if (randomLocation [z] > largest){
                        largest = randomLocation[z];
                    }
                }

                boolean allDead = false; // LAST MOB IS NOT ERASED FOR SOME REASON
                for (int z = largest; z >= -50; z--){
                    for (int a = 0; a < listOfMobs.size(); a++){
                        if (listOfMobs.get(a).getHealth() > 0) {
                            g.drawImage(listOfMobs.get(a).getImage(), listOfMobs.get(a).getLocation(), listOfMobs.get(a).getHeightLoc(), null);
                        }else if (!listOfMobs.get(a).getDead()){
                            g.setColor(Color.RED);
                            g.fillRect (listOfMobs.get(a).getLocation(), listOfMobs.get(a).getHeightLoc(), 50, 50 );
                            listOfMobs.get(a).setDead(true); // only draws a white box around dead mob once
                        }
                        if (listOfMobs.get(a).getLocation() < 10 && listOfMobs.get(a).getLocation() >= 0){ // redraw black line
                            g.setColor (Color.BLACK);
                            g.fillRect (50 + listOfMobs.get(a).getLocation() , a * 50, 10 - listOfMobs.get(a).getLocation(), a * 50 + 50);
                            g.setColor (Color.WHITE);
                        }

                        System.out.println (listOfMobs.get(0).getLocation());
                        if (listOfMobs.get(a).getLocation() == 60){
                            health--;
                            g.setColor (Color.WHITE);
                            g.fillRect (0, 590, 1000, 10);
                            g.setColor (Color.BLACK);
                            g.drawString ("Health: " + health + "\t Round: " + round, 10, 600);
                        }


                        listOfMobs.get(a).decreaseLocation();
                    }
                    delay(10);
                    allDead = true;
                    for (int b = 0; b < listOfMobs.size(); b++){ // if all dead skip this round and go to the next round
                        if (listOfMobs.get(b).getHealth() > 0){
                            allDead = false;
                        }
                    }
                    if (allDead){
                        z = -50;
                    }
                }

                delay(2000);
                round++;
                listOfMobs.clear();
                g.setColor (Color.WHITE);
                g.fillRect (0, 590, 1000, 10);
                g.setColor (Color.BLACK);
                g.drawString ("Health: " + health + "\t Round: " + round, 10, 600);

            }
        }
    }

    public class DrawMain implements Runnable{
        public void run(){
            while (true) {
                Image bufferMain = createImage(50, 50);
                Image main = Toolkit.getDefaultToolkit().getImage("mario.png");
                Graphics bufferMainG = bufferMain.getGraphics();
                bufferMainG.drawImage(main, 0, 0, 50, 50, null);

                Graphics g = getGraphics();
                g.drawImage(bufferMain, x, y, null);

                if (x <= 60){ // if main user is near the line it redraws the line
                    g.setColor (Color.BLACK);
                    g.fillRect (50, y - 4, 10, 58);
                }
            }
        }
    }

    public class Shooting implements Runnable{
        public void run() {
            Image bufferShoot = createImage(20, 5);
            Graphics bufferShootG = bufferShoot.getGraphics();
            bufferShootG.fillRect(10, 0, 10, 5);
            bufferShootG.setColor (Color.WHITE);
            bufferShootG.fillRect (0, 0, 10, 5);
            Graphics g = getGraphics();

            int yOrigin = y;
            for (int xShoot = x + 50; xShoot <= 1000; xShoot+= 10) {
                g.drawImage (bufferShoot, xShoot, yOrigin + 20, null);
                for (int z = 0; z < listOfMobs.size(); z++){
                    if ((xShoot + 10) >= listOfMobs.get(z).getLocation() && y + 20 >= listOfMobs.get(z).getHeightLoc() && y + 25 <= listOfMobs.get(z).getHeightLoc() + 50){
                        listOfMobs.get(z).setHealth (listOfMobs.get(z).getHealth() - 50);
                    }
                }

                delay (1);
            }
        }
    }


    private void clear (){
        Graphics g = getGraphics();
        g.setColor (Color.WHITE);
        g.fillRect (0, 0, 1000, 600);
        g.setColor (Color.BLACK);
        g.fillRect (50, 0, 10, 590);
    }

    private void delay (int millisec){
        try{ // Sleep program needs to be surrounded by a try/catch
            TimeUnit.MILLISECONDS.sleep(millisec);} // delays program for a certain number of milliseconds
        catch (InterruptedException e){}}

    public static void main (String [] args){
        JFrame frame = new JFrame ("Zombie Survival");
        MobSurvival myPanel = new MobSurvival();
        myPanel.setLayout(null);
        frame.add(myPanel);
        frame.pack ();
        frame.setVisible (true);

    }
}