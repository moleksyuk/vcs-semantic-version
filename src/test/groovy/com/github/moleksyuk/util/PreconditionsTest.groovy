package com.github.moleksyuk.util

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

class PreconditionsTest {

    @Test(expected = SemanticVersionGradleScriptException)
    void testCheckNotNullIfNull() {
        // GIVEN
        def name = 'name'
        def value = null

        // WHEN
        try {
            Preconditions.checkNotNull(name, value)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'name' property is required."))
            throw e
        }
    }

    @Test
    void testCheckNotNullForString() {
        // GIVEN
        def name = 'name'
        def value = 'value'

        // WHEN
        Preconditions.checkNotNull(name, value)

        // THEN
    }

    @Test
    void testCheckNotNullForEmptyString() {
        // GIVEN
        def name = 'name'
        def value = ''

        // WHEN
        Preconditions.checkNotNull(name, value)

        // THEN
    }

    @Test
    void testCheckNotNullForZero() {
        // GIVEN
        def name = 'name'
        def value = 0

        // WHEN
        Preconditions.checkNotNull(name, value)

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException)
    void testCheckNotNegativeIfNegative() {
        // GIVEN
        def name = 'name'
        def value = -1

        // WHEN
        try {
            Preconditions.checkNotNegative(name, value)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'name' property can not be negative."))
            throw e
        }
    }

    @Test
    void testCheckNotNegative() {
        // GIVEN
        def name = 'name'
        def value = 0

        // WHEN
        Preconditions.checkNotNegative(name, value)

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException)
    void testCheckNotBlankIfBlank() {
        // GIVEN
        def name = 'name'
        def value = ''

        // WHEN
        try {
            Preconditions.checkNotBlank(name, value)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'name' property can not be blank."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    void testCheckNotBlankIfWhitespaces() {
        // GIVEN
        def name = 'name'
        def value = '    '

        // WHEN
        try {
            Preconditions.checkNotBlank(name, value)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'name' property can not be blank."))
            throw e
        }
    }

    @Test
    void testCheckNotBlank() {
        // GIVEN
        def name = 'name'
        def value = 'value'

        // WHEN
        Preconditions.checkNotBlank(name, value)

        // THEN
    }
}
