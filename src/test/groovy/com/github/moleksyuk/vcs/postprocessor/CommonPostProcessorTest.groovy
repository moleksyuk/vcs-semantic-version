package com.github.moleksyuk.vcs.postprocessor

import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class CommonPostProcessorTest {

    @Test
    public void testPostProcessWithSuccess() throws Exception {
        // GIVEN
        Integer expected = 1
        CommonPostProcessor postProcessor = new CommonPostProcessor()

        // WHEN
        def actual = postProcessor.postProcess(expected.toString())

        // THEN
        assertThat(actual, Matchers.equalTo(expected))
    }
}