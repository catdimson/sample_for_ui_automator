package com.geekbrains.tests.automator

//import TEST_TIME_LOADING_SCREEN
//import TEST_TIMEOUT
//import TEST_NUMBER_FAKE
//import TEST_NUMBER_REAL
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.geekbrains.tests.*
import com.geekbrains.tests.TEST_NUMBER_FAKE
import com.geekbrains.tests.TEST_NUMBER_REAL
import com.geekbrains.tests.TEST_TIMEOUT
import com.geekbrains.tests.view.search.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class DetailsActivityTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TEST_TIMEOUT)

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()

        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TEST_TIMEOUT
        )

        val toDetails: UiObject2 = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        toDetails.click()
    }

    // Проверяем, что TextView с результатом присутствует на экране
    @Test
    fun test_ActivityDetail_ResultIsVisible() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TEST_TIME_LOADING_SCREEN
        )
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_FAKE"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_REAL"))
        }
        Assert.assertTrue(resultText.exists())
    }

    // Проверяем, что кнопка декремента меняет значение
    @Test
    fun test_ActivityDetail_WorkDecrementButton() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "decrementButton")),
            TEST_TIME_LOADING_SCREEN
        )
        val decrementButton: UiObject2 = uiDevice.findObject(
            By.res(packageName, "decrementButton")
        )
        decrementButton.click()
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_FAKE"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_REAL"))
        }
        Assert.assertFalse(resultText.exists())
    }

    // Проверяем, что кнопка инкремента меняет значение
    @Test
    fun test_ActivityDetail_WorkIncrementButton() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "incrementButton")),
            TEST_TIME_LOADING_SCREEN
        )
        val incrementButton: UiObject2 = uiDevice.findObject(
            By.res(packageName, "incrementButton")
        )
        incrementButton.click()
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_FAKE"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_REAL"))
        }
        Assert.assertFalse(resultText.exists())
    }// Проверяем, что кнопка декремента меняет значение

    @Test
    fun test_ActivityDetail_CorrectWorkDecrementButton() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "decrementButton")),
            TEST_TIME_LOADING_SCREEN
        )
        val decrementButton: UiObject2 = uiDevice.findObject(
            By.res(packageName, "decrementButton")
        )
        decrementButton.click()
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: ${TEST_NUMBER_FAKE - 1}"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: ${TEST_NUMBER_REAL - 1}"))
        }
        Assert.assertTrue(resultText.exists())
    }

    // Проверяем, что кнопка инкремента меняет значение
    @Test
    fun test_ActivityDetail_CorrectWorkIncrementButton() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "incrementButton")),
            TEST_TIME_LOADING_SCREEN
        )
        val incrementButton: UiObject2 = uiDevice.findObject(
            By.res(packageName, "incrementButton")
        )
        incrementButton.click()
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: ${TEST_NUMBER_FAKE + 1}"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: ${TEST_NUMBER_REAL + 1}"))
        }
        Assert.assertTrue(resultText.exists())
    }

    // Проверяем, что TextView с результатом присутствует на экране
    @Test
    fun test_ActivityDetailResultIsVisible() {
        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TEST_TIME_LOADING_SCREEN
        )
        val resultText: UiObject = if (isFakeData()) {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_FAKE"))
        } else {
            uiDevice.findObject(UiSelector().textMatches("Number of results: $TEST_NUMBER_REAL"))
        }
        Assert.assertTrue(resultText.exists())
    }

    private fun isFakeData(): Boolean = BuildConfig.TYPE == MainActivity.FAKE
}