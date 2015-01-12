package com.github.moleksyuk.vcs

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.vcs.postprocessor.AccurevPostProcessor
import com.github.moleksyuk.vcs.postprocessor.CommonPostProcessor
import com.github.moleksyuk.vcs.type.Accurev
import com.github.moleksyuk.vcs.type.Git
import com.github.moleksyuk.vcs.type.Mercurial
import com.github.moleksyuk.vcs.type.Svn
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsCommandPostProcessorFactoryTest extends AbstractIntegrationTest {

    @Test
    public void testCreateAccurevPostProcessor() {
        // GIVEN
        def vcsType = new Accurev()

        // WHEN
        def processor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)

        // THEN
        assertThat(processor, Matchers.instanceOf(AccurevPostProcessor))
    }

    @Test
    public void testCreateGitPostProcessor() {
        // GIVEN
        def vcsType = new Git()

        // WHEN
        def processor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)

        // THEN
        assertThat(processor, Matchers.instanceOf(CommonPostProcessor))
    }

    @Test
    public void testCreateMercurialPostProcessor() {
        // GIVEN
        def vcsType = new Mercurial()

        // WHEN
        def processor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)

        // THEN
        assertThat(processor, Matchers.instanceOf(CommonPostProcessor))
    }

    @Test
    public void testCreateSvnPostProcessor() {
        // GIVEN
        def vcsType = new Svn()

        // WHEN
        def processor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)

        // THEN
        assertThat(processor, Matchers.instanceOf(CommonPostProcessor))
    }

    @Test
    public void testCreateCommonPostProcessor() {
        // GIVEN
        def vcsType = new VcsType() {
            @Override
            String getCommand() {
                return null
            }

            @Override
            List<String> getCommandArguments() {
                return null
            }
        }

        // WHEN
        def processor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)

        // THEN
        assertThat(processor, Matchers.instanceOf(CommonPostProcessor))
    }
}