package com.github.moleksyuk.vcs;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class VcsCommandExecutorTest {

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullCommand() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(null);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = AssertionError.class)
    public void testExecuteWithEmptyCommand() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor("");

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = AssertionError.class)
    public void testExecuteWithOnlyWhiteSpacesCommand() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor("   ");

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test
    public void testExecuteWithSuccess() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor("echo 1");

        // WHEN
        int actual = vcsCommandExecutor.execute();

        // THEN
        assertThat(actual, Matchers.equalTo(1));
    }

    @Test(expected = NumberFormatException.class)
    public void testExecuteWithFail() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor("echo a");

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }
}