package eu.virtuwind.monitoring.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.TopologyChanged;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.TopologyChangedBuilder;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TopologyId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.TopologyKey;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

public class MonitoringModule extends eu.virtuwind.monitoring.impl.AbstractMonitoringModule {
    public MonitoringModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public MonitoringModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, eu.virtuwind.monitoring.impl.MonitoringModule oldModule, java.lang.AutoCloseable oldInstance) {
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
        MonitoringProvider provider = new MonitoringProvider(dataBrokerService, rpcProviderRegistry, notificationService);

        InstanceIdentifier<Link> linkInstance = InstanceIdentifier.builder(NetworkTopology.class)
                .child(Topology.class, new TopologyKey(new TopologyId("flow:1"))).child(Link.class).build();
        dataBrokerService.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, linkInstance,
                new TopologyListener(dataBrokerService, notificationService),
                AsyncDataBroker.DataChangeScope.BASE);

        return provider;
    }

}
