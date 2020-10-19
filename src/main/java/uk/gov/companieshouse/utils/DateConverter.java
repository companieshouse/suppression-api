package uk.gov.companieshouse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
    
    public static String convertDateToHumanReadableFormat(String date) {
        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("d MMMM yyyy");
        Date dateObject;
        try {
            dateObject = formatIn.parse(date);
            return formatOut.format(dateObject);
        } catch (ParseException e) {
            String warning = String.format("Could not convert date %s to human readable format", date);
            LOGGER.warn(warning, e);
            return date;
        }
    }
}
