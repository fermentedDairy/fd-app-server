package org.fermented.dairy.app.server.bootstrap.server.exceptions;

import java.util.Set;

public sealed class BootstrapException extends Exception {
    public BootstrapException(String message) {
        super(message);
    }

    public static final class DuplicateComponentException extends BootstrapException{

        private final Set<String> duplicateComponentNames;

        public DuplicateComponentException(Set<String> duplicateComponentNames) {
            super("Duplicate component names found: " + duplicateComponentNames);
            this.duplicateComponentNames = duplicateComponentNames;
        }

        public Set<String> getDuplicateComponentNames() {
            return duplicateComponentNames;
        }

        @Override
        public String toString() {
            return "DuplicateComponentException{" +
                    "duplicateComponentNames=" + duplicateComponentNames +
                    '}';
        }
    }
}
