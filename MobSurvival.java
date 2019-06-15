//Armand Sarkezians
// Started May 18th 2019, Finished June 14th, 2019
// Mob Survival game

package ISU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class MobSurvival extends JPanel implements MouseListener, KeyListener{
    private int screen = 1; // which screen is being shown, 1 for main, 2 for information, 3 for game
    private int x = 100; // x position of player
    private int y = 300; // y position of player
    private int round = 1; // round number
    private  LinkedList <Mob> listOfMobs = new LinkedList <> (); // Linked List of mobs, size is 1 for round 1, 2 for round 2, 3 for round 3, etc. (its a linked list as i need to remove mobs randomly, arrayList would remove from the middle which is inefficient)
    private int health = 1; // health starts at 25, subtract by 1 for each mob that goes through

    // No Parametors (constructor)
    // No return value (constructor)
    // This method constructs the frame and panel
    private MobSurvival (){ // constructor
        setPreferredSize(new Dimension (1000, 600)); // setting dimensions
        setFocusable(true); // all images will be drawn on this panel
        addMouseListener(this); // adding mouse listener
        addKeyListener(this); // adding key listener
    }

    //Graphics parameter, used for drawing
    // Void return value, all drawing is on screen
    // This method draw all of the screens
    public void paint(Graphics g) {
        if (screen == 1) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage("main.png"), 0, 0, this); // main screen
        }else if (screen == 2){
            g.drawImage (Toolkit.getDefaultToolkit().getImage("Information.png"), 0, 0,  this); // information screen
            g.drawImage (Toolkit.getDefaultToolkit().getImage("Back.png"), 900, 540, this);
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

    // MouseEvent e parameter used to find the x position and y position of the click
    // Void return value as the variables are not needed elsewhere in this class
    // This method checks to see where the user clicked the mouse and runs code based on that position
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
                screen = 2;
                repaint(); }
        }else if (screen == 2){ // information screen
            if (xPos >= 900 && xPos <= 1000 && yPos >= 540 && yPos <= 600){ // Back Button
                screen = 1;
                repaint();
            }
        }else if (screen == 3){ // shooting
            startShoot();
        }else if (screen == 7){
            System.exit (0); // if u click after dying the game will exit
        }
    }

    // KeyEvent e is a parameter as the input value from the keyboard must be known (which key was pressed)
    // Void return valye as the variables are not needed elsewhere in this class
    // This method moves the main character based on the input from the user
    public void keyPressed (KeyEvent e){
        int m = e.getKeyCode();
        if ((m == 'W' || m == KeyEvent.VK_UP) && y >= 0){ // up
            y-=4;
        }else if ((m == 'S' || m == KeyEvent.VK_DOWN) && y <= 530){ // down
            y+=4;
        }else if ((m == 'D' || m == KeyEvent.VK_RIGHT) && x <= 950){ // left
            x+=4;
        }else if ((m == 'A' || m == KeyEvent.VK_LEFT) && x >= 0){ // right
            x-=4;
        }
    }

    // No parameters as this method calls a thread
    // Void return value, a thread is called and runs automatically, no need to return a value
    // This method calls the main character thread to start, so the main character can start moving
    private void startMain(){
        Thread t = new Thread (new DrawMain()); // starts the DrawMain thread
        t.start();
    }

    // No parameters as this method calls a thread
    // Void return value, a thread is called and runs automatically, no need to return a value
    //This method calls the mobs to start spawning, so the game can begin
    private void startMobs(){
        Thread t = new Thread(new SpawningMobs()); // starts SpawningMob thread
        t.start();
    }

    // No parameters as this method calls a thread
    // Void return value, a thread is called and runs automatically, no need to return a value
    // This method is called when the user shoots, allows for the mobs to be killed
    private void startShoot(){
        Thread t = new Thread(new Shooting()); // starts Shooting Thread
        t.start();
    }

    // This thread has no parameters
    // This thread has no return value
    // This thread spawns the mobs, adds to the round value and checks to see if the user has lost yet
    public class SpawningMobs implements Runnable{
        public void run(){
            while (health != 0){ // if health == 0 then dont run the program anymore
                Graphics g = getGraphics();
                g.drawString ("Health: " + health + "\t Round: " + round, 10, 595); // drawing round number and health
                int numOfMobsRandom;
                if ((round - 2) <= 1){
                    numOfMobsRandom = (int)(Math.random() * (round - 1) + 1); // between rounds 1 and 3 theres a random chance to get 1, 2, or 3 mobs (meaning round 3 can have only 1 mob)
                }else{
                    numOfMobsRandom = (int)(Math.random() * (round - (round - 2)) + (round - 2)); // As an example, if the round is 20, you can get 20 mobs, 19 mobs or 18 mobs, no less (as that would be too easy)
                }
                for (int numOfMobs = 0; numOfMobs < numOfMobsRandom; numOfMobs++){ // Finds out how many mobs need to be alive for each round
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
                }else if (round %  10 == 0){ // for final boss (every 10 rounds)
                    listOfMobs.add (new FinalBoss ());
                }
                g.setColor(Color.WHITE);



                for (int z = 0; z < listOfMobs.size(); z++){
                    listOfMobs.get(z).setLocation((int)(Math.random() * ((1000 + round * 50) - 1000) + 1000)); // setting locations of mobs
                    if (listOfMobs.get(z).getLocation() % 2 != 0){
                        listOfMobs.get(z).setLocation(listOfMobs.get(z).getLocation() - 1); // making sure all numbers are even (so that sharks (which move by 2) will always hit the check line at x = 60 for decreasing health)
                    }
                    listOfMobs.get(z).setHeightLoc((int)(Math.random() * (500 - 30) + 30));
                }

                int largest = 0;
                for (int z = 0; z < listOfMobs.size(); z++){ // finding largest location so that I can start from there for my for loop, to ensure all mobs cross the screen
                    if (listOfMobs.get(z).getLocation() > largest){
                        largest = (listOfMobs.get(z).getLocation());
                    }
                }


                for (int z = largest; z >= -50; z--){ // looping from right side of screen to the left side

                    for (int a = 0; a < listOfMobs.size(); a++){ // looping through all mobs
                        if (listOfMobs.get(a).getHealth() > 0) {
                            g.drawImage(listOfMobs.get(a).getImage(), listOfMobs.get(a).getLocation(), listOfMobs.get(a).getHeightLoc(), null); // if the mob has health draw it

                        }else{
                            g.setColor(Color.WHITE);
                            g.fillRect (listOfMobs.get(a).getLocation(), listOfMobs.get(a).getHeightLoc(), 50, 50 );
                            listOfMobs.remove(a); // if the mob doesn't have health remove it
                            break;
                        }


                        if (listOfMobs.get(a).getLocation() < 10 && listOfMobs.get(a).getLocation() >= 0){ // redraw black line if a mob crosses over it
                            g.setColor (Color.BLACK);
                            g.fillRect (50 + listOfMobs.get(a).getLocation() , listOfMobs.get(a).getHeightLoc(), 10 - listOfMobs.get(a).getLocation(), 50);
                            g.setColor (Color.WHITE);
                        }

                        if (listOfMobs.get(a).getLocation() == 60) { // if a mob hits the black line decreases health and updates health
                            health--;
                            g.setColor(Color.WHITE);
                            g.fillRect(0, 580, 1000, 20);
                            g.setColor(Color.BLACK);
                            g.drawString("Health: " + health + "\t Round: " + round, 10, 595);
                        }

                        if (health == 0){ // if game is  over (health is 0)
                            delay (500);
                            screen = 7;
                            g.setColor (Color.WHITE);
                            g.fillRect (0, 0, 1000, 600);
                            g.setColor (Color.BLACK);
                            g.drawString ("You died (Click to Close)", 480, 300);

                            String name = JOptionPane.showInputDialog(null, "Enter In Your Name"); // getting name

                            try{ // keeping track of scores
                                PrintWriter outFile = new PrintWriter (new FileWriter ("TopScores.txt", true)); // writing top score to file
                                outFile.println (name + " " + round);
                                outFile.close();

                                HashMap <String, Integer> scores = new HashMap<>(); // saving scores to hash map
                                Scanner  inFile = new Scanner (new File ("TopScores.txt"));
                                while (inFile.hasNextLine()){
                                    StringTokenizer st = new StringTokenizer(inFile.nextLine()); // getting scores and putting them in a list
                                    try{
                                        scores.put (st.nextToken(), Integer.parseInt (st.nextToken()));
                                    }catch (NoSuchElementException e){}
                                    
                                }

                              String totalScores = "Top Scores:\n";
                                for (Map.Entry<String, Integer> entry : scores.entrySet()) { // getting ready to print list
                                    totalScores += entry.getKey() + ": " + entry.getValue() + "\n";
                                }

                                JOptionPane.showMessageDialog(null, totalScores); // printing list of scores and names


                            }catch (IOException e){
                                System.out.println("Error writing to file.");
                            }

                            delay (2000);
                            System.exit(0);
                        }


                        listOfMobs.get(a).decreaseLocation(); // decrease the location of each mob
                    }

                    if (listOfMobs.size() == 0){ // if there are no more mobs left skip the rest of the for loop
                        z = -50;
                    }

                    delay(10); // delay for smoothness and slowness

                }


                delay(2000); // 2 second delay between rounds
                round++;
                listOfMobs.clear();
                g.setColor (Color.WHITE);
                g.fillRect (0, 580, 1000, 20);
                g.setColor (Color.BLACK);

            }
        }
    }

    // This thread has no parameters
    // This thread has no return value
    // This thread is constantly run for the main character to move
    public class DrawMain implements Runnable{
        public void run(){
            while (health != 0) {
                Image bufferMain = createImage(50, 50); // draw mario on offscreen image then draw on main
                Image main = Toolkit.getDefaultToolkit().getImage("mario.png");
                Graphics bufferMainG = bufferMain.getGraphics();
                bufferMainG.drawImage(main, 0, 0, 50, 50, null);

                Graphics g = getGraphics();
                g.drawImage(bufferMain, x, y, null); // draw image on main

                if (x <= 60){ // if main user is near the line it redraws the line
                    g.setColor (Color.BLACK);
                    g.fillRect (50, y - 4, 10, 58);
                }
            }
        }
    }

    // This thread has no parameters
    // This thread has no return value
    // This thread is called when the user clicks the mouse, a bullet is drawn and checks are made to see if the bullet hit a mob
    public class Shooting implements Runnable{
        public void run() {
            Image bufferShoot = createImage(20, 5); // draw bullet on offscreen image then draw on main
            Graphics bufferShootG = bufferShoot.getGraphics();
            bufferShootG.fillRect(10, 0, 10, 5);
            bufferShootG.setColor (Color.WHITE);
            bufferShootG.fillRect (0, 0, 10, 5);
            Graphics g = getGraphics();
            g.setColor(Color.WHITE);

            int yOrigin = y; // if the user moves the position of the bullet wont move
            if (health != 0) {
                for (int xShoot = x + 50; xShoot <= 1000; xShoot += 10) {
                    g.drawImage(bufferShoot, xShoot, yOrigin + 20, null);
                    for (int z = 0; z < listOfMobs.size(); z++) { // if the bullet hits any mob
                        if ((xShoot + 10) >= listOfMobs.get(z).getLocation() && (xShoot + 10) <listOfMobs.get(z).getLocation() + 50 && yOrigin + 20 >= listOfMobs.get(z).getHeightLoc() && yOrigin + 25 <= listOfMobs.get(z).getHeightLoc() + 50) {
                            listOfMobs.get(z).setHealth(listOfMobs.get(z).getHealth() - 50);
                            g.fillRect(xShoot, yOrigin, 10, 5);
                            xShoot = 1000; // skip the rest of the bullet travel if the bullet hits something
                        }
                    }

                    delay(1); // delay for slowness and smoothness
                }
            }
        }
    }

    // This method has no parameters
    // This method has a void return value as its drawing to a screen, therefore, it does not need to return anything
    // This method clears the screen and draws the black line to the left of the screen
    private void clear (){
        Graphics g = getGraphics(); // clears screen and draws black line
        g.setColor (Color.WHITE);
        g.fillRect (0, 0, 1000, 600);
        g.setColor (Color.BLACK);
        g.fillRect (50, 0, 10, 580);
    }

    // This method has a integer as its parameter as it needs to know how long to delay the program for
    // This method has a void return value as it delays the program, no need to return a value
    // This method delays a program for a certain amount of time, determined by the parameter
    private void delay (int millisec){
        try{ // Sleep program needs to be surrounded by a try/catch
            TimeUnit.MILLISECONDS.sleep(millisec);} // delays program for a certain number of milliseconds
        catch (InterruptedException e){}}

    public static void main (String [] args){
        JFrame frame = new JFrame ("Zombie Survival"); // create frame
        MobSurvival myPanel = new MobSurvival(); // create container
        myPanel.setLayout(null);
        frame.add(myPanel); // add panel to container
        frame.pack (); // pack them and set them visible
        frame.setVisible (true);

    }
}