/*
 * Copyright © 2015 George and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.negotiator.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.negotiator.rev150722.NegotiatorService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NegotiatorProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(NegotiatorProvider.class);
    private BindingAwareBroker.RpcRegistration<NegotiatorService> negotiatorService;
    //Members related to MD-SAL operations
    private DataBroker dataBroker;
    private SalFlowService salFlowService;
    private NotificationProviderService notificationService;

    public NegotiatorProvider(DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry,
                              NotificationProviderService notificationService) {
        this.dataBroker = dataBroker;
        this.salFlowService = rpcProviderRegistry.getRpcService(SalFlowService.class);
        this.notificationService = notificationService;
        this.negotiatorService = rpcProviderRegistry.addRpcImplementation(NegotiatorService.class,
                new QoSNegotiatorImpl(salFlowService, dataBroker));

    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("NegotiatorProvider Session Initiated");
    }

    @Override
    public void close() throws Exception {
        LOG.info("NegotiatorProvider Closed");
    }

}
