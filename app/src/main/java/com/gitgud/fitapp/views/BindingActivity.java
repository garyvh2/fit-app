package com.gitgud.fitapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.PokemonQuery;
import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.ActivityBindingBinding;
import com.gitgud.fitapp.fragment.Pokemon;
import com.gitgud.fitapp.models.BindingViewModel;
import com.gitgud.fitapp.network.graphql.ApolloProvider;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindingActivity extends AppCompatActivity {

    ActivityBindingBinding binding;
    BindingViewModel bindingViewModel;

    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<String> evolutionNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        //  BINDING START
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        bindingViewModel = new BindingViewModel();
        binding.setViewModel(bindingViewModel);
        //  BINDING END

        listView = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, evolutionNames);
        listView.setAdapter(adapter);

        PokemonQuery query = PokemonQuery.builder().name("Charmander").build();
        ApolloCall<PokemonQuery.Data> apolloCall = ApolloProvider.getApolloInstance().query(query);

        bindingViewModel.setLoading(true);

        Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onSuccess,
                        throwable -> {
                            Log.e("[BINDING]", throwable.getStackTrace().toString());
                        }

                );

    }

    private void onSuccess(PokemonQuery.Data pokemonData) {
        Pokemon pokemon = pokemonData.pokemon().fragments().pokemon();
        bindingViewModel.setPokemon(pokemon);
        bindingViewModel.setLoading(false);


        if (pokemon.evolutions() != null && !pokemon.evolutions().isEmpty()) {
            List<String> evolutionData = pokemon.evolutions().stream().map(Pokemon.Evolution::name).collect(Collectors.toList());
            ArrayList<String>arrayList = new ArrayList<>(evolutionData);
            evolutionNames.clear();
            evolutionNames.addAll(arrayList);
            adapter.notifyDataSetInvalidated();
            listView.invalidateViews();
            listView.refreshDrawableState();
        }
    }
}
