package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton, deleteButton;
    int selectedPosition = -1; // -1 nothing selected


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.button_add_city);
        deleteButton = findViewById(R.id.button_delete_city);
        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // adapter
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);

        // select row
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true);
        });

        // add City
        addButton.setOnClickListener(v -> {
            final EditText input = new EditText(this);
            input.setHint("City name");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");
            builder.setView(input);

            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String name = input.getText().toString().trim();
                if (name.isEmpty()) {
                    return; // do nothing if input blank
                }
                dataList.add(name);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1;
                cityList.clearChoices();
            });

            builder.setNegativeButton("CANCEL", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // delete City
        deleteButton.setOnClickListener(v -> {
            if (selectedPosition < 0 || selectedPosition >= dataList.size()) {
                return; // do nothing if nothing selected
            }
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            selectedPosition = -1;
            cityList.clearChoices();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}