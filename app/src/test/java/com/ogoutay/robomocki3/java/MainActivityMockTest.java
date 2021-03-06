package com.ogoutay.robomocki3.java;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ogoutay.robomocki3.BuildConfig;
import com.ogoutay.robomocki3.R;
import com.ogoutay.robomocki3.activities.MainActivity;
import com.ogoutay.robomocki3.managers.ExampleManager;

import org.joor.Reflect;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

/**
 * Demonstrates how to mock a typed method and return something else
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = BuildConfig.APPLICATION_ID)
public class MainActivityMockTest {

    private static final String MOCKED_VALUE = "toto";

    private Activity activity;

    @Mock
    private ExampleManager mockExampleManager;

    @Before
    public void setUp() {
        //Building the activity
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);

        //Mocking ExampleManager within the Activity
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockExampleManager.getServiceName()).thenReturn(MOCKED_VALUE);
        Reflect.on(activityController.get()).set("mExampleManager", mockExampleManager);

        //Launching the Activity
        activityController.setup();
        activity = activityController.get();
    }

    @Test
    public void testMainActivity() {
        //Verify this method has been called
        Mockito.verify(mockExampleManager).getServiceName();

        //Assert the TextView has the mocked value
        assertEquals(MOCKED_VALUE, ((TextView) activity.findViewById(R.id.textView)).getText());

        //Assert the TextView visibility is regular visible in this case
        assertEquals(View.VISIBLE, activity.findViewById(R.id.textView).getVisibility());
    }

}
