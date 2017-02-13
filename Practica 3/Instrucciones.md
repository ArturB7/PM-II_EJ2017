#Descripción de la práctica

El propósito de esta practica es que el alumno pueda crear un IntentService para ejecutar una tarea en background.  La implementación surge a partir de una aplicación básica que tiene el propósito de llevar la cuenta de vasos que ha bebido y recordar al usuario beber agua mientras su dispositivo se encuentra cargando batería, pues según los cientificos este es el mejor momento para re-hidratar el cerebro. El código base actualmente solo presenta una interfaz gráfica de usuario que muestra un toast al momento de hacer clic sobre la imagen de un vaso.  Se pretende implementar un IntentService que simule la escritura en background de datos hacia un proveedor de persistencia en la nube, no obstente  en realidad por el momento solamente se hará uso de un archivo [SharedPreferences](https://developer.android.com/training/basics/data-storage/shared-preferences.html) para persistir los datos que nos permitan llevar la cuenta de los vasos que ha bebido y de la cantidad de veces que el usuario ha sido recordado :P los métodos necesarios para trabajar con SharedPreferences ya se encuentran programados en la clase PreferenceUtilities dentro del paquete utilities.

##Instrucciones:
Obtener el código fuente base y modificarlo para:

 1. Crear clase ReminderTasks 
 2. Crear clase WaterReminderIntentService
 3. Registrar el Service en el Manifest
 4. Utilizar el Service para mandar persistir los datos de manera asincrona

A continuación algo de código y su explicación para cada paso de la práctica:

##1. Crear clase ReminderTasks. 
Para lograr este propósito el estudiante debe crear una clase denominada **ReminderTasks** preferentemente en un nuevo paquete denominado **sync** dentro del paquete principal **com.example.android.background**.  El código a agregar en esta clase es mas o menos el siguiente:

```java
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
```

##2. Crear clase WaterReminderIntentService

De manera semejante al paso anterior, se debe crear una clase denominada **WaterReminderIntentService** en el nuevo paquete anteriormente creado denominado **sync**.  El código a agregar en esta nueva clase es mas o menos el siguiente:

```java
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
```
##3. Registrar el Service en el Manifest
Para que el Service sea valido debe registrarse en el archivo manifest agregando unas líneas mas o menos como las que siguen:

```xml
...
        <service
            android:name=".sync.WaterReminderIntentService"
            android:exported="false"/>

    </application>
...
```

##4.Utilizar el Service para mandar persistir los datos de manera asincrona
Para probar rápidamente el funcionamiento del nuevo service y lograr que se mande escribir hacia SharedPreferences de manera asincrona como ya se tiene programado,  habrá que modificar la **MainActivity** de nuestra App actual.  Para lograr esto se deberá modificar el código del método **incrementWater** de esta clase mas o menos así:

```java
 public void incrementWater(View view) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(this, R.string.water_chug_toast, Toast.LENGTH_SHORT);
        mToast.show();

        // Se crea un Intent explicito para WaterReminderIntentService
        Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
        // Se establece la acción del intent a ACTION_INCREMENT_WATER_COUNT
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        // Se inicia mediante startService pasandolo el intent creado
        startService(incrementWaterCountIntent);        
  }
```

Ahora la aplicación debería mandar incrementar de manera asincrona el contador cada vez que presiones el vaso. Eso es todo amigos.
