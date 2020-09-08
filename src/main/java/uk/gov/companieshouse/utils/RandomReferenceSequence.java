package uk.gov.companieshouse.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomReferenceSequence {
    public static String generate() {

        StringBuilder builder = new StringBuilder();
        return builder
            .append(RandomStringUtils.random(5, true, true).toUpperCase())
            .append('-')
            .append(RandomStringUtils.random(5, true, true).toUpperCase())
            .toString();
    }
}
