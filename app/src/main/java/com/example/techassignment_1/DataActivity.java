package com.example.techassignment_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private TextView tvEventA, tvEventB, tvEventC, tvTotalEvents;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> eventList;
    private boolean isNameMode = true; // Toggle mode (Name/Number)
    private CounterModel counterModel; // Model for SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);

        // Setting up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enabling Back Navigation in Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Handling system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializing CounterModel for managing SharedPreferences
        counterModel = new CounterModel(this);

        // Initializing UI Components
        tvEventA = findViewById(R.id.tvEventA);
        tvEventB = findViewById(R.id.tvEventB);
        tvEventC = findViewById(R.id.tvEventC);
        tvTotalEvents = findViewById(R.id.tvTotalEvents);
        listView = findViewById(R.id.lvEventList);

        // Loading event data and update UI
        loadEventList();
        updateEventSummary();
    }

    // Loading events into the ListView
    private void loadEventList() {
        eventList = counterModel.getEventList();
        updateListView();
    }

    // Updating Event Summary with Counts
    private void updateEventSummary() {
        int countA = 0, countB = 0, countC = 0;

        // Always recalculate counts when switching
        for (String event : eventList) {
            if (event.equals(counterModel.getCounter1Name()) || event.equals("1")) countA++;
            else if (event.equals(counterModel.getCounter2Name()) || event.equals("2")) countB++;
            else if (event.equals(counterModel.getCounter3Name()) || event.equals("3")) countC++;
        }

        // Updating TextViews dynamically based on mode
        if (isNameMode) {
            tvEventA.setText(counterModel.getCounter1Name() + ": " + countA + " events");
            tvEventB.setText(counterModel.getCounter2Name() + ": " + countB + " events");
            tvEventC.setText(counterModel.getCounter3Name() + ": " + countC + " events");
        } else {
            tvEventA.setText("Counter 1: " + countA + " events");
            tvEventB.setText("Counter 2: " + countB + " events");
            tvEventC.setText("Counter 3: " + countC + " events");
        }

        // Updating the total count of all events
        tvTotalEvents.setText("Total events: " + eventList.size());
    }

    // Toggling between Event Names and Counter Numbers
    private void toggleEventMode() {
        isNameMode = !isNameMode;

        // Converting event names to numbers or vice versa
        List<String> updatedList = new ArrayList<>();
        for (String event : eventList) {
            if (isNameMode) {
                // Converting numbers back to names
                if (event.equals("1")) updatedList.add(counterModel.getCounter1Name());
                else if (event.equals("2")) updatedList.add(counterModel.getCounter2Name());
                else if (event.equals("3")) updatedList.add(counterModel.getCounter3Name());
                else updatedList.add(event);
            } else {
                // Converting names to numbers
                if (event.equals(counterModel.getCounter1Name())) updatedList.add("1");
                else if (event.equals(counterModel.getCounter2Name())) updatedList.add("2");
                else if (event.equals(counterModel.getCounter3Name())) updatedList.add("3");
                else updatedList.add(event);
            }
        }

        // Updating event list and refresh UI
        eventList.clear();
        eventList.addAll(updatedList);
        updateListView();
        updateEventSummary();
    }

    // Updating ListView Adapter
    private void updateListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        listView.setAdapter(adapter);
    }

    // Inflating toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_menu, menu);
        return true;
    }

    // Handling toolbar menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_event_names) {
            toggleEventMode(); // Toggling between event names/numbers
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish(); // Navigate back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
