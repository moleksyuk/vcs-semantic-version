package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.parser.BasicOutputParser
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsCommandExecutorTest {

    @Test(expected = AssertionError)
    public void testInitIfProjectIsNull() throws Exception {
        // GIVEN
        def project = null
        def vcs = new Vcs(null, null, null, null)

        // WHEN
        new VcsCommandExecutor(project, vcs);

        // THEN
    }

    @Test(expected = AssertionError)
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
        def vcs = new Vcs(null,
                Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo',
                Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', '100'] : ['100'],
                new BasicOutputParser()
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
        def vcs = new Vcs(null, 'ping', [], null)
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcs);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }

    @Test(expected = SemanticVersionGradleScriptException.class)
    public void testExecuteWithNonParsedInteger() throws Exception {
        // GIVEN
        def project = ProjectBuilder.builder().build();
        def vcs = new Vcs(null,
                Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : 'echo',
                Os.isFamily(Os.FAMILY_WINDOWS) ? ['/c', 'echo', 'a'] : ['a'],
                new BasicOutputParser()
        )
        VcsCommandExecutor vcsCommandExecutor = new VcsCommandExecutor(project, vcs);

        // WHEN
        vcsCommandExecutor.execute();

        // THEN
    }
}