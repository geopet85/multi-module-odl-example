/*
 * Copyright © 2015 George and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.monitoring.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.TopologyChanged;
import org.opendaylight.yang.gen.v1.urn.eu.virtuwind.monitoring.rev150722.TopologyChangedBuilder;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by geopet on 04/06/16.
 */
public class TopologyListener implements DataChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(TopologyListener.class);
    private NotificationProviderService notificationService;
    private final DataBroker dataBroker;

    public TopologyListener(DataBroker dataBroker, NotificationProviderService notificationService) {
        this.dataBroker = dataBroker;
        this.notificationService = notificationService;
    }


    @Override
    public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> dataChangeEvent) {
        LOG.info("Data changed");

        TopologyChanged topo = new TopologyChangedBuilder()
                .setTopologyChange(TopologyChanged.TopologyChange.LinkFailed)
                .setLinkId("openflow:1")
                .build();
        notificationService.publish(topo);

        if (dataChangeEvent == null) {
            return;
        }
        Map<InstanceIdentifier<?>, DataObject> createdData = dataChangeEvent.getCreatedData();
        Set<InstanceIdentifier<?>> removedPaths = dataChangeEvent.getRemovedPaths();
        Map<InstanceIdentifier<?>, DataObject> originalData = dataChangeEvent.getOriginalData();
        boolean isGraphUpdated = false;

        if (createdData != null && !createdData.isEmpty()) {
            Set<InstanceIdentifier<?>> linksIds = createdData.keySet();
            for (InstanceIdentifier<?> linkId : linksIds) {
                if (Link.class.isAssignableFrom(linkId.getTargetType())) {
                    Link link = (Link) createdData.get(linkId);
                    if (!(link.getLinkId().getValue().contains("host"))) {
                        isGraphUpdated = true;
                        LOG.debug("Graph is updated! Added Link {}", link.getLinkId().getValue());
                        break;
                    }
                }
            }
        }

        if (removedPaths != null && !removedPaths.isEmpty() && originalData != null && !originalData.isEmpty()) {
            for (InstanceIdentifier<?> instanceId : removedPaths) {
                if (Link.class.isAssignableFrom(instanceId.getTargetType())) {
                    Link link = (Link) originalData.get(instanceId);
                    if (!(link.getLinkId().getValue().contains("host"))) {
                        isGraphUpdated = true;
                        LOG.debug("Graph is updated! Removed Link {}", link.getLinkId().getValue());
                        break;
                    }
                }
            }
        }

        if (!isGraphUpdated) {
            return;
        }
    }
}