package com.gitgud.fitapp.components;

public class MenuCardItem {

    private int cardComponent;
    private int icon;
    private String title;
    private Class view;
    private Boolean isView;
    private int fragment;
    private long id;

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
    public MenuCardItem(int cardComponent, int icon, String text, int fragment, long id) {
        this.cardComponent = cardComponent;
        this.icon = icon;
        this.title = text;
        this.fragment = fragment;
        this.isView = false;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
