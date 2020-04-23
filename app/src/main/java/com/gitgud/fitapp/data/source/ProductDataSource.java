package com.gitgud.fitapp.data.source;

import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.entities.product.GetProductBySkuQuery;
import com.gitgud.fitapp.entities.product.GetProductsByUserQuery;
import com.gitgud.fitapp.entities.product.InsertProductMutation;
import com.gitgud.fitapp.entities.product.InsertProductUserMutation;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;
import com.gitgud.fitapp.type.CompanyInputType;
import com.gitgud.fitapp.type.ProductInputType;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;

public class ProductDataSource {
    @Nullable
    private static ProductDataSource INSTANCE;

    private ProductDataSource() {}

    public static ProductDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    public Observable<Optional<InsertProductMutation.AddProduct>> insertProduct (String sku, String name, Double calories, Double portion, List<String> nutritionalFacts) {
        ApolloCall<InsertProductMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        InsertProductMutation.builder()
                                .sku(sku)
                                .name(name)
                                .calories(calories)
                                .portion(portion)
                                .nutritionalFacts(nutritionalFacts)
                                .company(
                                        CompanyInputType.builder().identifier("5e8d285bfb961700172465f4").build()
                                )
                                .build()
                );

        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    InsertProductMutation.Data data = dataResponse.data();
                    if (data.addProduct() != null) {
                        return Optional.of(data.addProduct());
                    }
                    return Optional.empty();
                });
    }

    @Nullable
    public Observable<Optional<GetProductBySkuQuery.Product>> getProductBySku (String sku) {
        ApolloCall<GetProductBySkuQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        GetProductBySkuQuery.builder()
                            .sku(sku)
                            .build()
                );

        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    GetProductBySkuQuery.Data data = dataResponse.data();
                    if (!data.products().isEmpty()) {
                        return Optional.of(data.products().get(0));
                    }
                    return Optional.empty();
                });
    }

    @Nullable
    public Observable<Optional<List<GetProductsByUserQuery.UserFood>>> getProductsByUser (String email) {
        ApolloCall<GetProductsByUserQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        GetProductsByUserQuery.builder()
                            .email(email)
                            .build()
                );

        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    GetProductsByUserQuery.Data data = dataResponse.data();
                    if (!data.userFood().isEmpty()) {
                        return Optional.of(data.userFood());
                    }
                    return Optional.empty();
                });
    }

    @Nullable
    public Observable<Optional<InsertProductUserMutation.AddUserProduct>> insertProductUser (String email, ProductInputType productInputType) {
        ApolloCall<InsertProductUserMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        InsertProductUserMutation.builder()
                            .email(email)
                            .product(productInputType)
                            .date(DateUtils.toString(new Date()))
                            .build()
                );

        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    InsertProductUserMutation.Data data = dataResponse.data();
                    if (data.addUserProduct() != null) {
                        return Optional.of(data.addUserProduct());
                    }
                    return Optional.empty();
                });
    }
}
