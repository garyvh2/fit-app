package com.gitgud.fitapp.components;

public class MenuCardItem {

    private int cardComponent;
    private int icon;
    private String title;
    private Class view;
    private Boolean isView;
    private int fragment;

    public MenuCardItem(int cardComponent, int icon, String text, Class view) {
        this.cardComponent = cardComponent;
        this.icon = icon;
        this.title = text;
        this.view = view;
        this.isView = true;

    }

    public MenuCardItem(int cardComponent, int icon, String text, int fragment) {
        this.cardComponent = cardComponent;
        this.icon = icon;
        this.title = text;
        this.fragment = fragment;
        this.isView = false;
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

    public int getFragment() {
        return fragment;
    }
    public  Boolean getIsView () {
        return  isView;
    }

    public int getCardComponent() {
        return cardComponent;
    }
}
