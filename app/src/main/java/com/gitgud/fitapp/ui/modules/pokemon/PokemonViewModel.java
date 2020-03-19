package com.gitgud.fitapp.ui.modules.pokemon;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;
import com.gitgud.fitapp.data.source.PokemonDataSource;
import com.gitgud.fitapp.entities.pokemon.PokemonsQuery;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PokemonViewModel extends BaseObservable {
    // INJECTION START
    @NonNull
    private PokemonDataSource pokemonDataSource;
    @NonNull
    private PokemonNavigator pokemonNavigator;
    // INJECTION END

    // ATTRIBUTES START
    private Boolean loading;
    private List<PokemonsQuery.Pokemon> pokemons = new ArrayList<>();
    // ATTRIBUTES END

    @NonNull
    public PokemonNavigator getPokemonNavigator() {
        return pokemonNavigator;
    }


    public PokemonViewModel(@NonNull PokemonDataSource pokemonDataSource, @NonNull PokemonNavigator pokemonNavigator) {
        this.pokemonDataSource = pokemonDataSource;
        this.pokemonNavigator = pokemonNavigator;
    }


    public Observable<List<PokemonsQuery.Pokemon>> getPokemonsQuery(@NonNull int first) {
        setLoading(true);
        return this.pokemonDataSource
                .getPokemons(first)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Bindable
    public List<PokemonsQuery.Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonsQuery.Pokemon> pokemons) {
        this.pokemons = pokemons;
        setLoading(false);
        notifyPropertyChanged(BR.pokemons);
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
