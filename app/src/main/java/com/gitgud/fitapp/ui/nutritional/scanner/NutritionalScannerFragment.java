package com.gitgud.fitapp.ui.nutritional.scanner;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gitgud.fitapp.R;
import com.gitgud.fitapp.databinding.NutritionalScannerFragmentBinding;
import com.gitgud.fitapp.services.QrCodeAnalyzer;
import com.gitgud.fitapp.utils.Permissions;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.schedulers.Schedulers;

public class NutritionalScannerFragment extends Fragment {
    private final int CAMERA_GRANTED = 1;

    private View fragment;
    private Button button;
    private PreviewView previewView;
    private NutritionalScannerFragmentBinding binding;
    private NutritionalScannerViewModel viewModel;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    public NutritionalScannerFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.nutritional_scanner_fragment, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.nutritional_scanner_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(NutritionalScannerViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        previewView = fragment.findViewById(R.id.camera_view);
        button = fragment.findViewById(R.id.cameraRequest);

        button.setOnClickListener((View v) -> {
            requestPermissions();
        });

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermissions();
    }

    private void requestPermissions() {
        button.setVisibility(View.INVISIBLE);
        try {
            if (Permissions.arePermissionsGranted(getActivity(), Arrays.asList(Manifest.permission.CAMERA))) {
                requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_GRANTED
                );
            } else {
                startCamera();
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case CAMERA_GRANTED: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startCamera();
                    } else {
                        button.setVisibility(View.VISIBLE);
                    }
                    return;
                }
            }
        } catch (SecurityException exception) {
            Log.e("[Error]", exception.getMessage());
        }
    }

    private void startCamera() {
        button.setVisibility(View.INVISIBLE);
        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder()
                        .build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                QrCodeAnalyzer qrCodeAnalyzer = new QrCodeAnalyzer(firebaseVisionBarcodes -> {
                    firebaseVisionBarcodes.forEach(firebaseVisionBarcode -> {
                        Log.i("NutritionalScannerFragment", firebaseVisionBarcode.getDisplayValue());
                    });
                    return null;
                });
                HandlerThread handlerThread = new HandlerThread("AnalysisThread");
                handlerThread.start();
                Handler handler = new Handler();

                Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageAnalysis);

                preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));

                imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), qrCodeAnalyzer);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }
}
