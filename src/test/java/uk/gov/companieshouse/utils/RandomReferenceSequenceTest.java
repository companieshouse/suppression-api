package uk.gov.companieshouse.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RandomReferenceSequenceTest {

    @Test
    public void testGeneration_returnsRandomNumbers() {

        String sequence1 = RandomReferenceSequence.generate();
        String sequence2 = RandomReferenceSequence.generate();
        String sequence3 = RandomReferenceSequence.generate();

        assertNotEquals(sequence1, sequence2);
        assertNotEquals(sequence2, sequence3);
        assertNotEquals(sequence1, sequence3);
    }

    @Test
    public void testGeneration_returnsCorrectFormat() {

        String sequence = RandomReferenceSequence.generate();

        Pattern regexPattern = Pattern.compile("[A-Z1-9]{5}-[A-Z1-9]{5}");
        Matcher matcher = regexPattern.matcher(sequence);

        assertTrue(matcher.find());
    }
}
