package uk.gov.companieshouse.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class ReferenceGenerator {
    public static String generate() {

        return RandomStringUtils.random(5, true, true).toUpperCase() +
            '-' +
            RandomStringUtils.random(5, true, true).toUpperCase();
    }
}
