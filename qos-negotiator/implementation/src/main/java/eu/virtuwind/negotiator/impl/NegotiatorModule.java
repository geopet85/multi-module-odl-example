package eu.virtuwind.negotiator.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

public class NegotiatorModule extends eu.virtuwind.negotiator.impl.AbstractNegotiatorModule {
    public NegotiatorModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public NegotiatorModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, eu.virtuwind.negotiator.impl.NegotiatorModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        DataBroker dataBrokerService = getDataBrokerDependency();
        RpcProviderRegistry rpcProviderRegistry = getRpcRegistryDependency();
        NotificationProviderService notificationService = getNotificationServiceDependency();
        NegotiatorProvider provider = new NegotiatorProvider(dataBrokerService, rpcProviderRegistry, notificationService);
        notificationService.registerNotificationListener(new TopologyChangeListenerImpl());
        return provider;
    }

}
