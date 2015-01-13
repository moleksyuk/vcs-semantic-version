package com.github.moleksyuk.vcs.parser

interface VcsCommandOutputParser {
    Integer parse(String commandOutput);
}
