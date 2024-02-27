package edu.school21.numbers;

public class IllegalNumberException extends RuntimeException {
    public IllegalNumberException(int number) {
        super("Number should be greater than 1. Your number is: " + number);
    }
}