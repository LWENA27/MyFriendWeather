package com.example.myfriendweather;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private EditText usernameInput, locationInput, phoneInput;
    private Button submitButton, viewDataButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        usernameInput = findViewById(R.id.usernameInput);
        locationInput = findViewById(R.id.locationInput);
        phoneInput = findViewById(R.id.phoneInput);
        submitButton = findViewById(R.id.submitButton);
        viewDataButton = findViewById(R.id.viewDataButton);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch user input
                String username = usernameInput.getText().toString();
                String location = locationInput.getText().toString();
                String phone = phoneInput.getText().toString();

                // Validate input
                if (username.isEmpty() || location.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert data into the database
                    long result = databaseHelper.insertUser(username, location, phone);

                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Update the click listener for the viewDataButton
        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the DisplayDataActivity
                Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
                startActivity(intent);
            }
        });
    }

    // Clear input fields after submission
    private void clearInputs() {
        usernameInput.setText("");
        locationInput.setText("");
        phoneInput.setText("");
    }

    // Display all users from the database
    private void displayAllUsers() {
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            Log.d("Database", "No data found in the database");
        } else {
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()) {
                buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
                buffer.append("Username: ").append(cursor.getString(1)).append("\n");
                buffer.append("Location: ").append(cursor.getString(2)).append("\n");
                buffer.append("Phone: ").append(cursor.getString(3)).append("\n\n");
            }
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_LONG).show();
            Log.d("Database", "Data found: " + buffer.toString());
        }
        cursor.close();
    }
}
