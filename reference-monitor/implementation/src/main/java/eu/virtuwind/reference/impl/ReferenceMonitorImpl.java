/*
 * Copyright © 2015 George and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.reference.impl;

import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.reference.rev150722.AddApplicationInput;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.reference.rev150722.ReferenceService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;


/**
 * Created by geopet on 26/5/2016.
 */
public class ReferenceMonitorImpl implements ReferenceService {

    private static final Logger LOG = LoggerFactory.getLogger(ReferenceMonitorImpl.class);
    private DataBroker db;

    public ReferenceMonitorImpl(DataBroker db) {
        this.db = db;
    }

    @Override
    public Future<RpcResult<Void>> addApplication(AddApplicationInput input) {
        LOG.info("Adding application {}", input);

        ApplicationRegistryUtils.getInstance().writeToApplicationRegistry(input);

        return Futures.immediateFuture(RpcResultBuilder.<Void>success().build());
    }
}
