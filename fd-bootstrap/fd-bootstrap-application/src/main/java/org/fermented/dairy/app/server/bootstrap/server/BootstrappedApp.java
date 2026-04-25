package org.fermented.dairy.app.server.bootstrap.server;

import org.fermented.dairy.app.server.bootstrap.api.Bootstrapped;
import org.fermented.dairy.app.server.bootstrap.api.Bootstrapper;
import org.fermented.dairy.app.server.bootstrap.api.error.StartupError;
import org.fermented.dairy.app.server.bootstrap.server.exceptions.BootstrapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static org.fermented.dairy.app.server.bootstrap.server.logging.LOGGING.BOOTSTRAPPING_LOGGER_TOPIC;

/// Application container that manages bootstrapped components using ServiceLoader SPI.
///
/// @since 1.0.0
public final class BootstrappedApp {

    private static final Logger LOG = LoggerFactory.getLogger(BOOTSTRAPPING_LOGGER_TOPIC);

    private final Map<String, ? extends Bootstrapped> componentMap;

    /// Constructs a BootstrappedApp by discovering all Bootstrapper implementations
    /// through Java's ServiceLoader SPI mechanism.
    ///
    /// This constructor loads all [Bootstrapper] classes from the classpath
    /// using [ServiceLoader]. Each bootstrapper is invoked with an empty configuration
    /// map to produce a bootstrapped component that is stored in a map indexed by the
    /// component's startup identifier.
    ///
    /// The loaded bootstrappers are used to initialize their respective components,
    /// which are then available in the [#componentMap] field for management operations.
    ///
    /// @throws StartupError if any bootstrapper fails to produce a valid component
    /// @since 1.0.0
    public BootstrappedApp() throws BootstrapException {
        componentMap = buildComponentMap();
    }

    /// Builds a map of component names to their bootstrapped instances.
    ///
    /// @return a map of component names to Bootstrapped instances
    private Map<String, Bootstrapped> buildComponentMap() throws BootstrapException{
        final Map<String, Bootstrapper> componentBootstrappers = new HashMap<>();
        final Set<String> duplicateComponentNames = new HashSet<>();
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
                        duplicateComponentNames.add(name);
                    } else {
                        componentBootstrappers.put(name, bootstrapper);
                    }
                }
        );
        if (!duplicateComponentNames.isEmpty()) {
            throw new BootstrapException.DuplicateComponentException(duplicateComponentNames);
        }

        return componentBootstrappers.entrySet().stream().map( entry ->
                Map.entry(entry.getKey(), entry.getValue().apply(config))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /// Starts all managed components.
    public void start() {
        componentMap.values().forEach(Bootstrapped::start);
    }

    /// Returns the managed component map.
    ///
    /// @return the component map
    public Map<String, ? extends Bootstrapped> componentMap() {
        return componentMap;
    }

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
