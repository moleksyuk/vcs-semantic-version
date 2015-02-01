package com.github.moleksyuk.plugin

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.process.ExecResult
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mockito

import static org.junit.Assert.assertThat
import static org.junit.Assert.fail
import static org.mockito.Matchers.any
import static org.mockito.Matchers.notNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

public class SemanticVersionPluginTest extends AbstractIntegrationTest {

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfMajorPropertyIsNull() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()

        def project = mock(Project.class)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'major' property is required."));
            throw e;
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfMajorPropertyIsNegative() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
        extension.major = -1

        def project = mock(Project.class)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'major' property can not be negative."));
            throw e;
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfMinorPropertyIsNull() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
        extension.major = 1

        def project = mock(Project.class)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'minor' property is required."));
            throw e;
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfMinorPropertyIsNegative() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
        extension.major = 1
        extension.minor = -1

        def project = mock(Project.class)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'minor' property can not be negative."));
            throw e;
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfAccurevStreamPropertyIsNull() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
        extension.major = 1
        extension.minor = 2

        def project = mock(Project)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)
        def logger = mock(Logger)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)
        when(project.getProjectDir()).thenReturn(new File(ACCUREV_REPOSITORY_PATH))
        when(project.getLogger()).thenReturn(logger)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'accurev.stream' property is required."));
            throw e;
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testIfAccurevStreamPropertyIsEmpty() {
        // GIVEN
        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
        extension.major = 1
        extension.minor = 2
        extension.accurev({ stream = '   ' })

        def project = mock(Project)
        def extensionContainer = Mockito.mock(ExtensionContainer.class)
        def logger = mock(Logger)

        when(project.getExtensions()).thenReturn(extensionContainer);
        when(extensionContainer.create(any(), any())).thenReturn(extension)
        when(project.getProjectDir()).thenReturn(new File(ACCUREV_REPOSITORY_PATH))
        when(project.getLogger()).thenReturn(logger)

        SemanticVersionPlugin plugin = new SemanticVersionPlugin()

        // WHEN
        try {
            plugin.apply(project)
            fail()
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'accurev.stream' property can not be empty."));
            throw e;
        }
    }

//    @Test
//    public void testSetProject() {
//        // GIVEN
//        SemanticVersionPluginExtension extension = new SemanticVersionPluginExtension()
//        extension.major = 1
//        extension.minor = 2
//
//        def project = mock(Project)
//        def execResult = mock(ExecResult)
//
//        Closure closure = {}
//        when(project.exec(any(Closure))).thenReturn(execResult)
////        when(project.exec(any(Action))).thenReturn(execResult)
//        when(execResult.getExitValue()).thenReturn(0)
//
//        // WHEN
//        def actual = project.exec(closure)
////        plugin.apply(project)
//
//        // THEN
//        assertThat(actual, notNull())
//    }
//
//    class StringAction implements Action<String> {
//
//        @Override
//        void execute(String s) {
//
//        }
//    }
}