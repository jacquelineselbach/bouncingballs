package at.ac.testballsfx;

import javafx.scene.paint.Color;

public enum State {

    // associate color with each state

    HEALTHY {
        public Color getColor(){

            return Color.LIGHTGRAY;
        }
    }, INFECTED {
        public Color getColor(){

            return Color.ROYALBLUE;
        }
    }, RECOVERED {
        public Color getColor(){

            return Color.DARKSEAGREEN;}
        };

        public abstract Color getColor();
}
