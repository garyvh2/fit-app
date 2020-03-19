package com.gitgud.fitapp.ui.modules.pokemon;

import com.gitgud.fitapp.data.source.PokemonDataSource;

public class PokemonModule {
    public static PokemonViewModel createViewModel() {
        return new PokemonViewModel(
                PokemonDataSource.getInstance(),
                new PokemonNavigator()
        );
    }
}
