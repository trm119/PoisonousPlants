package com.example.plants.poisonousplants;

import android.content.Context;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.plants.poisonousplants.View.activities.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void testValidateEmailInput_Format_True() {

        String example_email = "user1@email.com";
        boolean result  = loginActivityRule.getActivity().validateUserEmailInput(example_email);
        assertTrue(result);
    }

    @Test
    public void testValidatePasswordInput_Length_True() {

        String example_password = "password";
        boolean result = loginActivityRule.getActivity().validateUserPasswordInput(example_password);
        assertTrue(result);
    }

    @Test
    public void testValidateEmailInput_Format_False() {

        loginActivityRule.getActivity().runOnUiThread(new Runnable() {


            public void run() {
                // your code to update the UI
                String example_email = "ucom";
                boolean result  = loginActivityRule.getActivity().validateUserEmailInput(example_email);
                assertFalse(result);
            }
        });

    }

    @Test
    public void testValidatePasswordInput_Length_False() {

        loginActivityRule.getActivity().runOnUiThread(new Runnable() {


            public void run() {
                String example_password = "paw";
                boolean result = loginActivityRule.getActivity().validateUserPasswordInput(example_password);
                assertFalse(result);
            }
        });

    }

    @Test
    public void testValidateUserInput_True() {

        String ex_user_email = "user10@email.com";
        String ex_password = "password";
        boolean result = loginActivityRule.getActivity().isUserInputValid(ex_user_email, ex_password);
        assertTrue(result);
    }


    @Test
    public void testValidateUserInput_False() {

        loginActivityRule.getActivity().runOnUiThread(new Runnable() {

            public void run() {
                // your code to update the UI
                String ex_user_name = "use";
                String ex_password = "pass";

                boolean result = loginActivityRule.getActivity().isUserInputValid(ex_user_name, ex_password);
                assertFalse(result);

            }
        });
    }


}

