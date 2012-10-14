/*
 * Sonar maven checks plugin
 * Copyright (C) 2012 Lars Gohlke
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
package de.lgohlke.sonar.maven.org.codehaus.mojo.versions.rules;

import de.lgohlke.sonar.MavenRule;
import org.sonar.check.Priority;
import org.sonar.check.Rule;


@Rule(
    key = DependencyVersion.KEY, priority = Priority.MINOR, name = DependencyVersion.NAME,
    description = DependencyVersion.DESCRIPTION
)
public class DependencyVersion implements MavenRule {
  public static final String KEY = "Old Dependency";
  protected static final String NAME = "[Maven] found an newer version for a dependency in use";
  protected static final String DESCRIPTION = "this dependency has a newer version available";
}