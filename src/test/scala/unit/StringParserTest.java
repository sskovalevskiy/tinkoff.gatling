package unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringParserTest {

    @ParameterizedTest
    @MethodSource("stringProvider")
    void parse(String initial, String expected) {
        assertEquals(expected, StringParser.parse(initial));
    }

    static Stream<Arguments> stringProvider() {
        return Stream.of(
                arguments("2as3(kd2(ab))", "aaskdababkdababkdabab"),
                arguments("abc", "abc"),
                arguments("12abc", "aaaaaaaaaaaabc"),
                arguments("2as3(kd2(ab))w2(xyxy)", "aaskdababkdababkdababwxyxyxyxy")
        );
    }

}
