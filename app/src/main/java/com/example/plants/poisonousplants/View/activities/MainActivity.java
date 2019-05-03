package com.example.plants.poisonousplants.View.activities;

import android.content.Intent;
import android.hardware.Camera;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    /** On startup, display the camera to the user.
     *@param savedInstanceState A Bundle object that can restore the
     * activity to a previous state.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        log_button = (Button) findViewById(R.id.log_button);

        // open camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
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
