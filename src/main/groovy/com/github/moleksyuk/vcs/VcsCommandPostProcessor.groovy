package com.github.moleksyuk.vcs

interface VcsCommandPostProcessor {
    Integer postProcess(String commandOutput);
}
