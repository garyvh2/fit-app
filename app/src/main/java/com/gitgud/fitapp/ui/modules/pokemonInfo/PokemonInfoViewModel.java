package com.gitgud.fitapp.ui.modules.pokemonInfo;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;
import com.gitgud.fitapp.data.source.PokemonDataSource;
import com.gitgud.fitapp.entities.pokemon.PokemonQuery;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PokemonInfoViewModel extends BaseObservable {
    // INJECTION START
    @NonNull
    private PokemonDataSource pokemonDataSource;
    // INJECTION END


    // ATTRIBUTES START
    private Boolean loading;
    private PokemonQuery.Pokemon pokemon;
    // ATTRIBUTES END

    public PokemonInfoViewModel(@NonNull PokemonDataSource pokemonDataSource) {
        this.pokemonDataSource = pokemonDataSource;
    }

    public Observable<PokemonQuery.Data> getPokemonByName(@NonNull String name) {
        setLoading(true);
        return this.pokemonDataSource
                .getPokemonByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Bindable
    public PokemonQuery.Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonQuery.Pokemon pokemon) {
        this.pokemon = pokemon;
        setLoading(false);
        notifyPropertyChanged(BR.pokemon);
    }

    @Bindable
    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }
}
