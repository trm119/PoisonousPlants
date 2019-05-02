package com.example.plants.poisonousplants.View.activities;

import android.content.Intent;
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

/**
 * The class represents a Register Activity
 *
 * @author  Rogelio Paniagua
 * @version 1.0
 * @since   2019-02-27
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText editText_userName;
    private EditText editText_email;
    private EditText editText_password;

    private TextInputLayout textInput_userName;
    private TextInputLayout textInput_email;
    private TextInputLayout textInput_password;

    private Button register_button;

    private DbHelper db_Helper;

    /** Creates the Register Activity and initializes the widgets
     * @param savedInstanceState A Bundle object that can restore the
     * activity to a previous state.
     */
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

                String UserName = editText_userName.getText().toString();
                String Email = editText_email.getText().toString();
                String Password = editText_password.getText().toString();
                long user_id ;

                if (isUserInputValid(UserName, Email, Password)) {

                    //Check in the database is there any user associated with  this email
                    if (!db_Helper.doesEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        user_id = db_Helper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(register_button, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();

                        /*
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                        */
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(register_button, "User already exists with same email ", Snackbar.LENGTH_LONG).show();

                    }


                }
            }
        });
    }

    /** Initializes the Login text view click event
     *
     */
    private void initializeTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Connects XML views to their objects
     */
    private void initViews() {
        editText_email = (EditText) findViewById(R.id.editTextEmail);
        editText_password = (EditText) findViewById(R.id.editTextPassword);
        editText_userName = (EditText) findViewById(R.id.editTextUserName);
        textInput_email = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInput_password = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInput_userName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        register_button = (Button) findViewById(R.id.buttonRegister);

    }

    /** Validates password input from the user.
     *
     * @param password A String representing the user's password
     * @return A boolean determining whether the user's password is valid or not.
     */
    public boolean validatePasswordInput(String password) {
        boolean isValidPass = false;

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

    /** Validates email input from the user.
     *
     * @param email A String representing the user's email
     * @return A boolean determining whether the user's email is valid or not.
     */
    public boolean validateEmailInput(String email) {
        boolean isValidEmail = false;

        //Handling validation for email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInput_email.setError("Please enter valid email!");
        } else {
            isValidEmail = true;
            textInput_email.setError(null);
        }
        return isValidEmail;
    }

    /** Validates user name input from the user.
     *
     * @param userName A String representing the user's  user name
     * @return A boolean determining whether the user's user name is valid or not.
     */
    public boolean validateUserNameInput(String userName) {
        boolean isValidUserName = false;


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

    /** Validates input from the user
     *
     * @param userName A String representing the user's userName
     * @param email A String representing the user's email.
     * @param password A String representing the user's password
     * @return A boolean variable determining whether the user's input is valid or not.
     */
    public boolean isUserInputValid(String userName, String email, String password) {
        boolean isValid = false;

        if(validateEmailInput(email) && validatePasswordInput(password) && validateUserNameInput(userName)) {
            isValid = true;
        }

        return isValid;
    }

}
