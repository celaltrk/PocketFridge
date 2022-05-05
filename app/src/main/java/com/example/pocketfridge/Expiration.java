package com.example.pocketfridge;


import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketfridge.fridgeItems.Product;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Expiration {
    public static int warningDays = 3;
    Calendar calendar;

    public Expiration() {
    }

    public int getWarningDays() {
        return warningDays;
    }

    public void setWarningDays(int warningDays) {
        this.warningDays = warningDays;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    int daysLeft(Product product){
        Calendar calendarProduct = product.getExpDateCalendar();
        calendarProduct.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        int remainingDays = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                remainingDays = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(calendar.toInstant(), calendarProduct.toInstant())));
            }
        }
        catch (Exception e){
        }
        return remainingDays;
    }

    public boolean isExpired(Product product){
        calendar = Calendar.getInstance();
        if(product.getExpDateCalendar().compareTo(calendar)>=0){
            return true;
        }
        return false;
    }

    public boolean isClose(Product product){
        Calendar calendarProduct = product.getExpDateCalendar();
        calendarProduct.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        int remainingDays = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                remainingDays = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(calendar.toInstant(), calendarProduct.toInstant())));
            }
        }
        catch (Exception e){
        }
        if(remainingDays < warningDays){
            return true;
        }
        return false;
    }
}
