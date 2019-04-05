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
                if (isUserInputValid()) {
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

    public boolean validatePasswordInput() {
        boolean isValidPass = false;

        //Get values from EditText field
        String password = editText_password.getText().toString();

        if (password.isEmpty()) {
            textInput_password.setError("Please enter valid password!");
        } else {
            if (password.length() > 5) {
                isValidPass = true;
                textInput_password.setError(null);
            } else {
                textInput_password.setError("Password is too short!");
            }
        }

        return isValidPass;
    }

    public boolean validateEmailInput() {
        boolean isValidEmail = false;

        //Get values from EditText field
        String email = editText_email.getText().toString();

        //Handling validation for email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInput_email.setError("Please enter valid email!");
        } else {
            isValidEmail = true;
            textInput_email.setError(null);
        }
        return isValidEmail;
    }

    public boolean validateUserNameInput() {
        boolean isValidUserName = false;

        String userName = editText_userName.getText().toString();

        //Handling validation for UserName field
        if (userName.isEmpty()) {
            textInput_userName.setError("Please enter valid username!");
        } else {
            if (userName.length() > 4) {
                isValidUserName = true;
                textInput_userName.setError(null);
            } else {
                textInput_userName.setError("Username is too short!");
            }
        }
        return isValidUserName;
    }
    public boolean isUserInputValid() {
        boolean isValid = false;

        if(validateEmailInput() && validatePasswordInput() && validateUserNameInput()) {
            isValid = true;
        }

        return isValid;
    }

}
