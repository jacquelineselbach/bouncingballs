package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Random;

public class BallsController {
    private ArrayList<Ball> balls;
    private static double areaheight;
    private static double areawidth;
    private final static double infectionrate = SimulationController.getInfectionrate();
    private final static double deathrate = SimulationController.getDeathrate();

    public BallsController(Pane area, int populationSize) {
        areaheight = area.getHeight();
        areawidth = area.getWidth();
        balls = new ArrayList<>();


        // If lockdown is selected: half of the population stands still while th other half is moving casually
        if(uiSettingsController.getoptLockdown() == true){
            for (int i = 0; i < (populationSize)/2; i++) {
                balls.add(new Ball(State.HEALTHY, area));
            }

            for (int i = 0; i < (populationSize-1)/2; i++) {
                Ball ball = new Ball(State.HEALTHY, area);
                ball.setSPEED(0);
                balls.add(ball);
            }
        }

        // normal mode
        else {
            // create healthy balls in area simulation
            for (int i = 0; i < populationSize - 1; i++) {
                balls.add(new Ball(State.HEALTHY, area));
            }
        }

        // add single infected ball in area simulation
        balls.add(new Ball(State.INFECTED, area));
        draw();
    }

    public void draw() {
        for (Ball b : balls) {
            b.draw();
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void moveBalls() {
        for (Ball b : balls) {
            if(b.getState() == State.DEAD){
                continue; // to stop zombies from walking
            }
           b.move();
        }
    }

    public double distance(Ball a, Ball b) {
        // Euclidean Distance Formula - Standard Euclidean Distance
        return (Math.sqrt(Math.pow(a.getX() - b.getX(), 2)) + (Math.pow(a.getY() - b.getY(), 2)));
    }

    public void collisionCheck(Ball a, Ball b) {
        Random random = new Random();
        double rnd = random.nextDouble();
        if (distance(a,b) < 2 * Ball.radius){ // if distance is less that 2 times the radius, collision occurred
            if (((a.getState() == State.INFECTED && b.getState() == State.HEALTHY) ||
                    (a.getState() == State.HEALTHY && b.getState() == State.INFECTED)) &&
                    rnd <= (infectionrate/100)){
                a.setState(State.INFECTED);
                b.setState(State.INFECTED);
            }
            if((a.getState() != State.DEAD && b.getState() != State.DEAD) &&
                    (a.getX() > Ball.radius && a.getX() < areawidth-Ball.radius) &&
                    (b.getX() > Ball.radius && b.getX() < areawidth-Ball.radius) &&
                    (a.getY() > Ball.radius && a.getY() < areaheight-Ball.radius) &&
                    (a.getY() > Ball.radius && a.getY() < areaheight-Ball.radius)){
                    /*
                    this prevents dead balls from bouncing or getting bounced of and it prevents
                     balls from bouncing too near the edge of the pane since they otherwise could
                    bounce out of bounds
                    */
                if(a.getAbsDx() >= a.getAbsDy() && b.getAbsDx() >= b.getAbsDy()){
                    a.bounceX();
                    b.bounceX();
                    a.move();
                    b.move();
                }
                if(a.getAbsDx() < a.getAbsDy() && b.getAbsDx() < b.getAbsDy()){
                    a.bounceY();
                    b.bounceY();
                    a.move();
                    b.move();
                }
                if(a.getAbsDx() < a.getAbsDy() && b.getAbsDx() >= b.getAbsDy()){
                    a.bounceY();
                    b.bounceX();
                    a.move();
                    b.move();
                }
                if(a.getAbsDx() >= a.getAbsDy() && b.getAbsDx() < b.getAbsDy()){
                    a.bounceX();
                    b.bounceY();
                    a.move();
                    b.move();
                }
            }
        }
    }

    public void resolveCollisions() {
        for (Ball a : balls) {
            for (Ball b: balls) {
                if (a != b ){
                    collisionCheck(a,b);
                }
            }
        }
    }

    public void resolveInfections() {
        for(Ball b : balls) {
            b.outcome(deathrate);
        }
    }
}
