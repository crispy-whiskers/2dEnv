package entities;

public class Body extends physObject {
    public boolean isLeft, isRight,isAbove;
    public double speed;

    public Body(int x, int y, int boundaryx, int boundaryy) {

        super(x,y,boundaryx,boundaryy,40,40);
        jump();

        isLeft=false;
        isAbove=false;
        isRight=false;
        speed = 10;


    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public void setAbove(boolean above) {
        isAbove = above;
    }

    public void moveR() {
        if(!isRight)
            setxVol((int)speed);
        setFriction(1);
    }

    public void moveL() {
        if(!isLeft)
            setxVol((int)speed*-1);
        setFriction(1);
    }

    public void sprintR() {
        setxVol(30);
        setFriction(1);
    }
    public void sprintL() {
        setxVol(-30);
        setFriction(1);
    }

    public void jump() {
        setyVol(-30);
        setAccel(1);
    }




}
