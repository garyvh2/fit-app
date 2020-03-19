package com.gitgud.fitapp.ui.modules.pokemonInfo;

import com.gitgud.fitapp.data.source.PokemonDataSource;
import com.gitgud.fitapp.ui.modules.pokemon.PokemonNavigator;

public class PokemonInfoModule {
    public static PokemonInfoViewModel createViewModel() {
        return new PokemonInfoViewModel(
                PokemonDataSource.getInstance(),
                new PokemonNavigator()
        );
    }
}
