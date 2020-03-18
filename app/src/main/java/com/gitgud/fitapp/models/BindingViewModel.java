package com.gitgud.fitapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;
import com.gitgud.fitapp.fragment.Pokemon;

public class BindingViewModel extends BaseObservable {
    private Pokemon pokemon;
    private Boolean loading;

    @Bindable
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        notifyPropertyChanged(BR.pokemon);
    }

    @Bindable
    public Boolean getLoading() { return loading; }

    public void setLoading(Boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }
}