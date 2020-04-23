package com.gitgud.fitapp.ui.nutritional.create;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.gitgud.fitapp.data.model.Product;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.ProductDataSource;
import com.gitgud.fitapp.entities.product.GetProductBySkuQuery;
import com.gitgud.fitapp.entities.product.InsertProductMutation;
import com.gitgud.fitapp.entities.product.InsertProductUserMutation;
import com.gitgud.fitapp.type.ProductInputType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NutritionalCreateViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private UserRepository userRepository;

    private ProductDataSource productDataSource;

    private MutableLiveData<String> sku = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Float> calories = new MutableLiveData<>();
    private MutableLiveData<Float> portion = new MutableLiveData<>();
    private MutableLiveData<List<String>> nutritionalFacts = new MutableLiveData<>();

    public NutritionalCreateViewModel(@NonNull Application application) {
        super(application);
        this.productDataSource = ProductDataSource.getInstance();

        /**
         * Init Product
         */
        sku.setValue("");
        name.setValue("");
        calories.setValue(0.0f);
        portion.setValue(0.0f);
        nutritionalFacts.setValue(new ArrayList<>());

        userRepository = new UserRepository(application);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUserSync();
    }

    public Observable<Optional<InsertProductMutation.AddProduct>> insertProduct(String sku, String name, Double calories, Double portion, List<String> nutritionalFacts) {
        return this.productDataSource.insertProduct(sku, name, calories, portion, nutritionalFacts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

    public MutableLiveData<String> getSku() {
        return sku;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Float> getCalories() {
        return calories;
    }

    public MutableLiveData<Float> getPortion() {
        return portion;
    }

    public MutableLiveData<List<String>> getNutritionalFacts() {
        return nutritionalFacts;
    }

    public void setSku(String sku) {
        this.sku.setValue(sku);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setCalories(Float calories) {
        this.calories.setValue(calories);
    }

    public void setPortion(Float portion) {
        this.portion.setValue(portion);
    }

    public void setNutritionalFacts(List<String> nutritionalFacts) {
        this.nutritionalFacts.setValue(nutritionalFacts);
    }
}
