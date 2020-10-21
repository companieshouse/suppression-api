package uk.gov.companieshouse.utils;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static uk.gov.companieshouse.SuppressionApplication.APPLICATION_NAMESPACE;

public class DateConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAMESPACE);

    public static String convertDateToHumanReadableFormat(String date) {
        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("d MMMM yyyy");
        Date dateObject;
        try {
            dateObject = formatIn.parse(date);
            return formatOut.format(dateObject);
        } catch (ParseException e) {
            LOGGER.info(String.format("Could not convert date %s to human readable format %s", date, e));
            return date;
        }
    }
}
