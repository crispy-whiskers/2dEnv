package entities;

import environment.Block;
import java.awt.*;
import java.awt.geom.Line2D;

public class physObject extends Entity {
    public double friction;
    public double gravity;
    private int xVol;
    private double yVol;
    private double accel;
    private int boundX;

    private int boundY;     //fields are gay
    private int prevX;
    private int prevY;
    private Graphics savBuffer;
    private Block lastcol;

    private int prevMaxX,prevMaxY,prevCenterY;

    private Line2D.Double pathTL, pathTR, pathBR, pathBL;


    public physObject(int x, int y, int boundaryx, int boundaryy, int height, int width) {

        super(x,y,width,height); //will instantiate TLRB lines

        boundX = boundaryx; //screen boundaries: will remove later, perhaps
        boundY = boundaryy;
        accel = 1; //for the math

        setGravity(1.05); //    gravity 1.1: normal, earth gravity   1.05: game gravity (normal)   1.01: moon gravity

        pathTL = new Line2D.Double(getPrevX(), getPrevY(), getX(), getY());
        pathTR = new Line2D.Double(getPrevMaxX(), getPrevY(), getMaxX(), getY());            //linear path of the four corners
        pathBR = new Line2D.Double(getPrevMaxX(), getPrevMaxY(), getMaxX(), getMaxY());
        pathBL = new Line2D.Double(getPrevX(), getPrevMaxY(), getX(), getMaxY());




    }
    public void update(){
        physics();
        if (getX() < 0) {
            setxVol(5);
            setFriction(.9);
        } else if (getX() + getWidth() > boundX) {
            setxVol(-5);
            setFriction(.9);
        } else if (getY() + getHeight() > boundY) {    //screen boundaries
            setyVol(0);
            setyPos((int) (boundY - getHeight()));
        }
        prevX=(int)getX();
        prevY=(int)getY(); //previous position
        prevMaxX=(int)getMaxX();
        prevMaxY=(int)getMaxY();
        prevCenterY = (int)getCenterY();

        translate(xVol, (int) yVol);//move
    }

    public void draw(Graphics myBuffer) {
        savBuffer = myBuffer;
        myBuffer.setColor(Color.white); //draw on buffered image
        myBuffer.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());


        myBuffer.setColor(Color.cyan);
        drawLine(myBuffer,pathBL);
        drawLine(myBuffer,pathBR);
        myBuffer.setColor(Color.red);
        drawLine(myBuffer,pathTL);
        drawLine(myBuffer,pathTR);
        if(lastcol!=null)
            lastcol.draw(myBuffer);
    }



    private void updateBounds() {
        pathTL.setLine(getPrevX(), getPrevY(), getX(), getY());
        pathTR.setLine(getPrevMaxX(), getPrevY(), getMaxX(), getY());  //linear path of the four corners
        pathBR.setLine(getPrevMaxX(), getPrevMaxY(), getMaxX(), getMaxY());
        pathBL.setLine(getPrevX(), getPrevMaxY(), getX(), getMaxY()); //will update the location of these bois




    }


    public void physics() {      //probably the best method i have ever written. short, but powerful

        accel = (accel * getGravity() * getGravity() + .0001); //y-value math: Velocity=velocity+A*gravity^2. not legit math but it works very well
        if (yVol != 0)
            yVol = (yVol + accel); //for reference: set yvol to .1 or something to make it fall
        else
            accel = 0;//make sure obj doesnt move when standstill

        xVol = (int) (xVol * getFriction());
        if(Math.abs(yVol)>40) //50 is max speed
            yVol=40;

        updateBounds();

    }


    public void collide(Block e) {
        //formerly between block line and this path

        if (intersects(e)) {
            lastcol=e;








            if (e.getT().intersectsLine(getPathBL()) || e.getT().intersectsLine(getPathBR())) {
                setyPos((int) e.getY() - (int) getHeight());
                setyVol(0); //TOP COLL
                System.out.println("top of block");
            } else if (e.getB().intersectsLine(getPathTL()) || e.getB().intersectsLine(getPathTR())) {
                setyPos((int) e.getMaxY());
                setyVol(10); //BOTTOM COLL
                System.out.println("bottom of block");
            } else if (e.getR().intersectsLine(getPathTL()) || e.getR().intersectsLine(getPathBL())) {
                setxPos((int) e.getMaxX());
                setxVol(4); //RIGHT COLL
                setFriction(.9);
            } else if (e.getL().intersectsLine(getPathTR()) || e.getL().intersectsLine(getPathBR())) {
                setxPos((int) (e.getMinX() - getWidth()));
                setxVol(-5); //LEFT COLL
                setFriction(.9);

            } //todo try avik's collision system.
            /*

            On intersection (corner), say TR corner intersects with Body BL corner.
            Check distance between Body's R and Block's R. If distance is less than a certain
            threshold, the collision is on the right. If it is greater, the collision is on top.

            */


            //todo try alex L 's collision system

            /*
            *
            * Block is made out of multiple rectangles, one for each side. Whatever one it intersects first
            * is what side it collided on.
            *
            */
        }


        if (getMaxY() == e.getY()) {
            if (getX() > e.getMaxX() || getMaxX() < e.getX()) {
                setyVol(30);
                setAccel(1);

            }
        }

    }


    public String getData() {
        return "" + getX() + ", " + getY() + "\nmysize(wxh): " + getWidth() + "x" + getHeight();
    }

    public int getPrevMaxX() {
        return prevMaxX;
    }

    public int getPrevMaxY() {
        return prevMaxY;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public Line2D.Double getPathTL() {
        return pathTL;
    }

    public Line2D.Double getPathTR() {
        return pathTR;
    }

    public Line2D.Double getPathBR() {
        return pathBR;
    }

    public Line2D.Double getPathBL() {
        return pathBL;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public int getxVol() {
        return xVol;
    }

    public void setxVol(int xVol) {
        this.xVol = xVol;
    }

    public double getyVol() {
        return yVol;
    }

    public void setyVol(double yVol) {
        this.yVol = yVol;
    }

    public void setBoundX(int boundX) {
        this.boundX = boundX;
    }

    public void setBoundY(int boundY) {
        this.boundY = boundY;
    }

    public boolean isFalling() {
        return yVol > 0;
    }

    public void setAccel(int accel1){
        this.accel=accel1;
    }

    private void drawLine(Graphics myBuffer, Line2D line){
        myBuffer.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
    }
}
