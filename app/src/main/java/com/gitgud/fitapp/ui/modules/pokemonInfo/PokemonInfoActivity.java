package com.gitgud.fitapp.ui.modules.pokemonInfo;

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
import com.gitgud.fitapp.databinding.ActivityPokemonInfoBinding;
import com.gitgud.fitapp.entities.pokemon.PokemonQuery;
import com.gitgud.fitapp.entities.pokemon.PokemonsQuery;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonInfoActivity extends AppCompatActivity {
    @NonNull
    private PokemonInfoViewModel pokemonViewModel;
    @NonNull
    ActivityPokemonInfoBinding binding;

    String pokemonName;

    // LIST START
    ListView attacksListView;
    ArrayAdapter<String> adapterAttacks;
    ArrayList<String> attackNames = new ArrayList<>();
    // LIST END

    // LIST START
    ListView evolutionsListView;
    ArrayAdapter<String> adapterEvolutions;
    ArrayList<String> evolutionNames = new ArrayList<>();
    // LIST END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);

        pokemonName = getIntent().getStringExtra ("pokemonName");

        //  BINDING START
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_info);
        pokemonViewModel = PokemonInfoModule.createViewModel();
        binding.setViewModel(pokemonViewModel);
        //  BINDING END

        // LIST START
        adapterAttacks = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, attackNames);
        attacksListView = findViewById(R.id.attacks);
        attacksListView.setAdapter(adapterAttacks);
        // LIST END

        // LIST START
        adapterEvolutions = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, evolutionNames);
        evolutionsListView = findViewById(R.id.evolutions);
        evolutionsListView.setAdapter(adapterEvolutions);
        evolutionsListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            // ListView Clicked item index
            pokemonViewModel.getPokemonNavigator().navigateToPokemonInfo(this, adapterEvolutions.getItem(position));
        });
        // LIST END


    }

    @Override
    protected void onStart() {
        super.onStart();
        pokemonViewModel.getPokemonByName(pokemonName)
                .subscribe(
                        this::onPokemonSuccess,
                        throwable -> Log.e("[POKEMON]", throwable.getMessage())
                );
    }

    private void onPokemonSuccess(PokemonQuery.Data pokemon){
        pokemonViewModel.setPokemon(pokemon.pokemon());
        if (pokemon != null) {
            if (pokemon.pokemon().attacks() != null) {
                List<String> attackData = pokemon.pokemon().attacks().special().stream().map(
                        attack -> MessageFormat.format("{0} / {1}, dmg: {2}", attack.name(), attack.type(), attack.damage())
                ).collect(Collectors.toList());
                ArrayList<String> attackArrayList = new ArrayList<>(attackData);
                attackNames.clear();
                attackNames.addAll(attackArrayList);
                adapterAttacks.notifyDataSetInvalidated();
                attacksListView.invalidateViews();
                attacksListView.refreshDrawableState();
            }

            if (pokemon.pokemon().evolutions() != null && !pokemon.pokemon().evolutions().isEmpty()) {
                List<String> evolutionData = pokemon.pokemon().evolutions().stream().map(PokemonQuery.Evolution::name).collect(Collectors.toList());
                ArrayList<String> evolutionsArrayList = new ArrayList<>(evolutionData);
                evolutionNames.clear();
                evolutionNames.addAll(evolutionsArrayList);
                adapterEvolutions.notifyDataSetInvalidated();
                evolutionsListView.invalidateViews();
                evolutionsListView.refreshDrawableState();
            }
        }
    }
}
