package com.example.personal_project;

public class StudyTask {
    private final String title;
    private final String category;
    private final boolean important;

    public StudyTask(String title, String category, boolean important) {
        this.title = title;
        this.category = category;
        this.important = important;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public boolean isImportant() {
        return important;
    }
}
