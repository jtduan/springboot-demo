package rest.constants;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jtduan on 2016/9/14.
 */
public class DateUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String now(){
        return LocalDateTime.now().format(formatter);
    }

    public static LocalDateTime getDateTime(String dateTime){
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static String getDateTimeStr(LocalDateTime dateTime){
        return dateTime.format(formatter);
    }
}
