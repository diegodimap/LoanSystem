package com.diego.loansystem.util;

import java.time.LocalDate;

public class DateFormatter {
    public static String dateFormat(LocalDate localDate){
        return String.format("%02d", localDate.getDayOfMonth()) + "/" +
                String.format("%02d", localDate.getMonthValue()) + "/" +
                localDate.getYear();
    }
}
