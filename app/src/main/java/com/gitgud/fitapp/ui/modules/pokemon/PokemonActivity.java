package com.gitgud.fitapp.ui.modules.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.ActivityPokemonBinding;
import com.gitgud.fitapp.entities.pokemon.PokemonsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonActivity extends AppCompatActivity {
    @NonNull
    private PokemonViewModel pokemonViewModel;
    @NonNull
    ActivityPokemonBinding binding;
    // VIEWS START
    ListView listView;
    // VIEWS END

    ArrayAdapter<String> adapter;
    ArrayList<String> pokemonNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        //  BINDING START
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon);
        pokemonViewModel = PokemonModule.createViewModel();
        binding.setViewModel(pokemonViewModel);
        //  BINDING END

        // LIST START
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, pokemonNames);
        listView = findViewById(R.id.attacks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            // ListView Clicked item index
            pokemonViewModel.getPokemonNavigator().navigateToPokemonInfo(this, adapter.getItem(position));
        });
        // LIST END

    }

    @Override
    protected void onStart() {
        super.onStart();
        pokemonViewModel.getPokemonsQuery(100)
            .subscribe(
                    this::onPokemonSuccess,
                    throwable -> Log.e("[POKEMON]", throwable.getMessage())
        );
    }

    private void onPokemonSuccess(List<PokemonsQuery.Pokemon> pokemons){
        if (pokemons != null && !pokemons.isEmpty()) {
            pokemonViewModel.setPokemons(pokemons);
            List<String> evolutionData = pokemons.stream().map(PokemonsQuery.Pokemon::name).collect(Collectors.toList());
            ArrayList<String>arrayList = new ArrayList<>(evolutionData);
            pokemonNames.clear();
            pokemonNames.addAll(arrayList);
            adapter.notifyDataSetInvalidated();
            listView.invalidateViews();
            listView.refreshDrawableState();
        }
    }
}
