package com.github.moleksyuk.vcs

class VcsCommandExecutor {
    private final String command

    VcsCommandExecutor(String command) {
        assert command?.trim(), 'command argument can not be null or empty'
        this.command = command
    }

    int execute() {
        command.execute().text.toInteger()
    }
}
