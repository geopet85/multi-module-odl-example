/*
 * Copyright © 2015 George and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.negotiator.impl;

import eu.virtuwind.monitoring.impl.TopologyListener;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.MonitoringListener;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.TopologyChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by geopet on 04/06/16.
 */
public class TopologyChangeListenerImpl implements MonitoringListener{

    private static final Logger LOG = LoggerFactory.getLogger(NegotiatorProvider.class);

    public TopologyChangeListenerImpl() {
        LOG.info("TopologyChangeListenerImpl started.");
    }

    @Override
    public void onTopologyChanged(TopologyChanged notification) {
        LOG.info("Received notification {}", notification);
    }
}
