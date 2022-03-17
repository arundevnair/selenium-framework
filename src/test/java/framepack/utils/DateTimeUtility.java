package framepack.utils;

import reports.ReportTrail;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DateTimeUtility {


    public static String getTodayDate() {
        String region = PropertyReader.getProperty("country");
        String toFormat = PropertyReader.getProperty("dateFormat");
        return getTodayDateByRegion(region,toFormat);
    }

    public static String getTodayDateByRegion(String region, String toFormat) {

        String timeZone = null;
        region = region.toLowerCase();

        if (region.equals("india") | region.equals("in") | region.equals("ist")) {
            timeZone = "Asia/Kolkata";
        }else if (region.equals("us")) {
            timeZone = "US/Eastern";
        } else if (region.equals("uk")) {
            timeZone = "Europe/London";
        } else if (region.equals("ca")){
            timeZone = "Canada/Eastern";
        }else if (region.equals("pst")) {
            timeZone = "America/Los_Angeles";
        } else if (region.equals("cst")){
            timeZone = "US/Central";
        } else if (region.equals("est")){
            timeZone = "US/Eastern";
        }else {
            ReportTrail.info("Your input region " + region + " is not valid . Hence Time Zone defaulted to IST");
            timeZone = "Asia/Kolkata";
        }

        ZoneId zoneId = ZoneId.of(timeZone);
//        Getting time at zone in the given timeZone in +/-GMT format
        ZonedDateTime timeAsZoned = ZonedDateTime.now(zoneId);
//        Converting the time to localDate time format
//        LocalDateTime localDateTimeOfZone = timeAsZoned.toLocalDateTime();

//        Converting the calculated time the given time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(toFormat);
        String formattedDate = timeAsZoned.format(formatter);
        System.out.println(formattedDate);
        return formattedDate;

    }

    public static String getADateOlderThan(String years, String toFormat){
        String date = "";
        Random random = new Random();
        int yearUbound = 60-Integer.parseInt(years);
        int rYears = random.nextInt(yearUbound)+Integer.parseInt(years);
        int rMonths = random.nextInt(11)+1;
        int rDays = random.nextInt(27)+1;

        LocalDate ldt = LocalDate.now();
        ldt = ldt.minusYears(rYears);
        ldt = ldt.minusMonths(-rMonths);
        ldt = ldt.minusDays(rDays);

        date = convertDateToString(ldt,toFormat);

        return date;
    }

    public static String convertDateToString(LocalDate ldt, String toFormat){
        //        Converting the calculated time the given time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(toFormat);
        String formattedDate = ldt.format(formatter);
        return formattedDate;
    }

    public static LocalDate convertStringToDate(String strDate, String toFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(toFormat);
        LocalDate localDateTime = LocalDate.parse(strDate, formatter);
        System.out.println(localDateTime);
        return localDateTime;
    }

    public static LocalDateTime addTime(LocalDateTime time, int timeToAdd, String periodType) {
        periodType = periodType.toLowerCase(Locale.ENGLISH);
        LocalDateTime newTime = null;
        switch (periodType) {
            case "hours":
                newTime = time.plusHours(timeToAdd);
                break;
            case "days":
                newTime = time.plusDays(timeToAdd);
                break;
            case "minutes":
                newTime = time.plusMinutes(timeToAdd);
                break;
            case "months":
                newTime = time.plusMonths(timeToAdd);
                break;
            case "years":
                newTime = time.plusYears(timeToAdd);
                break;
        }
        return newTime;
    }

    public static LocalDate addLocalDate(LocalDate date, int timeToAdd, String periodType) {
        periodType = periodType.toLowerCase(Locale.ENGLISH);
        LocalDate newDate = null;
        switch (periodType) {
            case "days":
                newDate = date.plusDays(timeToAdd);
                break;
            case "months":
                newDate = date.plusMonths(timeToAdd);
                break;
            case "years":
                newDate = date.plusYears(timeToAdd);
                break;
        }
        return newDate;
    }

    public static String addDays(String currentDate, int daysToAdd){
        String dateFormat = PropertyReader.getProperty("dateFormat");
        return addDays(currentDate,daysToAdd,dateFormat);
    }

    public static String addDays(String currentDate, int monthsToAdd, String dateFormat){
        LocalDate ldt = convertStringToDate(currentDate,dateFormat);
        LocalDate newDateLdt =  addLocalDate(ldt,monthsToAdd,"days");
        String newDate = convertDateToString(newDateLdt,dateFormat);
        return newDate;
    }

    public static String addMonths(String currentDate, int monthsToAdd){
        String dateFormat = PropertyReader.getProperty("dateFormat");
        return addMonths(currentDate,monthsToAdd,dateFormat);
    }

    public static String addMonths(String currentDate, int monthsToAdd, String dateFormat){
        LocalDate ldt = convertStringToDate(currentDate,dateFormat);
        LocalDate newDateLdt =  addLocalDate(ldt,monthsToAdd,"months");
        String newDate = convertDateToString(newDateLdt,dateFormat);
        return newDate;
    }

    public static String addYears(String currentDate, int yearsToAdd){
        String dateFormat = PropertyReader.getProperty("dateFormat");
        return addYears(currentDate,yearsToAdd,dateFormat);
    }

    public static String addYears(String currentDate, int yearsToAdd, String dateFormat){
        LocalDate ldt = convertStringToDate(currentDate,dateFormat);
        LocalDate newDateLdt =  addLocalDate(ldt,yearsToAdd,"years");
        String newDate = convertDateToString(newDateLdt,dateFormat);
        return newDate;
    }

    public static int getNumberOfDaysInMonth(String year, String month) {
        ReportTrail.info("Getting the number of days in the month " + month + " of the year " + year);
        int numberOfDays = 0;
        try {
            YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
            numberOfDays = yearMonthObject.lengthOfMonth();
        } catch (NumberFormatException e) {
            ReportTrail.fail("Unable to parse the parameters.");
        }
        ReportTrail.info("The number of days are " + numberOfDays);
        return numberOfDays;
    }

    public static LocalDateTime getLastDayOfMonth(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        LocalDate lastDateOfMonth =
                localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
        ReportTrail.info("The last day is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        LocalDate lastDateOfMonth =
                localDate.withDayOfMonth(1);
        ReportTrail.info("The first day of the month is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfNextMonth(LocalDateTime date) {
        LocalDateTime sameDayNextMonth = date.plusMonths(1);
        LocalDate localDate = sameDayNextMonth.toLocalDate();
        LocalDate lastDateOfMonth = localDate.withDayOfMonth(1);
        ReportTrail.info("The first day of the next month is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static String getAnAdultDOB() {
        String years = PropertyReader.getProperty("adultYears");
        String toFormat = PropertyReader.getProperty("dateFormat");
        ReportTrail.info("Getting an adult DOB using utility with property as adultYears=" + years + " dateFormat=" + toFormat);
        String dob = DateTimeUtility.getADateOlderThan(years,toFormat);
        ReportTrail.info("DOB got as " + dob);
        return dob;
    }

    public static String getAChildDOB() {
        String years = PropertyReader.getProperty("childMaxYears");
        String toFormat = PropertyReader.getProperty("dateFormat");
        ReportTrail.info("Getting an child DOB using utility with property as childMaxYears=" + years + " dateFormat=" + toFormat);
        String dob = DateTimeUtility.getADateOlderThan(years,toFormat);
        ReportTrail.info("DOB got as " + dob);
        return dob;
    }

}
