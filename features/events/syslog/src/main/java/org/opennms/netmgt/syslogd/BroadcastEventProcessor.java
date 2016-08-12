/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2002-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.syslogd;

import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.events.api.EventConstants;
import org.opennms.netmgt.events.api.annotations.EventHandler;
import org.opennms.netmgt.events.api.annotations.EventListener;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seth
 * @author <a href="mailto:joed@opennms.org">Johan Edstrom</a>
 * @author <a href="mailto:tarus@opennms.org">Tarus Balog </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
@EventListener(name="OpenNMS.Syslogd", logPrefix="syslogd")
public class BroadcastEventProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BroadcastEventProcessor.class);

    @Autowired
    private SyslogdIPMgr m_syslogdIPMgr;

    @Autowired
    private NodeDao m_nodeDao;

    @EventHandler(uei=EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI)
    @Transactional
    public void handleNodeGainedInterface(Event event) {
        LOG.debug("Received event: {}", event.getUei());
        Long nodeId = event.getNodeid();
        if (nodeId == null) {
            LOG.error(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Event with no node ID: " + event.toString());
            return;
        }
        OnmsNode node = m_nodeDao.get(nodeId.intValue());
        if (node == null) {
            LOG.warn(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Cannot find node in DB: " + nodeId);
            return;
        }
        // add to known nodes
        m_syslogdIPMgr.setNodeId(node.getLocation().getLocationName(), event.getInterfaceAddress(), nodeId.intValue());
        LOG.debug("Added {} to known node list", event.getInterface());
    }

    @EventHandler(uei=EventConstants.INTERFACE_DELETED_EVENT_UEI)
    @Transactional
    public void handleInterfaceDeleted(Event event) {
        LOG.debug("Received event: {}", event.getUei());
        Long nodeId = event.getNodeid();
        if (nodeId == null) {
            LOG.error(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Event with no node ID: " + event.toString());
            return;
        }
        OnmsNode node = m_nodeDao.get(nodeId.intValue());
        if (node == null) {
            LOG.warn(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Cannot find node in DB: " + nodeId);
            return;
        }
        // remove from known nodes
        m_syslogdIPMgr.removeNodeId(node.getLocation().getLocationName(), event.getInterfaceAddress());
        LOG.debug("Removed {} from known node list", event.getInterface());
    }

    @EventHandler(uei=EventConstants.INTERFACE_REPARENTED_EVENT_UEI)
    @Transactional
    public void handleInterfaceReparented(Event event) {
        LOG.debug("Received event: {}", event.getUei());
        Long nodeId = event.getNodeid();
        if (nodeId == null) {
            LOG.error(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Event with no node ID: " + event.toString());
            return;
        }
        OnmsNode node = m_nodeDao.get(nodeId.intValue());
        if (node == null) {
            LOG.warn(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI + ": Cannot find node in DB: " + nodeId);
            return;
        }
        // add to known nodes
        m_syslogdIPMgr.setNodeId(node.getLocation().getLocationName(), event.getInterfaceAddress(), nodeId.intValue());
        LOG.debug("Reparented {} to known node list", event.getInterface());
    }
}
