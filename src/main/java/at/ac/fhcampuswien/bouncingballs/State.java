package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.paint.Color;

public enum State {
    // associated colors for each state
    HEALTHY {
        public Color getColor(){
            return Color.LIGHTGRAY;
        }
    }, INFECTED {
        public Color getColor(){ return Color.GOLD;
        }
    }, RECOVERED {
        public Color getColor() {
            return Color.DODGERBLUE;
        }
    }, DEAD {
        public Color getColor() {
            return Color.DIMGRAY;
        }
    };

        public abstract Color getColor();
}
