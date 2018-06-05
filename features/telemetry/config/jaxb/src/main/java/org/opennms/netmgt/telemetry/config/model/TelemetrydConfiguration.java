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

package org.opennms.netmgt.telemetry.config.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.core.xml.ValidateUsing;

import com.google.common.base.MoreObjects;

@XmlRootElement(name = "telemetryd-config")
@ValidateUsing()
@XmlAccessorType(XmlAccessType.NONE)
public class TelemetrydConfiguration {
    @XmlElement(name="listener")
    private List<Listener> listeners = new ArrayList<>();

    @XmlElement(name="adapter")
    private List<Adapter> adapters = new ArrayList<>();

    public List<Listener> getListeners() {
        return this.listeners;
    }

    public void setListeners(final List<Listener> listeners) {
        this.listeners = listeners;
    }

    public List<Adapter> getAdapters() {
        return this.adapters;
    }

    public void setAdapters(final List<Adapter> adapters) {
        this.adapters = adapters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TelemetrydConfiguration that = (TelemetrydConfiguration) o;

        return Objects.equals(this.listeners, that.listeners) &&
                Objects.equals(this.adapters, that.adapters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.listeners,this.adapters);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("listeners", this.listeners)
                .add("adapters", this.adapters)
                .toString();
    }
}
