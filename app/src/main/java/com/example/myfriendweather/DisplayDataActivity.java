package com.example.myfriendweather;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayDataActivity extends AppCompatActivity {

    private TextView dataTextView;
    private Button backButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        // Initialize UI components
        dataTextView = findViewById(R.id.dataTextView);
        backButton = findViewById(R.id.backButton);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Fetch and display data
        displayAllUsers();

        // Set click listener for the back button
        backButton.setOnClickListener(v -> finish()); // Close this activity and go back
    }

    // Fetch and display all users from the database
    private void displayAllUsers() {
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor.getCount() == 0) {
            dataTextView.setText("No data found");
        } else {
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
                buffer.append("Username: ").append(cursor.getString(1)).append("\n");
                buffer.append("Location: ").append(cursor.getString(2)).append("\n");
                buffer.append("Phone: ").append(cursor.getString(3)).append("\n\n");
            }
            dataTextView.setText(buffer.toString());
        }
        cursor.close();
    }
}