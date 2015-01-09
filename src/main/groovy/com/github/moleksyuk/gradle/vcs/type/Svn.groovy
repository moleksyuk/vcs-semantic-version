package com.github.moleksyuk.gradle.vcs.type

import com.github.moleksyuk.gradle.vcs.VcsType

class Svn implements VcsType {

    @Override
    String getCommand() {
        'svnversion'
    }

    @Override
    List<String> getCommandArguments() {
        ['.']
    }
}
