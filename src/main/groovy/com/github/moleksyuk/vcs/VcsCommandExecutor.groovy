package com.github.moleksyuk.vcs

class VcsCommandExecutor {
    private final String command

    VcsCommandExecutor(String command) {
        this.command = command
    }

    int execute() {
        command.execute().toInteger()
    }
}
