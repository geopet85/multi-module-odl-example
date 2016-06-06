/*
 * Copyright © 2015 a and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.reference.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.reference.rev150722.ReferenceService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferenceProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(ReferenceProvider.class);
    private BindingAwareBroker.RpcRegistration<ReferenceService> referenceService;
    //Members related to MD-SAL operations
    private DataBroker dataBroker;
    private SalFlowService salFlowService;

    public ReferenceProvider(DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry) {
        this.dataBroker = dataBroker;
        this.salFlowService = rpcProviderRegistry.getRpcService(SalFlowService.class);
        this.referenceService = rpcProviderRegistry.addRpcImplementation(ReferenceService.class,
                new ReferenceMonitorImpl(dataBroker));

        ApplicationRegistryUtils.getInstance().setDb(dataBroker);
        ApplicationRegistryUtils.getInstance().initializeDataTree();
    }

    @Override
    public void close() throws Exception {
        LOG.info("ReferenceProvider Closed");
    }

    @Override
    public void onSessionInitiated(ProviderContext providerContext) {
        LOG.info("ReferenceProvider Initiated");
    }
}
