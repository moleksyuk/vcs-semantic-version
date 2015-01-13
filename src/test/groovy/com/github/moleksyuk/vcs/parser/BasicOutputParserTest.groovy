package com.github.moleksyuk.vcs.parser

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class BasicOutputParserTest {

    @Test
    public void testParseValidString() throws Exception {
        // GIVEN
        def expected = '1'
        BasicOutputParser parser = new BasicOutputParser()

        // WHEN
        def actual = parser.parse(expected.toString())

        // THEN
        assertThat(actual, Matchers.equalTo(expected.toInteger()))
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testParseInvalidString() throws Exception {
        // GIVEN
        def output = 'transaction INVALID_TRANSACTION_ID; chstream; 2015/01/12 13:48:11 ; user: devavratt.bagayat'
        def parser = new BasicOutputParser()

        // WHEN
        def actual = parser.parse(output)

        // THEN
    }
}