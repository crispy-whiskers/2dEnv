package entities;

import java.awt.*;
import java.awt.geom.Line2D;

public class Entity extends Rectangle implements Renderable {
    private Line2D.Double T, L, R, B;

    public Entity(int x, int y, int width, int height){
        super(x,y,width,height);
        T = new Line2D.Double(x, y, x + getWidth(), y);
        L = new Line2D.Double(x, y, x, y + getHeight());

        R = new Line2D.Double(x + getWidth(), y, x + getWidth(), y + getHeight());
        B = new Line2D.Double(x, y + getHeight(), x + getWidth(), y + getHeight());
    }

    public void setxPos(int x){
        setLocation(x,(int)getY());
    }
    public void setyPos(int y){
        setLocation((int)getX(),y);
    }

    public void draw(Graphics myBuffer){
        myBuffer.setColor(Color.GRAY); //draw on buffered image
        myBuffer.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    public Line2D.Double getT() {
        return T;
    }

    public Line2D.Double getL() {
        return L;
    }

    public Line2D.Double getR() {
        return R;
    }

    public Line2D.Double getB() {
        return B;
    }


}
