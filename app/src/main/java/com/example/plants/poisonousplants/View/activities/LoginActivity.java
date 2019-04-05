package com.example.plants.poisonousplants.View.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.plants.poisonousplants.R;
import com.example.plants.poisonousplants.View.activities.RegisterActivity;
import com.example.plants.poisonousplants.View.helpers.DbHelper;
import com.example.plants.poisonousplants.View.User;

public class LoginActivity extends AppCompatActivity {


    private EditText editText_email;
    private EditText editText_password;

    private TextInputLayout textInputLayout_email;
    private TextInputLayout textInputLayout_password;

    private Button login_button;
    private DbHelper db_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db_Helper = new DbHelper(this);

        initCreateAccTextView();
        initialzeViews();

        //set click event of login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check user input
                if(isUserInputValid()) {

                    String an_email = editText_email.getText().toString();
                    String a_password = editText_password.getText().toString();

                    User current_user = db_Helper.authenticateUser((new User(null, null, an_email, a_password)));

                    if(current_user != null) {
                        Snackbar.make(login_button, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                    }
                    else {
                        Snackbar.make(login_button, "Login failed, please try again.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    //set Create account TextView text and click event( maltipal colors
    private void initCreateAccTextView() {
        TextView tv_create_acc = (TextView) findViewById(R.id.tv_CreateAccount);
        tv_create_acc.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        tv_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // connect XML views to its Objects
    private void initialzeViews() {
        editText_email = (EditText) findViewById(R.id.editText_Email);
        editText_password = (EditText) findViewById(R.id.editText_Password);
        textInputLayout_email = (TextInputLayout) findViewById(R.id.textInputLayout_Email);
        textInputLayout_password = (TextInputLayout) findViewById(R.id.textInputLayout_Password);
        login_button = (Button) findViewById(R.id.login_button);

    }

    //handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    // validate user password input
    public boolean validateUserPasswordInput() {
        boolean isValidPass = false;

        //Get values from EditText field
        String password = editText_password.getText().toString();

        if (password.isEmpty()) {
            textInputLayout_password.setError("Please enter valid password!");
        } else {
            if (password.length() > 5) {
                isValidPass = true;
                textInputLayout_password.setError(null);
            } else {
                textInputLayout_password.setError("Password is too short!");
            }
        }

        return isValidPass;
    }

    public boolean validateUserEmailInput() {
        boolean isValidEmail = false;

        //Get values from EditText field
        String email = editText_email.getText().toString();

        //Handling validation for email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout_email.setError("Please enter valid email!");
        } else {
            isValidEmail = true;
            textInputLayout_email.setError(null);
        }
        return isValidEmail;
    }
    public boolean isUserInputValid() {
        boolean isValid = false;

        if(validateUserEmailInput() && validateUserPasswordInput()) {
            isValid = true;
        }

        return isValid;
    }

}


