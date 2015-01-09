package com.github.moleksyuk.vcs

interface VcsType {

    String getCommand()

    List<String> getCommandArguments()
}