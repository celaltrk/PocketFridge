package com.example.pocketfridge.attributes;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.pocketfridge.MainActivity;

public class Vibration {
    Activity activity;
    int duration;
    public Vibration (Activity activity, int duration) {
        this.activity = activity;
        this.duration = duration;
    }
    public void vibrate() {
        Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && ((MainActivity) activity).isVibrationOn()) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.EFFECT_DOUBLE_CLICK));
        }
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
