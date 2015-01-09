package com.github.moleksyuk.gradle.vcs.type

import com.github.moleksyuk.gradle.vcs.VcsType

class Mercurial implements VcsType {

    @Override
    String getCommand() {
        'hg'
    }

    @Override
    List<String> getCommandArguments() {
        ['id', '--num', '--rev', 'tip']
    }
}
