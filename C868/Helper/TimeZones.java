package C868.Helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This is a helper class to manage conversions between time zones
 */
public class TimeZones {
    /**
     *
     * @param Date
     * @return Returns a string representing the time in the current timezone from UTC
     */
    public static String convertToCurrentTimeZone(String Date) {
        String converted_date = "";
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            java.util.Date date = utcFormat.parse(Date);
            DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getCurrentTimeZone()));
            converted_date =  currentTFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return converted_date;
    }

    /**
     *
     * @return Returns the user's current timezone
     */
    public static String getCurrentTimeZone(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        //System.out.println(tz.getDisplayName());
        return tz.getID();
    }

    /**
     *
     * @return Returns the current time in UTC time
     */
    public static String getUTCTime(){
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp timestamp = Timestamp.valueOf(utc.toLocalDateTime());
        return timestamp.toString();
    }
    /**
     *
     * @param Date
     * @return Returns a string representing the time in UTC from the user's current time
     */
    public static String convertToUTCTimeZone(String Date) {
        String converted_date = "";
        try {
            DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            localFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault().getId()));
            java.util.Date date = localFormat.parse(Date);
            DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentTFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            converted_date =  currentTFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return converted_date;
    }
    /**
     *
     * @param Date
     * @return Returns a string representing the time in EST from the user's current time
     */
    public static String convertToESTTimeZone(String Date) {
        String converted_date = "";
        try {
            DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            localFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault().getId()));
            java.util.Date date = localFormat.parse(Date);
            DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentTFormat.setTimeZone(TimeZone.getTimeZone("EST"));
            converted_date =  currentTFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return converted_date;
    }

    /**
     *
     * @param Date
     * @return Returns the day of the week as a string in EST converted from the date string passed in
     */
    public static int getDayOfWeekEST(String Date) {
        int day = 0;
        String ESTDate = convertToESTTimeZone(Date);
        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        localFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault().getId()));
        try {
            java.util.Date date = localFormat.parse(ESTDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            day = cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     *
     * @param dateString
     * @return Returns a Date object that has been parsed from the date string passed in
     */
    public static Date convertStringToDate(String dateString){
        String ESTDate = convertToESTTimeZone(dateString);
        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = localFormat.parse(ESTDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
