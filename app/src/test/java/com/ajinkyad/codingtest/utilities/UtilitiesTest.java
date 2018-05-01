package com.ajinkyad.codingtest.utilities;

import android.content.Context;
import android.provider.Settings;

import com.ajinkyad.codingtest.modules.common.CommonView;

import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by Ajinkya D on 29-04-2018.
 */

public class UtilitiesTest {

    @Test
    public void getFormattedName_BothNamesAreNull() {

        assertEquals("Name not available", Utilities.getFormattedName(null, null));

    }

    @Test
    public void getFormattedName_FirstNameIsAvailable() {

        assertEquals("John", Utilities.getFormattedName("John", null));

    }

    @Test
    public void getFormattedName_LastNameIsAvailable() {

        assertEquals("Smith", Utilities.getFormattedName(null, "Smith"));

    }

    @Test
    public void getFormattedName_BothNamesAreAvailable() {

        assertEquals("Smith, John", Utilities.getFormattedName("John", "Smith"));

    }


}
