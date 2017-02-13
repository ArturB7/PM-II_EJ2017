package com.example.android.background.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Es una subclase de {@link IntentService} para manejar tareas asincronas (en background) en un
 * servicio en un hilo diferente al principal.
 */
public class WaterReminderIntentService extends IntentService {

    //  Constructor que llama al super con el nombre para esta clase
    public WaterReminderIntentService() {
        super("WaterReminderIntentService");
    }

    //  El trabajo a desempeñar en background
    @Override
    protected void onHandleIntent(Intent intent) {
        //Se obtiene la accion desde el Intent que inició el servicio
        String action = intent.getAction();

        //se invoca al ReminderTasks.executeTask pasandolo la acción indicada.
        ReminderTasks.executeTask(this, action);
    }
}