package com.gitgud.fitapp.ui.nutritional.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.ProductDataSource;
import com.gitgud.fitapp.entities.product.GetProductBySkuQuery;
import com.gitgud.fitapp.entities.product.GetProductsByUserQuery;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NutritionalDashboardViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private ProductDataSource productDataSource;

    public NutritionalDashboardViewModel(@NonNull Application application) {
        super(application);
        this.productDataSource = ProductDataSource.getInstance();
        userRepository = new UserRepository(application);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUserSync();
    }

    public Observable<Optional<List<GetProductsByUserQuery.UserFood>>> getProductsByUser(String email) {
        return this.productDataSource.getProductsByUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
