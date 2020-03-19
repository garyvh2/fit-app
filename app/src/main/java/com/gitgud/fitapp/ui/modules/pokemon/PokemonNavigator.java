package com.gitgud.fitapp.ui.modules.pokemon;

import android.content.Context;
import android.content.Intent;

import com.gitgud.fitapp.ui.modules.pokemonInfo.PokemonInfoActivity;

public class PokemonNavigator {
    public void navigateToPokemonInfo(Context context, String name) {
        Intent intent = new Intent(context, PokemonInfoActivity.class);
        intent.putExtra("pokemonName", name);
        context.startActivity(intent);
    }
}
