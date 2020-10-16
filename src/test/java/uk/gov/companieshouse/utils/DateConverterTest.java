package uk.gov.companieshouse.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DateConverterTest {

    @Test
    public void testDateConversion__successfullyConvertsValidDatestrings() {

        String date1 = "2020-01-01";
        String date2 = "1965-12-31";
        String date3 = "2012-02-29";

        String date1Result = DateConverter.convertDateToHumanReadableFormat(date1);
        String date2Result = DateConverter.convertDateToHumanReadableFormat(date2);
        String date3Result = DateConverter.convertDateToHumanReadableFormat(date3);

        assertEquals("1 January 2020", date1Result);
        assertEquals("31 December 1965", date2Result);
        assertEquals("29 February 2012", date3Result);
    }

    @Test
    public void testDateConversion__gracefullyRejectsInvalidDatestrings() {

        String date = "etcfryvgkubhlinjlm";
        String dateResult = DateConverter.convertDateToHumanReadableFormat(date);

        assertEquals(date, dateResult);
    }
    
}
