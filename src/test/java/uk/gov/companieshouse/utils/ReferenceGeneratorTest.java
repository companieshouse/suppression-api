package uk.gov.companieshouse.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceGeneratorTest {

    @Test
    public void testGeneration_returnsRandomNumbers() {

        String sequence1 = ReferenceGenerator.generate();
        String sequence2 = ReferenceGenerator.generate();
        String sequence3 = ReferenceGenerator.generate();

        assertNotEquals(sequence1, sequence2);
        assertNotEquals(sequence2, sequence3);
        assertNotEquals(sequence1, sequence3);
    }

    @Test
    public void testGeneration_returnsCorrectFormat() {

        String regexExpression = "[A-Z0-9]{5}-[A-Z0-9]{5}";
        ArrayList<Boolean> results = new ArrayList<>();
        boolean [] expected = new boolean[10];

        for(int i = 0; i < 10; i++){
            String sequence = ReferenceGenerator.generate();
            Pattern regexPattern = Pattern.compile(regexExpression);
            results.add(regexPattern.matcher(sequence).find());
        }

        assertFalse(results.contains(false));
    }
}
