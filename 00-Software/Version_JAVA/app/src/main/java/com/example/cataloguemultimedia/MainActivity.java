package com.example.cataloguemultimedia;

import static com.example.cataloguemultimedia.API_request.getZtLink;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) { // Charge le fragment seulement une fois
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new WelcomeFragment())
                    .commit();
        }
    }
}