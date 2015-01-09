package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Project

class VcsCommandExecutor {

    private final Project project
    private final VcsType vcsType

    VcsCommandExecutor(Project project, VcsType vcsType) {
        assert project, 'project can not be null'
        assert vcsType, 'vcsType can not be null'
        this.project = project
        this.vcsType = vcsType
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

        try {
            output.toString().toInteger()
        } catch (def e) {
            throw new SemanticVersionGradleScriptException(e.message, e)
        }
    }
}
