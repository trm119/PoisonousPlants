package com.example.plants.poisonousplants;

import android.content.Context;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.plants.poisonousplants.View.activities.LoginActivity;
import com.example.plants.poisonousplants.View.activities.RegisterActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {


    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void testValidateEmailInput_Format_True() {

        String example_email = "user1@email.com";
        boolean result  = registerActivityRule.getActivity().validateEmailInput(example_email);
        assertTrue(result);
    }

    @Test
    public void testValidatePasswordInput_Length_True() {

        String example_password = "password";
        boolean result = registerActivityRule.getActivity().validatePasswordInput(example_password);
        assertTrue(result);
    }

    @Test
    public void testValidateUserNameInput_Length_True() {

        String example_password = "user1";
        boolean result = registerActivityRule.getActivity().validateUserNameInput(example_password);
        assertTrue(result);
    }

    @Test
    public void testValidateEmailInput_Format_False() {

        registerActivityRule.getActivity().runOnUiThread(new Runnable() {

            public void run() {

                String example_email = "email";
                boolean result  = registerActivityRule.getActivity().validateEmailInput(example_email);
                assertFalse(result);
            }
        });
    }


    @Test
    public void testValidatePasswordInput_Length_False() {

        registerActivityRule.getActivity().runOnUiThread(new Runnable() {

            public void run() {

                String example_password = "pas";
                boolean result = registerActivityRule.getActivity().validatePasswordInput(example_password);
                assertFalse(result);
            }
        });
    }

    @Test
    public void testValidateUserNameInput_Length_False() {

        registerActivityRule.getActivity().runOnUiThread(new Runnable() {

            public void run() {

                String example_username = "use";
                boolean result = registerActivityRule.getActivity().validateUserNameInput(example_username);
                assertFalse(result);
            }
        });
    }

    @Test
    public void testValidateUserInput_True() {

        String ex_user_name = "username";
        String ex_email = "user2@email.com";
        String ex_password = "password2";

        boolean result = registerActivityRule.getActivity().isUserInputValid(ex_user_name, ex_email, ex_password);
        assertTrue(result);

    }

    @Test
    public void testValidateUserInput_False() {

        registerActivityRule.getActivity().runOnUiThread(new Runnable() {

            public void run() {

                String ex_user_name = "use";
                String ex_email = "email";
                String ex_password = "pass";

                boolean result = registerActivityRule.getActivity().isUserInputValid(ex_user_name, ex_email, ex_password);
                assertFalse(result);

            }
        });
    }

}

