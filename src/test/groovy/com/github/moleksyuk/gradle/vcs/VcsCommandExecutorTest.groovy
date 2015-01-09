package com.github.moleksyuk.gradle.vcs

import com.github.moleksyuk.gradle.SemanticVersionGradleScriptException
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsCommandExecutorTest {

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullProject() throws Exception {
        // GIVEN
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(null, new DummySuccessVcsType());

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullVcsType() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, null);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test
    public void testExecuteSuccessProcess() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcsType = new DummySuccessVcsType()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType);

        // WHEN
        Integer actual = vcsCommandExecutor.execute();

        // THEN
        assertThat(actual, Matchers.equalTo(100));
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteFailedProcess() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcsType = new DummyFailVcsType()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteWithNonParsedInteger() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcsType = new DummyNonParsableOutputVcsType()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    class DummySuccessVcsType implements VcsType {

        @Override
        String getCommand() {
            return 'echo'
        }

        @Override
        List<String> getCommandArguments() {
            ['100']
        }
    }

    class DummyNonParsableOutputVcsType implements VcsType {

        @Override
        String getCommand() {
            return 'echo'
        }

        @Override
        List<String> getCommandArguments() {
            ['a']
        }
    }

    class DummyFailVcsType implements VcsType {

        @Override
        String getCommand() {
            return 'ping'
        }

        @Override
        List<String> getCommandArguments() {
            []
        }
    }

}