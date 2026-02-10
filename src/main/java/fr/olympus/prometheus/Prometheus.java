package fr.olympus.prometheus;

import fr.olympus.prometheus.register.AutoRegistrar;
import fr.olympus.prometheus.register.RegisterType;
import fr.olympus.prometheus.resources.PrometheusData;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Main class for the Prometheus framework.
 * Provides methods for initializing the framework, registering components, and accessing data.
 */
public class Prometheus {

    /**
     * Singleton instance of Prometheus
     */
    private static final AtomicReference<Prometheus> INSTANCE = new AtomicReference<>();

    private final PrometheusData data;

    /**
     * Private constructor to prevent instantiation
     */
    private Prometheus() {
        this.data = new PrometheusData();
    }

    /**
     * Get the singleton instance of Prometheus
     * @return Prometheus instance
     * @throws IllegalStateException if Prometheus is already initialized
     */
    public static Prometheus init(){
        Prometheus create = new Prometheus();
        if(!INSTANCE.compareAndSet(null, create)){
            throw new IllegalStateException("Prometheus is already initialized");
        }
        return create;
    }

    /**
     * Automatically register components by scanning the specified base packages for annotated classes.
     * @param type The type of components to register. see {@link RegisterType}.
     * @param basePackages The base packages to scan for components.
     */
    public static void autoRegister(RegisterType type, String... basePackages){
        AutoRegistrar.register(type, basePackages);
    }

    /**
     * Get the singleton instance of Prometheus
     * @return Prometheus instance
     * @throws IllegalStateException if Prometheus is not initialized
     */
    private static Prometheus getInstance(){
        Prometheus instance = INSTANCE.get();
        if(instance == null){
            throw new IllegalStateException("Prometheus is not initialized");
        }
        return instance;
    }

    /**
     * Get the PrometheusData instance
     * @return PrometheusData instance
     */
    public static PrometheusData getData(){
        return getInstance().data;
    }


}
