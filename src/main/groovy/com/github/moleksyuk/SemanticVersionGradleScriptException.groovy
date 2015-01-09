package com.github.moleksyuk

import org.gradle.api.GradleScriptException

class SemanticVersionGradleScriptException extends GradleScriptException {

    SemanticVersionGradleScriptException(String message) {
        super(message, null)
    }

    SemanticVersionGradleScriptException(String message, Throwable cause) {
        super(message, cause)
    }
}
