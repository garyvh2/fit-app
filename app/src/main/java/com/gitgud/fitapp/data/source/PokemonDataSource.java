package com.gitgud.fitapp.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.entities.pokemon.PokemonQuery;
import com.gitgud.fitapp.entities.pokemon.PokemonsQuery;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;

import java.util.List;

import io.reactivex.Observable;

public class PokemonDataSource {
    @Nullable
    private static PokemonDataSource INSTANCE;

    private PokemonDataSource() {}

    public static PokemonDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (PokemonDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PokemonDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<PokemonQuery.Data> getPokemonById (@NonNull String id) {
        /**
         * Query Build
         */
        ApolloCall<PokemonQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        PokemonQuery.builder()
                                .id(id)
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    public Observable<PokemonQuery.Data> getPokemonByName (@NonNull String name) {
        /**
         * Query Build
         */
        ApolloCall<PokemonQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        PokemonQuery.builder()
                                .name(name)
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    public Observable<List<PokemonsQuery.Pokemon>> getPokemons (@NonNull int first) {
        /**
         * Query Build
         */
        ApolloCall<PokemonsQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        PokemonsQuery.builder()
                                .first(first)
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data().pokemons());
    }
}
