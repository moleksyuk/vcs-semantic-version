package com.github.moleksyuk.vcs

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.plugin.SemanticVersionPlugin
import com.github.moleksyuk.vcs.parser.AccurevOutputParser
import com.github.moleksyuk.vcs.parser.BasicOutputParser
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsFactoryTest extends AbstractIntegrationTest {

    @Test
    public void testCreateAccurevVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(ACCUREV_REPOSITORY_PATH)).build()
        project.apply plugin: SemanticVersionPlugin
        project.extensions.semanticVersion.accurev.stream = 'stream name'

        // WHEN
        def vcs = VcsFactory.createVcs(project)

        // THEN
        assertThat(vcs.type, Matchers.equalTo(VcsType.ACCUREV))
        assertThat(vcs.command, Matchers.equalTo('accurev'))
        assertThat(vcs.commandArguments, Matchers.equalTo(['hist', '-ft', '-t', 'highest', '-s', project.extensions.semanticVersion.accurev.stream]))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(AccurevOutputParser))
    }

    @Test
    public void testCreateGitVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()

        // WHEN
        def vcs = VcsFactory.createVcs(project)

        // THEN
        assertThat(vcs.type, Matchers.equalTo(VcsType.GIT))
        assertThat(vcs.command, Matchers.equalTo('git'))
        assertThat(vcs.commandArguments, Matchers.equalTo(['rev-list', 'HEAD', '--count']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(BasicOutputParser))
    }

    @Test
    public void testCreateMercurialVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(MERCURIAL_REPOSITORY_PATH)).build()

        // WHEN
        def vcs = VcsFactory.createVcs(project)

        // THEN
        assertThat(vcs.type, Matchers.equalTo(VcsType.MERCURIAL))
        assertThat(vcs.command, Matchers.equalTo('hg'))
        assertThat(vcs.commandArguments, Matchers.equalTo(['id', '--num', '--rev', 'tip']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(BasicOutputParser))
    }

    @Test
    public void testCreateSvnVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(SVN_REPOSITORY_PATH)).build()

        // WHEN
        def vcs = VcsFactory.createVcs(project)

        // THEN
        assertThat(vcs.type, Matchers.equalTo(VcsType.SVN))
        assertThat(vcs.command, Matchers.equalTo('svnversion'))
        assertThat(vcs.commandArguments, Matchers.equalTo(['.']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(BasicOutputParser))
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testCreateUnknownVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(UNKNOWN_REPOSITORY_PATH)).build()

        // WHEN
        VcsFactory.createVcs(project)

        // THEN
    }
}