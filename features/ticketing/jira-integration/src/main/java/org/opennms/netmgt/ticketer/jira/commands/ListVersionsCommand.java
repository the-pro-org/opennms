/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2016 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2016 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.ticketer.jira.commands;

import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Version;
import com.google.common.base.Strings;

@Command(scope = "jira", name = "list-versions", description="Uses the JIRA ReST API to list all versions")
public class ListVersionsCommand extends AbstractJiraCommand {

    @Option(name="-k", aliases="--project-key", description="A project key to limit issue types. If defined it overwrites the default defined in the jira ticketer plugin configuration")
    String projectKey;

    @Override
    protected void doExecute(JiraRestClient jiraRestClient) throws Exception {
        final String theProjectKey = Strings.isNullOrEmpty(projectKey) ? getConfig().getProjectKey() : projectKey;
        final Iterable<Version> versions = jiraRestClient.getProjectClient().getProject(theProjectKey).get().getVersions();
        if (!versions.iterator().hasNext()) {
            System.out.println("No versions found for project '" + theProjectKey + "'.");
            return;
        }
        System.out.println(String.format(DEFAULT_ROW_FORMAT, "Id", "Name", "Description"));
        for (Version eachVersion : versions) {
            System.out.println(
                    String.format(
                            DEFAULT_ROW_FORMAT,
                            eachVersion.getId(),
                            eachVersion.getName(),
                            eachVersion.getDescription() == null ? "" : removeNewLines(eachVersion.getDescription())));
        }
    }
}
