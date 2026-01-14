package app;

import javafx.scene.control.Button;

public class TimeChipButton extends Button {

    public TimeChipButton(String text) {
        super(text);
        getStyleClass().add("time-chip");
        setFocusTraversable(false);
    }
}
