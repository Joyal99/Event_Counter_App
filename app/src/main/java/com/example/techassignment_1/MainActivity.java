//Joyal Biju Kulangara
//40237314
//COEN 390 - Programming Assignment 1

package com.example.techassignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button eventButton1, eventButton2, eventButton3;
    private TextView totalCountTextView;
    private CounterModel counterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializing CounterModel class to manage SharedPreferences (MVC)
        counterModel = new CounterModel(this);

        // Initializing UI elements
        eventButton1 = findViewById(R.id.eventButton1);
        eventButton2 = findViewById(R.id.eventButton2);
        eventButton3 = findViewById(R.id.eventButton3);
        totalCountTextView = findViewById(R.id.totalCountText);

        // Load Button Names and Total Count
        updateUI();

        // Resetting Count Button (for testing purposes)
        Button resetCountsBtn = findViewById(R.id.resetCountsBtn);
        resetCountsBtn.setOnClickListener(v -> resetCounts());

        // Setting On Click Listeners for incrementing total count
        eventButton1.setOnClickListener(v -> incrementCount(counterModel.getCounter1Name()));
        eventButton2.setOnClickListener(v -> incrementCount(counterModel.getCounter2Name()));
        eventButton3.setOnClickListener(v -> incrementCount(counterModel.getCounter3Name()));

        // redirection to Settings Activity
        findViewById(R.id.Settingsbtn).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        // redirection to Data Activity
        findViewById(R.id.showCountsBtn).setOnClickListener(v -> startActivity(new Intent(this, DataActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI(); // Refresh UI when returning to MainActivity
    }

    // Updating UI elements with counter names and total count
    private void updateUI() {
        eventButton1.setText(counterModel.getCounter1Name());
        eventButton2.setText(counterModel.getCounter2Name());
        eventButton3.setText(counterModel.getCounter3Name());
        totalCountTextView.setText("Total Count: " + counterModel.getTotalCount());
    }

    // Incrementing total count with event button press
    private void incrementCount(String eventName) {
        if (counterModel.getTotalCount() >= counterModel.getMaxCount()) {
            Toast.makeText(this, "Maximum count reached!", Toast.LENGTH_SHORT).show();
            return;
        }
        counterModel.incrementTotalCount();
        counterModel.addEvent(eventName);
        updateUI();
    }

    // Resetting total count and clearing event history
    private void resetCounts() {
        counterModel.resetCounts();  // Resetting the counts using CounterModel
        updateUI();
        Toast.makeText(this, "Counts have been reset!", Toast.LENGTH_SHORT).show();
    }
}