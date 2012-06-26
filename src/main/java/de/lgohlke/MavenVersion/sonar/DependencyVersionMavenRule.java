/*
 * Sonar maven checks plugin
 * Copyright (C) 2011 ${owner}
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package de.lgohlke.MavenVersion.sonar;

import de.lgohlke.MavenVersion.handler.ArtifactUpdate;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(key = DependencyVersionMavenRule.KEY, priority = Priority.MINOR, name = DependencyVersionMavenRule.NAME, description = DependencyVersionMavenRule.DESCRIPTION)
public class DependencyVersionMavenRule implements MavenRule {
  protected final static String KEY = "Old Dependency";
  protected final static String NAME = "[POM] found an updated version for dependency";
  protected final static String DESCRIPTION = "TODO";

  public String getName() {
    return NAME;
  }

  public String getKey() {
    return KEY;
  }

  public String formatMessage(final ArtifactUpdate update) {
    return "update available for: " + update;
  }
}
