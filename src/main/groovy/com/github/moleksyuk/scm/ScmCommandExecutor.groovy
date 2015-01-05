package com.github.moleksyuk.scm

class ScmCommandExecutor {
    private final String command

    ScmCommandExecutor(String command) {
        this.command = command
    }

    int execute() {
        command.execute().toInteger()
    }
}
