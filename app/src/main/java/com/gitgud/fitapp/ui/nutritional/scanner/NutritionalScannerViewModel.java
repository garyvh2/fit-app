package com.gitgud.fitapp.ui.nutritional.scanner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Product;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.ProductDataSource;
import com.gitgud.fitapp.entities.product.GetProductBySkuQuery;
import com.gitgud.fitapp.entities.product.InsertProductUserMutation;
import com.gitgud.fitapp.type.CompanyInputType;
import com.gitgud.fitapp.type.ProductInputType;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NutritionalScannerViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private UserRepository userRepository;
    private ProductDataSource productDataSource;

    public NutritionalScannerViewModel(@NonNull Application application) {
        super(application);
        this.productDataSource = ProductDataSource.getInstance();
        userRepository = new UserRepository(application);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUserSync();
    }

    public Observable<Optional<GetProductBySkuQuery.Product>> getProductBySku(String sku) {
        return this.productDataSource.getProductBySku(sku)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Optional<InsertProductUserMutation.AddUserProduct>> insertProductUser(String email, ProductInputType productInputType) {
        return this.productDataSource.insertProductUser(email, productInputType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
