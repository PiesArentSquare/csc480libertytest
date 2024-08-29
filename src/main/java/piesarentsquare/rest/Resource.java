package piesarentsquare.rest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Resource {
    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
