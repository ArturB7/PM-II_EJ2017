package com.example.android.background.sync;

import android.content.Context;
import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {

    //  Constante que nos servirá para indicarle a este task que se desea incrementar el contador de agua tomada
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    // Si la  acción es ACTION_INCREMENT_WATER_COUNT, incrementar el contador de agua
    public static void executeTask(Context context, String action) {
        if (ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }
    }

    //incrementar el contador de agua mediante la clase PreferenceUtilities
    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
    }
}