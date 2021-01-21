package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.paint.Color;

/**
enum class associates color with each of the 4 possible states (separated with commas)
**/

public enum State {

    /*
    public final class Color extends javafx.scene.paint.Paint
    = Base class for color or gradients used to fill shapes and backgrounds
    when rendering the scene graph.
    */

    HEALTHY {
        public Color getColor(){
            return Color.LIGHTGRAY; // color class is used to encapsulate colors in the default sRGB color space
        }
    }, INFECTED {
        public Color getColor(){
            return Color.GOLD;
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
    public abstract Color getColor(); // gets the current color
}
