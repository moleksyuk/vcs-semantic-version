package com.github.moleksyuk.gradle.vcs.type

import com.github.moleksyuk.gradle.vcs.VcsType

class Git implements VcsType {

    @Override
    String getCommand() {
        'git'
    }

    @Override
    List<String> getCommandArguments() {
        ['rev-list', 'HEAD', '--count']
    }
}
