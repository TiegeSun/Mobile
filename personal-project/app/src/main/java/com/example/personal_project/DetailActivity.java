package com.example.personal_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.personal_project.TITLE";
    public static final String EXTRA_CATEGORY = "com.example.personal_project.CATEGORY";
    public static final String EXTRA_IMPORTANT = "com.example.personal_project.IMPORTANT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        android.view.View detailView = findViewById(R.id.detailMain);
        int baseLeft = detailView.getPaddingLeft();
        int baseTop = detailView.getPaddingTop();
        int baseRight = detailView.getPaddingRight();
        int baseBottom = detailView.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    baseLeft + systemBars.left,
                    baseTop + systemBars.top,
                    baseRight + systemBars.right,
                    baseBottom + systemBars.bottom
            );
            return insets;
        });

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String category = getIntent().getStringExtra(EXTRA_CATEGORY);
        boolean important = getIntent().getBooleanExtra(EXTRA_IMPORTANT, false);

        TextView titleTextView = (TextView) findViewById(R.id.detailTitleTextView);
        TextView categoryTextView = (TextView) findViewById(R.id.detailCategoryTextView);
        TextView priorityTextView = (TextView) findViewById(R.id.detailPriorityTextView);
        TextView tipTextView = (TextView) findViewById(R.id.detailTipTextView);

        titleTextView.setText(title);
        categoryTextView.setText("Category: " + category);
        priorityTextView.setText(important ? "Priority: Important" : "Priority: Normal");
        tipTextView.setText(getStudyTip(category, important));
    }

    private String getStudyTip(String category, boolean important) {
        String prefix = important ? "Start with this one today. " : "Keep this in your weekly rhythm. ";
        if ("Android".equals(category)) {
            return prefix + "Open the project, run the screen, then explain what each file does.";
        } else if ("Writing".equals(category)) {
            return prefix + "Write a short diary while the lesson is still fresh.";
        } else if ("Practice".equals(category)) {
            return prefix + "Change one small feature and build the app again.";
        } else {
            return prefix + "Break the task into one small step and finish that first.";
        }
    }
}
