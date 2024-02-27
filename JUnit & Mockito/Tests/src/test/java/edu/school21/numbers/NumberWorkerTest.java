package edu.school21.numbers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberWorkerTest {

    NumberWorker numberWorker;

    @BeforeAll
    void init() {
        System.out.println("TESTS STARTED :)))");
        numberWorker = new NumberWorker();
    }

    @AfterAll
    void goodbye () {
        System.out.println("ALL TESTS ENDED!!!");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 11, 6997})
    void isPrimeForPrimes(int number) {
        boolean result = numberWorker.isPrime(number);
        Assertions.assertTrue(result, "Answer should be true because given number is prime");
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 22, 8080})
    void isPrimeForNotPrimes(int number) {
        boolean result = numberWorker.isPrime(number);
        Assertions.assertFalse(result, "Answer should be false because given number is not prime");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, Integer.MIN_VALUE})
    void isPrimeForIncorrectNumbers(int number) {
        Exception exception = Assertions.assertThrows(IllegalNumberException.class, () -> {
            numberWorker.isPrime(number);
        });

        String exceptedMessage = "Number should be greater than 1. Your number is: " + number;
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(exceptedMessage, actualMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSumTest(int number, int expected) {
        int result = numberWorker.digitsSum(number);
        Assertions.assertEquals(expected, result);
    }
}