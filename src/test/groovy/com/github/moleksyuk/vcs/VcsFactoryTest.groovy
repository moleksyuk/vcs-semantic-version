package com.github.moleksyuk.vcs

import com.github.moleksyuk.plugin.SemanticVersionPluginExtension
import com.github.moleksyuk.vcs.cmd.parser.impl.AccurevOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.CommonOutputParser
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat
import static org.mockito.Mockito.mock

public class VcsFactoryTest {

    @Test(expected = IllegalArgumentException)
    public void testCreateIfProjectIsNull() {
        // GIVEN
        def project = null
        def vcsType = VcsType.ACCUREV;

        // WHEN
        VcsFactory.createVcs(project, vcsType)

        // THEN
    }

    @Test(expected = IllegalArgumentException)
    public void testCreateIfVcsTypeIsNull() {
        // GIVEN
        def project = mock(Project)
        def vcsType = null;

        // WHEN
        VcsFactory.createVcs(project, vcsType)

        // THEN
    }

    @Test
    public void testCreateAccurev() {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.accurev.stream = 'stream'
        def vcsType = VcsType.ACCUREV

        // WHEN
        def vcs = VcsFactory.createVcs(project, vcsType)

        // THEN
        assertThat(vcs.type, equalTo(VcsType.ACCUREV))
        assertThat(vcs.command, equalTo('accurev'))
        assertThat(vcs.commandArguments, equalTo(['hist', '-ft', '-t', 'highest', '-s', 'stream']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(AccurevOutputParser))
    }

    @Test
    public void testCreateGitVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def vcsType = VcsType.GIT

        // WHEN
        def vcs = VcsFactory.createVcs(project, vcsType)

        // THEN
        assertThat(vcs.type, equalTo(VcsType.GIT))
        assertThat(vcs.command, equalTo('git'))
        assertThat(vcs.commandArguments, equalTo(['rev-list', 'HEAD', '--count']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(CommonOutputParser))
    }

    @Test
    public void testCreateMercurialVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def vcsType = VcsType.MERCURIAL

        // WHEN
        def vcs = VcsFactory.createVcs(project, vcsType)

        // THEN
        assertThat(vcs.type, equalTo(VcsType.MERCURIAL))
        assertThat(vcs.command, equalTo('hg'))
        assertThat(vcs.commandArguments, equalTo(['id', '--num', '--rev', 'tip']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(CommonOutputParser))
    }

    @Test
    public void testCreateSvnVcs() {
        // GIVEN
        def project = ProjectBuilder.builder().build()
        def vcsType = VcsType.SVN

        // WHEN
        def vcs = VcsFactory.createVcs(project, vcsType)

        // THEN
        assertThat(vcs.type, equalTo(VcsType.SVN))
        assertThat(vcs.command, equalTo('svnversion'))
        assertThat(vcs.commandArguments, equalTo(['.']))
        assertThat(vcs.commandOutputParser, Matchers.instanceOf(CommonOutputParser))
    }
}