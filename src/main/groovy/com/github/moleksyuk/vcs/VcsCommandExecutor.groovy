package com.github.moleksyuk.vcs

import org.gradle.api.GradleScriptException

class VcsCommandExecutor {
    private final String command

    VcsCommandExecutor(String command) {
        assert command?.trim(), 'command can not be null or empty'
        this.command = command
    }

    int execute() {
        def output = new StringBuffer()
        def errors = new StringBuffer()

        def process = command.execute()
        process.consumeProcessOutput(output, errors)
        process.waitFor()

        if (process.exitValue()) {
            throw new GradleScriptException(errors.toString(), null)
        }

        try {
            output.toInteger()
        } catch (def e) {
            throw new GradleScriptException(e.message, e)
        }
    }
}
