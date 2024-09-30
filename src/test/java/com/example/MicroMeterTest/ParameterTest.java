package com.example.MicroMeterTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterTest {

    static Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(5, 0, 5),
                Arguments.of(-1, 1, 0),
                Arguments.of(100, 50, 150)
        );
    }

    @DisplayName("Display name for the test")
    @ParameterizedTest(name = "{index} - Add({0} + {1}) = {2}")
    @MethodSource("testDataProvider")
    public void test(int operand1, int operand2, int expectedResult) {
        assertEquals(operand1 + operand2, expectedResult);
    }
}
