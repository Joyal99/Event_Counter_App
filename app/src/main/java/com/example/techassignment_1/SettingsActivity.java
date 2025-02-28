package com.example.techassignment_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private CounterModel counterModel; // Model managing SharedPreferences
    private EditText editTextCounter1, editTextCounter2, editTextCounter3, editTextMaxCount;
    private Button btnSave;
    private boolean isEditMode = false; // Tracking edit mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        // Custom Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Up Navigation Enabled
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Handle system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize CounterModel for managing SharedPreferences
        counterModel = new CounterModel(this);

        // Initialize UI Elements
        editTextCounter1 = findViewById(R.id.editTextCounter1);
        editTextCounter2 = findViewById(R.id.editTextCounter2);
        editTextCounter3 = findViewById(R.id.editTextCounter3);
        editTextMaxCount = findViewById(R.id.editTextMaxCount);
        btnSave = findViewById(R.id.btnSave);

        // Loading Saved Data into UI
        loadSettings();
        toggleEditMode(false);

        // Saving button click listener
        btnSave.setOnClickListener(v -> saveSettings());
    }

    // Inflating toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    // Handling toolbar actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            toggleEditMode(!isEditMode);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish(); // Navigate back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Loading settings from CounterModel and display them in UI
    private void loadSettings() {
        editTextCounter1.setText(counterModel.getCounter1Name());
        editTextCounter2.setText(counterModel.getCounter2Name());
        editTextCounter3.setText(counterModel.getCounter3Name());
        editTextMaxCount.setText(String.valueOf(counterModel.getMaxCount()));
    }

    // Saving user input settings to CounterModel
    private void saveSettings() {
        String counter1 = editTextCounter1.getText().toString().trim();
        String counter2 = editTextCounter2.getText().toString().trim();
        String counter3 = editTextCounter3.getText().toString().trim();
        String maxCountStr = editTextMaxCount.getText().toString().trim();

        // Validating Counter Names (Must be alphabetic and max 20 chars)
        if (!isValidName(counter1) || !isValidName(counter2) || !isValidName(counter3)) {
            Toast.makeText(this, "Counter names must be alphabetic and max 20 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validating Max Count (5-200)
        int maxCount;
        try {
            maxCount = Integer.parseInt(maxCountStr);
            if (maxCount < 5 || maxCount > 200) {
                Toast.makeText(this, "Maximum count should be between 5 and 200.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid maximum count value.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Saving settings using CounterModel
        counterModel.setCounterNames(counter1, counter2, counter3);
        counterModel.setMaxCount(maxCount);

        Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();

        // Switching back to non-edit mode
        toggleEditMode(false);
    }

    // Enabling/Disabling Edit Mode
    private void toggleEditMode(boolean enable) {
        isEditMode = enable;

        // Enable/Disable input fields
        editTextCounter1.setEnabled(enable);
        editTextCounter2.setEnabled(enable);
        editTextCounter3.setEnabled(enable);
        editTextMaxCount.setEnabled(enable);

        // Show Save button in edit mode
        btnSave.setVisibility(enable ? View.VISIBLE : View.GONE);

        // Update Toolbar Title
        getSupportActionBar().setTitle(enable ? "⚙ Edit Settings" : "⚙ Settings Activity");
    }

    // Validate counter name (Alphabetic, max 20 chars)
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z ]{1,20}$");
    }
}
