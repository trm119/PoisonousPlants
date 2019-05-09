package com.example.plants.poisonousplants.View.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.example.plants.poisonousplants.R;
import com.example.plants.poisonousplants.View.User;
import com.example.plants.poisonousplants.View.helpers.ShowCamera;

/**
 * The class is the main entrance to the application that hosts the camera.
 *
 * @author  Taylor Mauldin
 * @version 1.0
 * @since   2019-04-06
 */

public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    private Button log_button;
    public static TextView plant_prediction;
    public static TextView plant_val;


    /** On startup, display the camera to the user.
     *@param savedInstanceState A Bundle object that can restore the
     * activity to a previous state.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView plant_prediction;
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        log_button = (Button) findViewById(R.id.log_button);
        plant_prediction = (TextView) findViewById(R.id.textViewPlant);
        plant_val = (TextView) findViewById(R.id.textViewVal);
        plant_prediction.setText("idle...");
        plant_val.setText("");
        boolean permissions = false;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }


        // open camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera, getApplicationContext());
        frameLayout.addView(showCamera);

        log_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openLoginActivity();

        }
    });


        // If "failed to connect to camera service", must give app permissions in device settings.

    }

    /** Transitions from this activity to the Login Activity
     *  using an Intent
     */
    public void openLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
