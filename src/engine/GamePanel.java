package engine;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.*;


import entities.*;
import environment.Block;


public class GamePanel extends Canvas implements Runnable {
    public Body abc;
    public boolean running;
    private String data = "";
    private JFrame frame;
    private Block[] a;


    private GamePanel() {

        a=new Block[7];




        frame = new JFrame("im bad at coding");
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(new Key());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();
        frame.setVisible(true);



    }

    public void init() {

        abc = new Body(400, 400, frame.getWidth(), frame.getHeight());
        Random r = new Random();
        for(int x=0;x<a.length;x++){
            a[x]=new Block((int)(Math.random()*1000+300),(int)(Math.random()*700+70), (int)(Math.random()*50+20),
                    (int)(Math.random()*70+50),new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math
                    .random()*255)
            ));
        }

        new Thread(this).start(); //thread runs run() method

    }

    public static void main(String[] args) { //driver
        new GamePanel().init();
    }


    public void run() {    //the thread in init() will run this ove and over
        double a, b;

        int targetframes = 80; //you can change this value accordingly ***************************************************************************
        running = true;
        long time;
        long now;
        long elapsed = 0;
        int updates = 0, frames = 0;

        /* You are not expected to understand this.*/

        while (running) {
            time = System.currentTimeMillis();
            try {
                Thread.sleep(1000 / targetframes);
            } catch (InterruptedException e) {
                System.out.println("shit something went wrong lmao");
            }


            update();
            render();


            frames++;
            now = System.currentTimeMillis();
            elapsed += now - time;

            if (elapsed >= 1000) {

                elapsed = 0;
                data = "FPS: " + frames;
                frames = 0;

            }


        }

    }





    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        /*I dont understand this graphics2d shit either*/

        g2d.setPaint(new GradientPaint(new Point(0,0),new Color(1.0f,.6f,.1f),new Point(this.getWidth(),this.getHeight()),new Color(.1f, .8f,
                1.0f)));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.green); //please for the love of god dont touch this stuff cause i dont know how it works
        g2d.drawString(data, this.getWidth() - 200, 50);

        for(Block d : a){
            d.draw(g2d);
        }
        abc.draw(g2d);

        g2d.dispose();

        bs.show();
    }





    public void update() {
        abc.update();
        for(Block d: a) {
            abc.collide(d);
        }
        //System.out.println(abc.getData());

    }


    class Key implements KeyListener {

        private final Set<Character> pressed = new HashSet<Character>();

        public void keyPressed(KeyEvent e) {
            pressed.add(e.getKeyChar());
            //if(!e.isShiftDown()) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                abc.moveL();

            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                abc.moveR();
                //System.out.println(e);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W) {
                if (abc.getyVol() == 0)
                    abc.jump();


            } else if(e.getKeyCode()== KeyEvent.VK_U) {
                abc.setLeft(true);
                abc.setFriction(.5);
            }


        }


        public void keyReleased(KeyEvent e) {
            pressed.remove(e.getKeyChar());
            if (pressed.size() == 0)
                abc.setFriction(.9);

        }


        public void keyTyped(KeyEvent e) {/* Not used */

        }

        public Set<Character> getPressed() {
            return pressed;
        }
    }


}

