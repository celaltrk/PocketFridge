package com.example.pocketfridge.attributes;

import com.example.pocketfridge.fridgeItems.Product;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Expiration {
    private int warningDays = 3;
    private Calendar calendar;

    public Expiration() {
        calendar = Calendar.getInstance();
    }

    public boolean isExpired(Product product) {
        return product.getExpDateCalendar().before(Calendar.getInstance());
    }

    public boolean isCloseToExpire(Product product) {
        Calendar calendarProduct = product.getExpDateCalendar();
        calendarProduct.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        int remainingDays = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                remainingDays = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(calendar.toInstant(), calendarProduct.toInstant())));
            }
        } catch (Exception e) {
        }
        if (remainingDays < warningDays) {
            return true;
        }
        return false;
    }
}