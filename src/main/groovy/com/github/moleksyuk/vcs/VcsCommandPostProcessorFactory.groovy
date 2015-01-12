package com.github.moleksyuk.vcs

import com.github.moleksyuk.vcs.postprocessor.AccurevPostProcessor
import com.github.moleksyuk.vcs.postprocessor.CommonPostProcessor
import com.github.moleksyuk.vcs.type.Accurev

class VcsCommandPostProcessorFactory {
    private VcsCommandPostProcessorFactory() {}

    static VcsCommandPostProcessor createVcsCommandPostProcessor(VcsType vcsType) {
        if (vcsType instanceof Accurev) {
            new AccurevPostProcessor()
        } else {
            new CommonPostProcessor()
        }
    }
}
