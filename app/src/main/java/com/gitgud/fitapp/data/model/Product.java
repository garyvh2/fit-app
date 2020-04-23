package com.gitgud.fitapp.data.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Product {

    @NotNull
    private String id;

    @Nullable
    private String name;

    @Nullable
    private Double calories;

    @Nullable
    private Double portion;

    @Nullable
    private List<String> nutritionalFacts;

    @Nullable
    private String image;

    @Nullable
    private String sku;

    public Product() {
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public Double getCalories() {
        return calories;
    }

    public void setCalories(@Nullable Double calories) {
        this.calories = calories;
    }

    @Nullable
    public Double getPortion() {
        return portion;
    }

    public void setPortion(@Nullable Double portion) {
        this.portion = portion;
    }

    @Nullable
    public List<String> getNutritionalFacts() {
        return nutritionalFacts;
    }

    public void setNutritionalFacts(@Nullable List<String> nutritionalFacts) {
        this.nutritionalFacts = nutritionalFacts;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public void setSku(@Nullable String sku) {
        this.sku = sku;
    }
}
