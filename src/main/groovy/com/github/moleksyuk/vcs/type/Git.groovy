package com.github.moleksyuk.vcs.type

import com.github.moleksyuk.vcs.VcsType

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
