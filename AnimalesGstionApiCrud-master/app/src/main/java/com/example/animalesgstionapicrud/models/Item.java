package com.example.animalesgstionapicrud.models;

public class Item {
    private String title;
    private String description;
    private int imageResource;
    private int backgroundColor;

    public Item(String title, String description, int imageResource, int backgroundColor) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}