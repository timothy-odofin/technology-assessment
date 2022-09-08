package technology.assessment.app.util;

import java.time.LocalDate;
import java.time.Period;

public class AppUtil {
    public static Integer getYearDifference(LocalDate start, LocalDate end){
      return start.until(end).getYears();
    }
    public static String formatMessage(String message, String placeHolder){
        return String.format(message,placeHolder);

    }
}
