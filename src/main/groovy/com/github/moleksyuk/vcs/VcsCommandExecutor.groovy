package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Project

class VcsCommandExecutor {

    private final Project project
    private final VcsType vcsType
    private final VcsCommandPostProcessor postProcessor

    VcsCommandExecutor(Project project, VcsType vcsType, VcsCommandPostProcessor postProcessor) {
        assert project, 'project can not be null'
        assert vcsType, 'vcsType can not be null'
        assert postProcessor, 'postProcessor can not be null'
        this.project = project
        this.vcsType = vcsType
        this.postProcessor = postProcessor
    }

    Integer execute() {
        def output = new ByteArrayOutputStream()
        def errors = new ByteArrayOutputStream()
        def execResult = project.exec({
            commandLine vcsType.command
            args vcsType.commandArguments
            standardOutput output
            errorOutput errors
            ignoreExitValue true
        })

        if (execResult.exitValue) {
            throw new SemanticVersionGradleScriptException("Command '${vcsType.command}' finished with non-zero exit value '${execResult.exitValue}'. Error output: ${errors.toString()}")
        }

        def commandOutput = output.toString().trim()
        project.logger.info("Command output: ${commandOutput}")
        postProcessor.postProcess(commandOutput)
    }
}
