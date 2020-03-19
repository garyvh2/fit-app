package com.gitgud.fitapp.ui.modules.pokemonInfo;

import com.gitgud.fitapp.data.source.PokemonDataSource;

public class PokemonInfoModule {
    public static PokemonInfoViewModel createViewModel() {
        return new PokemonInfoViewModel(
                PokemonDataSource.getInstance()
        );
    }
}
