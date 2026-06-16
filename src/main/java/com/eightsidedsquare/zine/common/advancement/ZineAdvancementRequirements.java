package com.eightsidedsquare.zine.common.advancement;

import java.util.List;

public interface ZineAdvancementRequirements {
    default void zine$setRequirements(List<List<String>> requirements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequirement(List<String> requirement) {
        throw new UnsupportedOperationException("Implemented `via mixin.");
    }

    default void zine$addRequirements(List<List<String>> requirements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequirement(int index, String requirement) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRequirements(int index, List<String> requirements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setRequirement(int index, List<String> requirements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
