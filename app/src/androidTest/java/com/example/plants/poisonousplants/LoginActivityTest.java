package com.example.plants.poisonousplants;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.plants.poisonousplants.View.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import androidx.test.core.app.ApplicationProvider;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    //private Context context = LoginActivity.getApplicationContext();

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testValidateUserEmailInput() {

        //LoginActivity log_act = new LoginActivity(mMockContext);
        String example_email = "user1@email.com";
        boolean result  = loginActivityRule.getActivity().validateUserEmailInput(example_email);
        assertTrue(result);
    }

    @Test
    public void testValidateUserPasswordInput() {

        String example_password = "password";
        boolean result = loginActivityRule.getActivity().validateUserPasswordInput(example_password);
        assertTrue(result);
    }


}
