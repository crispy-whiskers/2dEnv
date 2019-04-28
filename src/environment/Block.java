package environment;

import entities.Body;
import entities.Entity;

import java.awt.*;
import java.awt.geom.Line2D;

public class Block extends Entity {
    public Color color;
    public Rectangle HRight, HLeft, HTop, HBottom;

    //todo: 4 Rectangle hitbox system.

    public Block(int x, int y, int height, int width, Color r) {
        super(x,y,width,height);
        color = r;

        HTop = new Rectangle(x,y,width, height/5);
        HBottom = new Rectangle(x, y+height*4/5, width,height/5);
        HRight = new Rectangle(x+((width*4)/5),y+height/5, width/5, height*3/5);
        HLeft = new Rectangle(x, y+height/5, width/5, height*3/5);

    //NOTE: try ratios of height and width;
    }
    @Override
    public void draw(Graphics myBuffer) {
        myBuffer.setColor(color); //draw on buffered image
        myBuffer.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());

        //trace hitboxes
        myBuffer.setColor(Color.black);
       drawR(HTop, myBuffer);
       drawR(HBottom, myBuffer);
       drawR(HRight, myBuffer);
       drawR(HLeft, myBuffer);


    }


    private void drawR(Rectangle r, Graphics myBuffer){
        myBuffer.drawRect(r.x,r.y,r.width,r.height);
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle getHRight() {
        return HRight;
    }

    public Rectangle getHLeft() {
        return HLeft;
    }

    public Rectangle getHTop() {
        return HTop;
    }

    public Rectangle getHBottom() {
        return HBottom;
    }
}
