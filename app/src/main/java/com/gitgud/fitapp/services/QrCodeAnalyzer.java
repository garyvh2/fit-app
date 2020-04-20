package com.gitgud.fitapp.services;

import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.List;
import java.util.function.Function;

public class QrCodeAnalyzer implements ImageAnalysis.Analyzer {
    private Function<List<FirebaseVisionBarcode>, Void> callback;

    public QrCodeAnalyzer(Function<List<FirebaseVisionBarcode>, Void>  callback) {
        this.callback = callback;
    }


    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void analyze(@NonNull ImageProxy image) {
        FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_CODABAR,
                        FirebaseVisionBarcode.FORMAT_CODE_39,
                        FirebaseVisionBarcode.FORMAT_CODE_93,
                        FirebaseVisionBarcode.FORMAT_CODE_128,
                        FirebaseVisionBarcode.FORMAT_EAN_8,
                        FirebaseVisionBarcode.FORMAT_EAN_13,
                        FirebaseVisionBarcode.FORMAT_ITF,
                        FirebaseVisionBarcode.FORMAT_UPC_A,
                        FirebaseVisionBarcode.FORMAT_UPC_E
                )
                .build();
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
        FirebaseVisionImage visionImage = FirebaseVisionImage.fromMediaImage(image.getImage(), rotationDegreesToFirebaseRotation(image.getImageInfo().getRotationDegrees()));

        detector.detectInImage(visionImage)
            .addOnSuccessListener(firebaseVisionBarcodes -> {
                if (!firebaseVisionBarcodes.isEmpty()) {
                    callback.apply(firebaseVisionBarcodes);
                }
                image.close();
            })
            .addOnFailureListener(e -> {
                Log.e("QrCodeAnalyzer", e.getMessage());
            });
    }

    private int rotationDegreesToFirebaseRotation(int rotationDegress) {
        switch (rotationDegress) {
            case 0:
                return FirebaseVisionImageMetadata.ROTATION_0;
            case 90:
                return FirebaseVisionImageMetadata.ROTATION_90;
            case 180:
                return FirebaseVisionImageMetadata.ROTATION_180;
            case 270:
                return FirebaseVisionImageMetadata.ROTATION_270;
            default:
                Log.e("QrCodeAnalyzer", "INVALID ROTATION");
                return FirebaseVisionImageMetadata.ROTATION_0;
        }
    }
}
