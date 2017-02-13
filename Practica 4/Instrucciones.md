#Descripción de la práctica

El propósito de esta practica es que el alumno pueda crear un Job Service que sea capaz de lanzar periodicamente una notificación cuando el dispositivo se encuentre cargando. 

[SharedPreferences](https://developer.android.com/training/basics/data-storage/shared-preferences.html) 

##Instrucciones:
Obtener el código fuente base y modificarlo para:

 1. Crear clase NotificationUtils 
 2. Crear clase 
 3. Registrar 
 4. Utilizar 

A continuación algo de código y su explicación para cada paso de la práctica:

##1. Crear clase NotificationUtils. 
Para lograr este propósito el estudiante debe crear una clase denominada **NotificationUtils** preferentemente dentro del paquete denominado **utilities** dentro del paquete principal **com.example.android.background**.  El código a agregar en esta clase es mas o menos el siguiente:

```java
package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;

public class NotificationUtils {

    /*
     * Esta constante se refuere al ID que puede ser utilizado para acceder a nuestra notificación una vez que 
     * ha sido mostrada.  Esto puede ser útil cuando se necesita cancelar la notificación o actualizarla. Este
     * es un número arbitrario y puede ser establecido a cualquier valor que gustes. 1138 no significa nada especial.
     */
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;
    /**
     * Este ID es utilizado para identificar y referenciar de manera única al pending intent
     */
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;

    // Este método creará una notificaión cuando el dispositivo se cargue. Puede ser de utilidad para
    // comprender mejor echarle un vistazo a la guía del siguiente link para ver los ejemplos en los que
    // se basa el código: https://developer.android.com/training/notify-user/build-notification.html
    public static void remindUserBecauseCharging(Context context) {
        // - Tiene el color de R.colorPrimary - se utiliza ContextCompat.getColor para obtener un color compatible
        // - tiene la ic_drink_notification como el icono pequeño
        // - utiliza el icono devuelto por el método largeIcon helper como el icono grande
        // - establece el titulo al valor del String en recursos: charging_reminder_notification_title
        // - establece el texto al valor del String en recursos: charging_reminder_notification_body 
        // - establece el estilo a NotificationCompat.BigTextStyle().bigText(text)
        // - establece el default de notification a "vibrar"
        // - utiliza el content intent devuelto por el metodo auxiliar contentIntent para el contentIntent
        // - automaticamente cancela la notificación cuando se le da clic a esta
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        // Si el build version es mayor que JELLY_BEAN, establece la prioridad de la notificación a PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        // Obtiene un NotificationManager, utilizando context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Dispara la notificación invocando notify en el NotificationManager.
        // Pasa el ID que se definió para la notification y el notificationBuilder.build()
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    // Este es un metodo auxiliar con un único parametro, el contexto, devuelve un PendingIntent
    // el cual es creado cuando la notificación es presionada.  Este pending intent debería abrir
    // nuevamente la MainActivity
    private static PendingIntent contentIntent(Context context) {
        // Se crea un intent para abrir la MainActivity
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        // Se crea un PendingIntent utilizando getActivity que:
        // - Toma el contexto pasado como parametro
        // - toma el ID enero único definido anteriormente
        // - toma el intent para abrir la MainActivity que ha sido creada;
        // - Tiene la bandera FLAG_UPDATE_CURRENT, para que si el intent es creado nuevamente, 
        //   mantenga el intent pero actualice los datos
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // Metodo auxiliar que toma el contexto como parametro de entrada y devuelve Bitmap. 
    // Este metodo es necesario para decodificar el bitmap requerido por la notificación.
    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}
```

##2. Crear clase WaterReminderIntentService

De manera semejante al paso anterior, se debe crear una clase denominada **WaterReminderIntentService** en el nuevo paquete anteriormente creado denominado **sync**.  El código a agregar en esta nueva clase es mas o menos el siguiente:

```java

```
##3. Registrar el Service en el Manifest
Para que el Service sea valido debe registrarse en el archivo manifest agregando unas líneas mas o menos como las que siguen:

```xml
...

...
```

##4.Utilizar el Service para mandar persistir los datos de manera asincrona
Para probar rápidamente el funcionamiento del nuevo service y lograr que se mande escribir hacia SharedPreferences de manera asincrona como ya se tiene programado,  habrá que modificar la **MainActivity** de nuestra App actual.  Para lograr esto se deberá modificar el código de esta clase **MainActivity** mas o menos así:

```java

```

Eso es todo amigos.
