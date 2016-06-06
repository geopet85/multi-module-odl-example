/*
 * Copyright © 2015 George and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.negotiator.impl;

import eu.virtuwind.monitoring.impl.AddressMonitor;
import eu.virtuwind.monitoring.impl.NodeMonitor;
import eu.virtuwind.resource.impl.SwitchConfigurator;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.negotiator.rev150722.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.Future;

/**
 * Created by geopet on 26/5/2016.
 */
public class QoSNegotiatorImpl implements NegotiatorService {

    private static final Logger LOG = LoggerFactory.getLogger(QoSNegotiatorImpl.class);
    private SalFlowService salFlowService;
    private DataBroker db;

    public QoSNegotiatorImpl() {

    }

    public QoSNegotiatorImpl(SalFlowService salFlowService, DataBroker db) {
        this.salFlowService = salFlowService;
        this.db = db;
    }

    @Override
    public Future<RpcResult<NegotiationOutput>> negotiation(NegotiationInput input) {
        LOG.info("Received negotiation request {}", input);

        new NodeMonitor(db).measureNodeStatistics("openflow:1", "openflow:1:2");
        new AddressMonitor(db).getNodeFromIpAddress("10.0.0.1");

        new SwitchConfigurator(salFlowService).send("openflow:1", "openflow:1:2");

        NegotiationOutput output = new NegotiationOutputBuilder()
                .setAppId(input.getAppId())
                .setDiffBandwidth(new BigDecimal(0))
                .setDiffJitter(new BigDecimal(0))
                .setDiffPacketDelay(new BigDecimal(0))
                .setDiffPacketLoss(new BigDecimal(0))
                .setResponse(NegotiationOutcome.ACCEPT)
                .build();
        LOG.info("Sending negotiation response {}", output);


        return RpcResultBuilder.success(output).buildFuture();
    }
}
