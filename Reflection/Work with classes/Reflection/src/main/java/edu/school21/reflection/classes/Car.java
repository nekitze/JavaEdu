package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
    private String model;
    private int horsepower;
    private boolean isEngineStarted;

    public Car() {
        this.model = "Noname";
        this.horsepower = 0;
        isEngineStarted = false;
    }

    public Car(String model, int horsepower) {
        this.model = model;
        this.horsepower = horsepower;
        isEngineStarted = false;
    }

     public void startEngine() {
        isEngineStarted = true;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("horsepower='" + horsepower + "'")
                .add("isEngineStarted=" + isEngineStarted)
                .toString();
    }
}
