package com.example.plants.poisonousplants;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.plants.poisonousplants.View.activities.LoginActivity;
import com.example.plants.poisonousplants.View.activities.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import androidx.test.core.app.ApplicationProvider;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    //private Context context = LoginActivity.getApplicationContext();

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void testValidateUserEmailInput() {

        //LoginActivity log_act = new LoginActivity(mMockContext);
        String example_email = "user1@email.com";
        boolean result  = registerActivityRule.getActivity().validateEmailInput(example_email);
        assertTrue(result);
    }

    @Test
    public void testValidateUserPasswordInput() {

        String example_password = "userpassword";
        boolean result = registerActivityRule.getActivity().validatePasswordInput(example_password);
        assertTrue(result);
    }

    @Test
    public void testValidateUserNameInput() {

        String example_password = "username";
        boolean result = registerActivityRule.getActivity().validatePasswordInput(example_password);
        assertTrue(result);
    }


}
