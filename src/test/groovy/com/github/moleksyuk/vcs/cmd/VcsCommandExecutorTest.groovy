package com.github.moleksyuk.vcs.cmd

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.Vcs
import com.github.moleksyuk.vcs.VcsType
import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.CommonOutputParser
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsCommandExecutorTest {

    @Test(expected = IllegalArgumentException)
    public void testInitIfProjectIsNull() throws Exception {
        // GIVEN
        def project = null
        def vcs = new DummyVcs(null, null, null)

        // WHEN
        new VcsCommandExecutor(project, vcs);

        // THEN
    }

    @Test(expected = IllegalArgumentException)
    public void testInitIfVcsIsNull() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def vcs = null

        // WHEN
        new VcsCommandExecutor(project, vcs);

        // THEN
    }

    @Test
    public void testExecuteSuccessProcess() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcs = new DummyVcs(Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo',
                Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', '100'] : ['100'],
                new CommonOutputParser()
        )
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcs);

        // WHEN
        Integer actual = vcsCommandExecutor.execute();

        // THEN
        assertThat(actual, Matchers.equalTo(100));
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteFailedProcess() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcs = new DummyVcs('ping', [], null)
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcs);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteWithNonParsedInteger() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcs = new DummyVcs(Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo',
                Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', 'a'] : ['a'],
                new CommonOutputParser()
        )
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcs);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    static class DummyVcs implements Vcs {

        private final String command
        private final List<String> commandArguments
        private final VcsCommandOutputParser parser

        DummyVcs(String command, List<String> commandArguments, VcsCommandOutputParser parser) {
            this.command = command
            this.commandArguments = commandArguments
            this.parser = parser
        }

        @Override
        VcsType getType() {
            return null
        }

        @Override
        String getCommand() {
            return command
        }

        @Override
        List<String> getCommandArguments() {
            return commandArguments
        }

        @Override
        VcsCommandOutputParser getCommandOutputParser() {
            return parser
        }
    }
}