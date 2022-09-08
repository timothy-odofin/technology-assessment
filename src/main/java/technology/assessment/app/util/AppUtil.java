package technology.assessment.app.util;

import java.time.LocalDate;
import java.time.Period;

public class AppUtil {
    public static Integer getYearDifference(LocalDate start, LocalDate end){
      return start.until(end).getYears();
    }
}
