package com.refridgeapp;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.view.View;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Executor cameraExecutor = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        previewView = findViewById(R.id.previewView);


        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // This should never be reached, if it is, something has gone deeply deeply wrong
            }
        }, ContextCompat.getMainExecutor(this));

    }
    // gives us our preview and configures the app to take photos
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageCapture imageCapture = new ImageCapture.Builder()
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();


        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, preview);

        TextRecognizer recognizer =
                TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Button captureImage = findViewById(R.id.captureImage);
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCapture.takePicture(cameraExecutor,
                        new ImageCapture.OnImageCapturedCallback() {
                            @Override
                            public void onCaptureSuccess(ImageProxy imageProxy) {
                                // image analysis here
                                @SuppressLint("UnsafeOptInUsageError") Image mediaImage = imageProxy.getImage();
                                if (mediaImage != null) {
                                    InputImage image =
                                            InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                                    Task<Text> result =
                                            recognizer.process(image)
                                                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                                        @Override
                                                        public void onSuccess(Text visionText) {
                                                            String resultText = visionText.getText();
                                                            //Log.d("Receipt", resultText);
                                                            List<String> resultList = Receipt.getItemList(visionText);
                                                            //Log.d("Receipt List", String.valueOf(resultList));
                                                            // analyze text
                                                            // dump text to database
                                                        }
                                                    })
                                                    .addOnFailureListener(
                                                            new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // handle errors
                                                                }
                                                            });

                                }

                                // close the image
                                imageProxy.close();
                                // send the user to the add grocery view
                                /*
                                Intent intent = new Intent(this, AddGroceryActivity.class);
                                startActivity(intent); */
                            }
                            @Override
                            public void onError(ImageCaptureException error) {
                                // error handling
                            }
                        }
                );
            }
        });
    }




}



