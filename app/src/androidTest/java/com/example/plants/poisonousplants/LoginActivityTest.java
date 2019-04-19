package com.example.plants.poisonousplants;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.plants.poisonousplants.View.activities.LoginActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testValidateEmailInput_Format_True() {

        //LoginActivity log_act = new LoginActivity(mMockContext);
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

        //LoginActivity log_act = new LoginActivity(mMockContext);
        String example_email = "u.com";
        boolean result  = loginActivityRule.getActivity().validateUserEmailInput(example_email);
        assertTrue(result);
    }

    @Test
    public void testValidatePasswordInput_Length_False() {

        String example_password = "paw";
        boolean result = loginActivityRule.getActivity().validateUserPasswordInput(example_password);
        assertTrue(result);
    }

}

