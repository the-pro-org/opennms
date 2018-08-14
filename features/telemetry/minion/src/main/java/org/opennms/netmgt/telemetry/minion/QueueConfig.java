/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2017 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2017 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.telemetry.minion;

import org.opennms.netmgt.telemetry.config.api.QueueDefinition;

import java.util.Map;
import java.util.Optional;

public class QueueConfig implements QueueDefinition {
    private final String name;
    private final Optional<Integer> numThreads;
    private final Optional<Integer> batchSize;
    private final Optional<Integer> batchIntervalMs;
    private final Optional<Integer> queueSize;

    public QueueConfig(final Map<String, String> parameters) {
        this.name = MapUtils.getRequiredString("name", parameters);
        this.numThreads = MapUtils.getOptionalInteger("threads", parameters);
        this.queueSize = MapUtils.getOptionalInteger("queue.size", parameters);
        this.batchSize = MapUtils.getOptionalInteger("batch.size", parameters);
        this.batchIntervalMs = MapUtils.getOptionalInteger("batch.interval", parameters);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Integer> getNumThreads() {
        return this.numThreads;
    }

    @Override
    public Optional<Integer> getBatchSize() {
        return this.batchSize;
    }

    @Override
    public Optional<Integer> getBatchIntervalMs() {
        return this.batchIntervalMs;
    }

    @Override
    public Optional<Integer> getQueueSize() {
        return this.queueSize;
    }
}
