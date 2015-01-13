package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Project

class VcsCommandExecutor {

    private final Project project
    private final Vcs vcs

    VcsCommandExecutor(Project project, Vcs vcs) {
        this.project = project
        this.vcs = vcs
    }

    Integer execute() {
        def output = new ByteArrayOutputStream()
        def errors = new ByteArrayOutputStream()
        def execResult = project.exec({
            commandLine vcs.command
            args vcs.commandArguments
            standardOutput output
            errorOutput errors
            ignoreExitValue true
        })

        if (execResult.exitValue) {
            throw new SemanticVersionGradleScriptException("Command '${vcs.command}' finished with non-zero exit value '${execResult.exitValue}'. Error output: ${errors.toString()}")
        }

        def commandOutput = output.toString().trim()
        project.logger.info("Command output: ${commandOutput}")
        vcs.commandOutputParser.parse(commandOutput)
    }
}
