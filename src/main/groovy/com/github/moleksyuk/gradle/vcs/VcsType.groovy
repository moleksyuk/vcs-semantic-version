package com.github.moleksyuk.gradle.vcs

interface VcsType {

    String getCommand()

    List<String> getCommandArguments()
}