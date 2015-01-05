package com.github.moleksyuk.scm

enum ScmType {

    ACCUREV(""),
    GIT("git rev-list HEAD --count")

    private final String command

    ScmType(String command) {
        this.command = command
    }

    String getCommand() {
        return command
    }
}