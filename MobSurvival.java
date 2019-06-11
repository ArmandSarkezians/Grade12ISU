//Armand Sarkezians
// May 18th 2019
// Zombie Survival Game
//This is such trash

package ISU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class MobSurvival extends JPanel implements MouseListener, KeyListener{
    private int screen = 1;
    private int x = 500;
    private int y = 300;
    private int round = 1;

    private MobSurvival (){
        setPreferredSize(new Dimension (1000, 600));
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        Image menu = Toolkit.getDefaultToolkit ().getImage ("main.png"); // main image
        g.drawImage (menu, 0, 0, this);
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
        if (screen == 1) {
            if (xPos >= 47 && xPos <= 508 && yPos >= 283 && yPos <= 556){ // Play Button
                screen = 3;
                clear();
                startMain();
                startMobs();
            }else if (xPos >= 509 && xPos <= 949 && yPos >= 283 && yPos <= 556){ // Information Button
                informationDraw();
                screen = 2;
            }

        }else if (screen == 2){ // information screen
            if (xPos >= 900 && xPos <= 1000 && yPos >= 540 && yPos <= 600){ // Back Button
                screen = 1;
                repaint();
            }


        }else if (screen == 3){
            startShoot();
        }






//        if (screen == 1){
//            screen = 2;
//            clear();
//            startMain();
//            startMobs();
//        }else if (screen == 2){
//            startShoot();
//        }
    }

    private void informationDraw(){
        Graphics g = getGraphics();
        g.drawImage (Toolkit.getDefaultToolkit().getImage("Information.png"), 0, 0, this);
        g.drawImage (Toolkit.getDefaultToolkit().getImage("Back.png"), 900, 540, this);
    }

    private void startMain(){
        DrawMain thread = new DrawMain();
        Thread t = new Thread (thread);
        t.start();
    }

    private void startMobs(){
        SpawningMobs mt = new SpawningMobs();
        Thread t = new Thread(mt);
        t.start();
    }


    private void startShoot(){
        Shooting mt = new Shooting();
        Thread t = new Thread(mt);
        t.start();
    }

    public void keyPressed (KeyEvent e){
        int m = e.getKeyCode();
        if ((m == 'W' || m == KeyEvent.VK_UP) && y >= 0){
            y-=3;
        }else if ((m == 'S' || m == KeyEvent.VK_DOWN) && y <= 550){
            y+=3;
        }else if ((m == 'D' || m == KeyEvent.VK_RIGHT) && x <= 950){
            x+=3;
        }else if ((m == 'A' || m == KeyEvent.VK_LEFT) && x >= 0){
            x-=3;
        }
    }



    public class SpawningMobs implements Runnable{
        public void run(){
            while (true) {
                LinkedList <Mob> listOfMobs = new LinkedList <> (); // Linked List of mobs, size is 1 for round 1, 2 for round 2, 3 for round 3, etc.
                for (int numOfMobs = 0; numOfMobs < round; numOfMobs++){ // Fins out how many mobs need to be alive for each round
                    int random = (int)(Math.random() * (4 - 1) + 1);
                    if (random == 1){
                        listOfMobs.add (new Zombie ());
                    }else if (random == 2){
                         listOfMobs.add (new Skeleton ());
                    }else if (random == 3){
                         listOfMobs.add (new Mummy ());
                    }else if (random == 4){
                          listOfMobs.add (new Shark ());
                    }
                    if ((round % 5 == 0) && (round % 10 != 0)){ // for miniboss (only every 5 rounds)
                        listOfMobs.add (new MiniBoss ());
                    }else if (round %  10 == 0){ // for finalboss (every 1 rounds)
                        listOfMobs.add (new FinalBoss ());
                    }
                }
                Graphics g = getGraphics();
                g.setColor(Color.WHITE);



                for (int location = 1000; location >= 0; location--){
                    for (int spawnAllMobs = 0; spawnAllMobs < listOfMobs.size(); spawnAllMobs++){
                        g.drawImage (listOfMobs.get(spawnAllMobs).getImage(), location, spawnAllMobs * 50, null);
                        g.fillRect (location + 50, spawnAllMobs * 50, 50, 50);
                        delay (1);
                    }
                }
                delay (1000);
                round++;

//
//                for (int xMob = 1000; xMob >= 0; xMob--) {
//
//                    bufferG.drawImage(shark, 0, 0, null);
//                    bufferG.drawImage(sharkErase, 0, 0, null);
//                    g.drawImage(buffer, xMob, rand, null);
//
//
//                    if (xMob == 0){
//                        g.fillRect (xMob, rand, 50, 50);
//                    }
//                }
//
//                delay(2000);
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
                    g.fillRect (50, 0, 10, 600);
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
            int yPosOrigin = y;
            for (int xShoot = x + 50; xShoot <= 1000; xShoot+= 10) {
                g.drawImage (bufferShoot, xShoot, yPosOrigin + 20, null);
                delay (1);
            }
        }
    }

    private void clear (){
        Graphics g = getGraphics();
        g.setColor (Color.WHITE);
        g.fillRect (0, 0, 1000, 600);
        g.setColor (Color.BLACK);
        g.fillRect (50, 0, 10, 600);
    }

    private void delay (int millisec){
        try{ // Sleep program needs to be surrounded by a try/catch
            TimeUnit.MILLISECONDS.sleep(millisec);} // delays program for a certain number of milliseconds
        catch (InterruptedException e){}}

    public static void main (String [] args){
        JFrame frame = new JFrame ("Zombie Survival");
        MobSurvival myPanel = new MobSurvival();
        frame.add(myPanel);
        frame.pack ();
        frame.setVisible (true);

    }
}