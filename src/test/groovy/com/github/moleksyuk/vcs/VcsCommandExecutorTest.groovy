package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.postprocessor.CommonPostProcessor
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsCommandExecutorTest {

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullProject() throws Exception {
        // GIVEN
        def vcsType = new DummySuccessVcsType()
        def postProcessor = new CommonPostProcessor()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(null, vcsType, postProcessor);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullVcsType() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def postProcessor = new CommonPostProcessor()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, null, postProcessor);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = AssertionError.class)
    public void testExecuteWithNullPostProcessor() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def vcsType = new DummySuccessVcsType()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType, null);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test
    public void testExecuteSuccessProcess() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcsType = new DummySuccessVcsType()
        def postProcessor = new CommonPostProcessor()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType, postProcessor);

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
        def postProcessor = new CommonPostProcessor()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType, postProcessor);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteWithNonParsedInteger() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcsType = new DummyNonParsableOutputVcsType()
        def postProcessor = new CommonPostProcessor()
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcsType, postProcessor);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    class DummySuccessVcsType implements VcsType {

        @Override
        String getCommand() {
            Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo'
        }

        @Override
        List<String> getCommandArguments() {
            Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', '100'] : ['100']
        }
    }

    class DummyNonParsableOutputVcsType implements VcsType {

        @Override
        String getCommand() {
            Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo'
        }

        @Override
        List<String> getCommandArguments() {
            Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', 'a'] : ['a']
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