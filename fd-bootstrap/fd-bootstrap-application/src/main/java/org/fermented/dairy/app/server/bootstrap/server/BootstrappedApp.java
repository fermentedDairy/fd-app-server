package org.fermented.dairy.app.server.bootstrap.server;

import org.fermented.dairy.app.server.bootstrap.api.Bootstrapped;
import org.fermented.dairy.app.server.bootstrap.api.Bootstrapper;
import org.fermented.dairy.app.server.bootstrap.server.exceptions.BootstrapException;

import java.util.*;
import java.util.stream.Collectors;

import static org.fermented.dairy.app.server.bootstrap.server.logging.LOGGING.BOOTSTRAPPING_LOGGER;

/// Application container that manages bootstrapped components using ServiceLoader SPI.
///
/// @since 1.0.0
public final class BootstrappedApp {

    private final Map<String, ? extends Bootstrapped> componentMap;

    /// Constructs a new BootstrappedApp, bootstrapping all components via ServiceLoader SPI.
    ///
    /// @throws BootstrapException If a component bootstrap fails or duplicate components are detected.
    public BootstrappedApp() throws BootstrapException {
        componentMap = buildComponentMap();
    }

    /// Builds a map of component names to their bootstrapped instances.
    ///
    /// @return A map of component names to Bootstrapped instances.
    /// @throws BootstrapException If a component bootstrap fails or duplicate components are detected.
    /// @since 1.0.0
    private Map<String, Bootstrapped> buildComponentMap() throws BootstrapException{
        final Map<String, Bootstrapper> componentBootstrappers = new HashMap<>();
        final Map<String, String> duplicateComponentNames = new HashMap<>();
        final Map<String, String> config = getConfig();
        final ServiceLoader<Bootstrapper> loader = ServiceLoader.load(Bootstrapper.class);

        final List<Pair<String, Bootstrapper>> componentNamePair = loader.stream()
                .map(ServiceLoader.Provider::get)
                .map(bootstrapper -> Pair.of(bootstrapper.name(), bootstrapper))
                .toList();

        componentNamePair.forEach(
                pair -> {
                    final String name = pair.key;
                    final Bootstrapper bootstrapper = pair.value;
                    if (componentBootstrappers.containsKey(name)) {
                        duplicateComponentNames.put(name, bootstrapper.getClass().getCanonicalName());
                    } else {
                        componentBootstrappers.put(name, bootstrapper);
                    }
                }
        );
        if (!duplicateComponentNames.isEmpty()) {
            var exception = new BootstrapException.DuplicateComponentException(duplicateComponentNames);
            BOOTSTRAPPING_LOGGER.error("Duplicate Component names", exception);
            throw exception;
        }

        return componentBootstrappers.entrySet().stream().map( entry ->
                Map.entry(entry.getKey(), entry.getValue().apply(config))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /// Starts all managed components.
    ///
    /// This method iterates through all bootstrapped components and starts them.
    ///
    /// @since 1.0.0
    public void start() {
        componentMap.values().stream().map(

        );
    }

    /// Returns the managed component map.
    ///
    /// @return A map of component names to their bootstrapped instances.
    /// @since 1.0.0
    public Map<String, ? extends Bootstrapped> componentMap() {
        return componentMap;
    }

    /// Returns the configuration map for the bootstrapped application.
    ///
    /// @return A map containing the configuration settings.
    /// @since 1.0.0
    private Map<String, String> getConfig(){
        return Map.of();
    }

    private record Pair<K, V>(
        K key,
        V value
    ){
        public static <K, V> Pair<K, V> of( K key,
                          V value
        ){
            return new Pair<>(key, value);
        }
    }
}
