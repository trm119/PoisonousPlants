package com.example.plants.poisonousplants.View.activities;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.plants.poisonousplants.R;
import com.example.plants.poisonousplants.View.helpers.ShowCamera;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        // open camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

        // If "failed to connect to camera service", must give app permissions in device settings.

    }


}
