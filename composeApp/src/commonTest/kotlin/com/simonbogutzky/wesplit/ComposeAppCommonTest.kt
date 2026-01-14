package com.simonbogutzky.wesplit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ComposeAppCommonTest {

    /**
     * Test basic tip calculation without tip
     */
    @Test
    fun calculateTotalPerPerson_noTip_correctAmount() {
        val checkAmount = 100.0
        val numberOfPeople = 2
        val tipPercentage = 0

        val tipValue = checkAmount / 100 * tipPercentage
        val grandTotal = checkAmount + tipValue
        val totalPerPerson = grandTotal / numberOfPeople

        assertEquals(50.0, totalPerPerson, 0.01)
    }

    /**
     * Test tip calculation with 20% tip
     */
    @Test
    fun calculateTotalPerPerson_with20PercentTip_correctAmount() {
        val checkAmount = 100.0
        val numberOfPeople = 4
        val tipPercentage = 20

        val tipValue = checkAmount / 100 * tipPercentage
        val grandTotal = checkAmount + tipValue
        val totalPerPerson = grandTotal / numberOfPeople

        assertEquals(30.0, totalPerPerson, 0.01)
    }

    /**
     * Test tip calculation with odd amounts
     */
    @Test
    fun calculateTotalPerPerson_oddAmount_correctCalculation() {
        val checkAmount = 47.89
        val numberOfPeople = 3
        val tipPercentage = 15

        val tipValue = checkAmount / 100 * tipPercentage
        val grandTotal = checkAmount + tipValue
        val totalPerPerson = grandTotal / numberOfPeople

        // 47.89 * 1.15 = 55.0735, 55.0735 / 3 = 18.3578...
        assertEquals(18.36, totalPerPerson, 0.01)
    }

    /**
     * Test calculation with single person
     */
    @Test
    fun calculateTotalPerPerson_onePerson_paysFullAmount() {
        val checkAmount = 75.50
        val numberOfPeople = 1
        val tipPercentage = 25

        val tipValue = checkAmount / 100 * tipPercentage
        val grandTotal = checkAmount + tipValue
        val totalPerPerson = grandTotal / numberOfPeople

        assertEquals(94.375, totalPerPerson, 0.01)
    }

    /**
     * Test calculation with zero check amount
     */
    @Test
    fun calculateTotalPerPerson_zeroAmount_returnsZero() {
        val checkAmount = 0.0
        val numberOfPeople = 2
        val tipPercentage = 20

        val tipValue = checkAmount / 100 * tipPercentage
        val grandTotal = checkAmount + tipValue
        val totalPerPerson = grandTotal / numberOfPeople

        assertEquals(0.0, totalPerPerson, 0.01)
    }

    /**
     * Test constants are properly defined
     */
    @Test
    fun appConstants_tipPercentages_containsExpectedValues() {
        val expected = listOf(0, 10, 15, 20, 25)
        assertEquals(expected, AppConstants.TIP_PERCENTAGES)
    }

    @Test
    fun appConstants_peopleRange_startsAt2() {
        assertEquals(2, AppConstants.PEOPLE_RANGE.first)
    }

    @Test
    fun appConstants_peopleRange_endsAt99() {
        assertEquals(99, AppConstants.PEOPLE_RANGE.last)
    }

    @Test
    fun appConstants_defaults_areValid() {
        assertEquals(2, AppConstants.DEFAULT_NUMBER_OF_PEOPLE)
        assertEquals(20, AppConstants.DEFAULT_TIP_PERCENTAGE)
        assertTrue(AppConstants.TIP_PERCENTAGES.contains(AppConstants.DEFAULT_TIP_PERCENTAGE))
    }

    /**
     * Test input validation regex patterns
     */
    @Test
    fun inputValidation_validDecimalNumbers_match() {
        val validInputs = listOf("0", "1", "10", "100", "1.5", "10.99", "0.5", "123.45")
        val regex = Regex("^\\d+(\\.\\d{0,2})?$")

        validInputs.forEach { input ->
            assertTrue(input.matches(regex), "Input '$input' should be valid")
        }
    }

    @Test
    fun inputValidation_invalidFormats_doNotMatch() {
        val invalidInputs = listOf(".", ".5", "1.234", "abc", "1.2.3", "-5", "12.345")
        val regex = Regex("^\\d+(\\.\\d{0,2})?$")

        invalidInputs.forEach { input ->
            assertTrue(!input.matches(regex), "Input '$input' should be invalid")
        }
    }

    @Test
    fun inputValidation_intermediateDecimalState_matches() {
        // "1." is a valid intermediate state when typing "1.5"
        val intermediateInputs = listOf("1.", "10.", "100.")
        val regex = Regex("^\\d+(\\.\\d{0,2})?$")

        intermediateInputs.forEach { input ->
            assertTrue(input.matches(regex), "Input '$input' should be valid as intermediate state")
        }
    }

    /**
     * Test navigation routes are properly defined
     */
    @Test
    fun navigationRoutes_areNotEmpty() {
        assertTrue(NavigationRoutes.HOME.isNotEmpty())
        assertTrue(NavigationRoutes.SELECT_PEOPLE.isNotEmpty())
    }
}