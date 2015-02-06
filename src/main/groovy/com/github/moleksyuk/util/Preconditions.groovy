package com.github.moleksyuk.util

import com.github.moleksyuk.SemanticVersionGradleScriptException

public final class Preconditions {

    public static void checkNotNull(def name, def value) {
        if (value == null) throw new SemanticVersionGradleScriptException("'${name}' property is required.");
    }

    public static void checkNotBlank(def name, def value) {
        if (!value?.trim()) throw new SemanticVersionGradleScriptException("'${name}' property can not be blank.");
    }

    public static void checkNotNegative(def name, def value) {
        if (value < 0) throw new SemanticVersionGradleScriptException("'${name}' property can not be negative.");
    }
}
