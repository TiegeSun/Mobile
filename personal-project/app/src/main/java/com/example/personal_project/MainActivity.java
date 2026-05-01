package com.example.personal_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<StudyTask> allTasks = new ArrayList<>();
    private final ArrayList<StudyTask> visibleTasks = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private EditText taskEditText;
    private Spinner categorySpinner;
    private Switch importantSwitch;
    private Switch filterSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View mainView = findViewById(R.id.main);
        int baseLeft = mainView.getPaddingLeft();
        int baseTop = mainView.getPaddingTop();
        int baseRight = mainView.getPaddingRight();
        int baseBottom = mainView.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    baseLeft + systemBars.left,
                    baseTop + systemBars.top,
                    baseRight + systemBars.right,
                    baseBottom + systemBars.bottom
            );
            return insets;
        });

        taskEditText = (EditText) findViewById(R.id.taskEditText);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        importantSwitch = (Switch) findViewById(R.id.importantSwitch);
        filterSwitch = (Switch) findViewById(R.id.filterSwitch);
        Button addButton = (Button) findViewById(R.id.addTaskButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        ListView taskListView = (ListView) findViewById(R.id.taskListView);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.study_categories,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        taskAdapter = new TaskAdapter(this, visibleTasks);
        taskListView.setAdapter(taskAdapter);

        seedDefaultTasks();
        refreshTasks();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seedDefaultTasks();
                filterSwitch.setChecked(false);
                refreshTasks();
                Toast.makeText(MainActivity.this, "Default study plan restored", Toast.LENGTH_SHORT).show();
            }
        });

        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTasks();
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudyTask task = visibleTasks.get(position);
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_TITLE, task.getTitle());
                detailIntent.putExtra(DetailActivity.EXTRA_CATEGORY, task.getCategory());
                detailIntent.putExtra(DetailActivity.EXTRA_IMPORTANT, task.isImportant());
                startActivity(detailIntent);
            }
        });
    }

    private void addTask() {
        String title = taskEditText.getText().toString().trim();
        if (title.length() == 0) {
            Toast.makeText(this, "Write a study task first", Toast.LENGTH_SHORT).show();
            return;
        }

        String category = categorySpinner.getSelectedItem().toString();
        boolean important = importantSwitch.isChecked();
        allTasks.add(new StudyTask(title, category, important));

        taskEditText.setText("");
        importantSwitch.setChecked(false);
        refreshTasks();
    }

    private void seedDefaultTasks() {
        allTasks.clear();
        allTasks.add(new StudyTask("Watch Android lesson video", "Android", true));
        allTasks.add(new StudyTask("Write learning diary", "Writing", true));
        allTasks.add(new StudyTask("Practice ListView project", "Practice", false));
    }

    private void refreshTasks() {
        visibleTasks.clear();
        boolean importantOnly = filterSwitch.isChecked();
        for (StudyTask task : allTasks) {
            if (!importantOnly || task.isImportant()) {
                visibleTasks.add(task);
            }
        }
        taskAdapter.notifyDataSetChanged();
    }
}
