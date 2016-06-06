package eu.virtuwind.reference.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

public class ReferenceModule extends eu.virtuwind.reference.impl.AbstractReferenceModule {
    public ReferenceModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public ReferenceModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, eu.virtuwind.reference.impl.ReferenceModule oldModule, java.lang.AutoCloseable oldInstance) {
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
        ReferenceProvider provider = new ReferenceProvider(dataBrokerService, rpcProviderRegistry);
        return provider;
    }

}
