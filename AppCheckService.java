package com.example.applocker;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import java.util.Arrays;
import java.util.List;

public class AppCheckService extends Service {
    private Handler handler = new Handler();
    private String lastApp = "";
    
    // Daftar package aplikasi yang ingin dikunci
    private List<String> lockedPackages = Arrays.asList(
        "com.whatsapp",
        "com.instagram.android"
    );

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForegroundApp();
                handler.postDelayed(this, 1000); // Cek setiap 1 detik
            }
        }, 1000);
        return START_STICKY;
    }

    private void checkForegroundApp() {
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        UsageEvents events = usm.queryEvents(time - 3000, time);
        UsageEvents.Event event = new UsageEvents.Event();

        String currentApp = "";
        while (events.hasNextEvent()) {
            events.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                currentApp = event.getPackageName();
            }
        }

        if (!currentApp.isEmpty() && !currentApp.equals(lastApp)) {
            lastApp = currentApp;
            if (lockedPackages.contains(currentApp)) {
                // Buka activity layar kunci saat aplikasi terdeteksi
                Intent lockIntent = new Intent(this, LockScreenActivity.class);
                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lockIntent);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
