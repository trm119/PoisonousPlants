package com.example.plants.poisonousplants.View.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plants.poisonousplants.View.User;
import com.example.plants.poisonousplants.View.helpers.DbHelper;

import com.example.plants.poisonousplants.R;


public class RegisterActivity extends AppCompatActivity {

    EditText editText_userName;
    EditText editText_email;
    EditText editText_password;

    TextInputLayout textInput_userName;
    TextInputLayout textInput_email;
    TextInputLayout textInput_password;

    Button register_button;

     DbHelper db_Helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db_Helper = new DbHelper(this);

        initializeTextViewLogin();
        initViews();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    String UserName = editText_userName.getText().toString();
                    String Email = editText_email.getText().toString();
                    String Password = editText_password.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!db_Helper.doesEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        db_Helper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(register_button, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(register_button, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    //set Login TextView click event
    private void initializeTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //connect XML views to its Objects
    private void initViews() {
        editText_email = (EditText) findViewById(R.id.editTextEmail);
        editText_password = (EditText) findViewById(R.id.editTextPassword);
        editText_userName = (EditText) findViewById(R.id.editTextUserName);
        textInput_email = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInput_password = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInput_userName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        register_button = (Button) findViewById(R.id.buttonRegister);

    }

    // validate input given by user
    public boolean validateInput() {
        boolean isValid = false;

        //Get values from EditText fields
        String UserName = editText_userName.getText().toString();
        String Email = editText_email.getText().toString();
        String Password = editText_password.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            isValid = false;
            textInput_userName.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                isValid = true;
                textInput_userName.setError(null);
            } else {
                isValid = false;
                textInput_userName.setError("Username is too short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            isValid = false;
            textInput_email.setError("Please enter valid email!");
        } else {
            isValid = true;
            textInput_email.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            isValid = false;
            textInput_password.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                isValid = true;
                textInput_password.setError(null);
            } else {
                isValid = false;
                textInput_password.setError("Password is to short!");
            }
        }


        return isValid;
    }
}
