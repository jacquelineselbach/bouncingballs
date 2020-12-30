package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.paint.Color;

public enum State {
    // associate color with each state
    HEALTHY {
        public Color getColor(){
            return Color.LIGHTGRAY;
        }
    }, INFECTED {
        public Color getColor(){
            return Color.YELLOW;
        }
    }, RECOVERED {
        public Color getColor() {
            return Color.SPRINGGREEN;
        }
    }, DEAD {
        public Color getColor() {
            return Color.DARKSLATEGREY;
        }
    };

        public abstract Color getColor();
}
