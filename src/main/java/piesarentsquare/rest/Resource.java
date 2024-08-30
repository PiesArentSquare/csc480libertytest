package piesarentsquare.rest;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class Resource {
    private AtomicInteger value = new AtomicInteger(0);

    public int getValue() {
        return value.get();
    }

    public void setValue(int value) {
        this.value.set(value);
    }
}
