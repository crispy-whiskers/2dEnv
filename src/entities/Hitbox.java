package entities;

import java.awt.*;

public class Hitbox extends Polygon {
    Color myColor;
    public Hitbox(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color c){
        addPoint(x1,y1);
        addPoint(x2,y2);
        addPoint(x3,y3);
        addPoint(x4,y4);
        myColor=c;
    }
    public void update(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        reset();
        addPoint(x1,y1);
        addPoint(x2,y2);
        addPoint(x3,y3);
        addPoint(x4,y4);
    }
    public void draw(Graphics myBuffer){
        Color prev =myBuffer.getColor();
        myBuffer.setColor(myColor);
        myBuffer.drawPolygon(this);
        myBuffer.setColor(prev);
    }
}
