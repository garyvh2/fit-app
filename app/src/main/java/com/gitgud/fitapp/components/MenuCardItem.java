package com.gitgud.fitapp.components;

public class MenuCardItem {

    private int icon;
    private String title;
    private Class view;

    public MenuCardItem(int icon, String text, Class view) {
        this.icon = icon;
        this.title = text;
        this.view = view;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public Class getView() {
        return view;
    }
}
