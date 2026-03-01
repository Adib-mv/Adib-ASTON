package org.example.providers;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class NegativeTestDataProvider {
    static Stream<Arguments> provideInvalidEmailValues() {
        return Stream.of(
                Arguments.of("test", "без @"),
                Arguments.of("test@", "только с @"),
                Arguments.of("@example.com", "без локальной части"),
                Arguments.of("test@example", "без домена верхнего уровня"),
                Arguments.of("test.example.com", "без @"),
                Arguments.of("test@.com", "с точкой после @")
        );
    }

    static Stream<Arguments> provideEmptyRequiredFields() {
        return Stream.of(
                Arguments.of("", "10.00", "test@example.com", "пустом поле телефона"),
                Arguments.of("297777777", "", "test@example.com", "пустом поле суммы"),
                Arguments.of("", "", "test@example.com", "пустых полях телефона и суммы")
        );
    }
}