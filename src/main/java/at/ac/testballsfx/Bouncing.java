package at.ac.testballsfx;

public class Bouncing {

    private final static int SPEED = 2;


    // change of dx and dy
    private double dx;
    private double dy;

    public Bouncing(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // random dir direction = "angle" java prefers radiants 0 to 2PI

    public Bouncing() {
        double direction = Math.random() * 2 * Math.PI;
        dx = Math.sin(direction);
        dy = Math.cos(direction);

        //sin and cos of direction angle
    }

    // getDirection

    public double getDx() {

        return dx * SPEED;
    }

    public double getDy() {

        return dy * SPEED;
    }

    // if we hit "walls" dx/dy we want do change direction

    public void bounceX() {

        dx *= -1;
    }

    public void bounceY() {

        dy *= -1;
    }

}
