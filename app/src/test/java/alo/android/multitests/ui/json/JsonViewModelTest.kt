package alo.android.multitests.ui.json

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class JsonViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    
    // Subject under test
    private lateinit var jsonViewModel: JsonViewModel
    
    @Before
    fun setupViewModel() {
        jsonViewModel = JsonViewModel(ApplicationProvider.getApplicationContext())
    }
}
