package com.github.moleksyuk.vcs

import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat;

public class VcsTypeTest {

    @Test
    public void testGitCommandDefinition() {
        // GIVEN
        def expected = 'git rev-list HEAD --count'

        // WHEN
        def actual = VcsType.GIT.getCommand()

        // THEN
        assertThat(actual, equalTo(expected))
    }
}