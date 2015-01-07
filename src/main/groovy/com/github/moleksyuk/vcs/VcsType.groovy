package com.github.moleksyuk.vcs

enum VcsType {

    GIT("git rev-list HEAD --count")

    private final String command

    VcsType(String command) {
        this.command = command
    }

    String getCommand() {
        return command
    }
}