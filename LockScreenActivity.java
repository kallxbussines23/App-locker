package com.example.applocker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LockScreenActivity extends AppCompatActivity {
    private EditText etPin;
    private Button btnUnlock;
    private final String CORRECT_PIN = "1234"; // PIN bawaan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        etPin = findViewById(R.id.etPin);
        btnUnlock = findViewById(R.id.btnUnlock);

        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPin.getText().toString().equals(CORRECT_PIN)) {
                    finish(); // Tutup layar kunci jika PIN benar
                } else {
                    Toast.makeText(LockScreenActivity.this, "PIN Salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Mencegah tombol back melewati layar kunci
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
    }
