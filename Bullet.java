//package ISU;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class Bullet extends JPanel{
//    private int xPos;
//    private int yPos;
//
//    protected void newImage (){
//        Image bufferShoot = createImage(20, 5);
//        Graphics bufferShootG = bufferShoot.getGraphics();
//        bufferShootG.fillRect(10, 0, 10, 5);
//        bufferShootG.setColor (Color.WHITE);
//        bufferShootG.fillRect (0, 0, 10, 5);
//        drawBullet (bufferShoot);
//    }
//
//    protected void drawBullet(Image bufferShoot){
//        int yPosOrigin = yPos;
//        Graphics g = getGraphics();
//        for (int xShoot = xPos + 50; xShoot <= 1000; xShoot+= 10) {
//            g.drawImage (bufferShoot, xShoot, yPosOrigin + 20, null);
//        }
//    }
//
//    protected void drawBullet2 (){
//        int yPosOrigin = yPos;
//        Graphics g = j.graphics();
//        Graphics h = j.graphics();
//        h.setColor (Color.WHITE);
//        for (int xShoot = xPos + 50; xShoot <= 1000; xShoot+= 10) {
//            g.fillRect (xShoot, yPosOrigin, 10, 5);
//            h.fillRect (xShoot - 10, yPosOrigin, 10, 5);
//        }
//    }
//
//
//
//
//    protected Bullet (int xPos, int yPos){
//        this.xPos = xPos;
//        this.yPos = yPos;
//    }
//
//
//}
