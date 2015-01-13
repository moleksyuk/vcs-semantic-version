package com.github.moleksyuk.vcs

import com.github.moleksyuk.vcs.parser.VcsCommandOutputParser

class Vcs {

    def VcsType type
    def String command
    def List<String> commandArguments
    def VcsCommandOutputParser commandOutputParser

    Vcs(VcsType type, String command, List<String> commandArguments, VcsCommandOutputParser commandOutputParser) {
        this.type = type
        this.command = command
        this.commandArguments = commandArguments
        this.commandOutputParser = commandOutputParser
    }

    VcsType getType() {
        return type
    }

    String getCommand() {
        return command
    }

    List<String> getCommandArguments() {
        return commandArguments
    }

    VcsCommandOutputParser getCommandOutputParser() {
        return commandOutputParser
    }
}
