package com.gitgud.fitapp.ui.nutritional.create;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.adapters.NutritionalFactsAdapter;
import com.gitgud.fitapp.adapters.TextInputLayoutAdapter;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.databinding.NutritionalCreateFragmentBinding;
import com.gitgud.fitapp.type.ProductInputType;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NutritionalCreate extends Fragment implements Validator.ValidationListener {
    private int REQUEST_IMAGE_CAPTURE = 1;

    private View fragment;
    private ImageButton addNutritional;
    private TextInputEditText nutritionalFact;
    private NutritionalFactsAdapter nutritionalFactsAdapter;
    private NutritionalCreateViewModel viewModel;
    private NutritionalCreateFragmentBinding binding;

    private Validator validator;

    private Button save;

    private ImageView photo;

    private File image;

    @NotEmpty
    private TextInputEditText sku;
    @NotEmpty
    private TextInputEditText name;
    @NotEmpty
    private TextInputEditText calories;
    @NotEmpty
    private TextInputEditText portion;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.nutritional_create_fragment, container, false);
        binding.setLifecycleOwner(this);

        fragment = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(NutritionalCreateViewModel.class);
        binding.setViewModel(viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = fragment.findViewById(R.id.nutritionalProducts);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        nutritionalFactsAdapter = new NutritionalFactsAdapter(getContext(), new ArrayList<>(), (view, item) -> {
            List<String> nutritionalFacts = viewModel.getNutritionalFacts().getValue();
            nutritionalFacts.remove(item);
            viewModel.setNutritionalFacts(nutritionalFacts);
            return null;
        });

        nutritionalFact = fragment.findViewById(R.id.nutritionalFact);
        addNutritional = fragment.findViewById(R.id.addNutritional);
        addNutritional.setOnClickListener(view -> {
            if (!nutritionalFact.getText().toString().isEmpty()) {
                List<String> nutritionalFacts = viewModel.getNutritionalFacts().getValue();
                nutritionalFacts.add(nutritionalFact.getText().toString());
                viewModel.setNutritionalFacts(nutritionalFacts);
                nutritionalFact.setText("");
            }
        });

        photo = fragment.findViewById(R.id.photo);
        photo.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        recyclerView.setAdapter(nutritionalFactsAdapter);

        viewModel.getNutritionalFacts().observe(getViewLifecycleOwner(), nutritionalFacts -> {
            nutritionalFactsAdapter.setNutritionalFacts(nutritionalFacts);
            nutritionalFactsAdapter.notifyDataSetChanged();
        });

        save = fragment.findViewById(R.id.save);
        save.setOnClickListener(this::saveProduct);

        sku = fragment.findViewById(R.id.sku);
        name = fragment.findViewById(R.id.name);
        calories = fragment.findViewById(R.id.calories);
        portion = fragment.findViewById(R.id.portion);


        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.registerAdapter(TextInputLayout.class, new TextInputLayoutAdapter());
        validator.setViewValidatedAction(view -> {
            if (view instanceof TextInputLayout) {
                TextInputLayout textInputLayout = (TextInputLayout) view;
                textInputLayout.setError("");
                textInputLayout.setErrorEnabled(false);
            }
        });

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String sku = NutritionalCreateArgs.fromBundle(getArguments()).getSku();
        viewModel.setSku(sku);
    }


    private void saveProduct(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        this.setEnabled(false);
        AsyncTask.execute(() -> {
            try {
                String sku = this.viewModel.getSku().getValue();
                String name = this.viewModel.getName().getValue();
                Double calories = new BigDecimal(this.viewModel.getCalories().getValue()).doubleValue();
                Double portion = new BigDecimal(this.viewModel.getPortion().getValue()).doubleValue();
                List<String> nutritionalFact = this.viewModel.getNutritionalFacts().getValue();

                if (image != null) {
                    viewModel.insertProduct(sku, name, calories, portion, nutritionalFact, image)
                            .subscribe(
                                    product -> {
                                        if (product.isPresent()) {
                                            AsyncTask.execute(()->{
                                                User user = viewModel.getCurrentUser();
                                                if (user != null) {
                                                    viewModel.insertProductUser(user.getEmail(), ProductInputType.builder().id(product.get().id()).build())
                                                            .subscribe(
                                                                    productUser -> {
                                                                        Navigation.findNavController(fragment).navigate(R.id.nutritionalDashboard);
                                                                    },
                                                                    throwable -> {
                                                                        Log.e("NutritionalCreate", throwable.toString());
                                                                    }
                                                            );
                                                }
                                            });
                                        }
                                    },
                                    throwable -> {
                                        Log.e("NutritionalCreate", throwable.toString());
                                    }
                            );
                } else {
                    Toast.makeText(getContext(), "Please setup an image", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                Log.e("NutritionalCreate", e.getMessage());
                this.setEnabled(true);
            }
        });
    }

    private void setEnabled(boolean state) {
        getActivity().runOnUiThread(() -> {
            save.setEnabled(state);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);

            try {
                File file = new File(getContext().getCacheDir().getPath(), "product_photo");
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                image = file;
            } catch (Exception e) {
                Log.e("NutritionalCreate", e.getMessage());
            }
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages
            if (view instanceof TextInputLayout) {
                TextInputLayout textInputLayout = (TextInputLayout) view;
                textInputLayout.setError(message);
                textInputLayout.setErrorEnabled(true);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
