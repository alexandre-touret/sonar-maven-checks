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
package de.lgohlke.sonar.maven.org.codehaus.mojo.versions;

import de.lgohlke.sonar.maven.BridgeMojoMapper;
import de.lgohlke.sonar.maven.MavenBaseSensor;
import de.lgohlke.sonar.maven.MavenPluginHandlerFactory;
import org.apache.maven.project.MavenProject;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.maven.MavenPluginHandler;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.batch.MavenPluginExecutor;

public class DisplayDependencyUpdatesSensor extends MavenBaseSensor {
  private final DisplayDependencyUpdatesBridgeMojoResultHandler resultHandler = new DisplayDependencyUpdatesBridgeMojoResultHandler();
  private final BridgeMojoMapper bridgeMojoMapper = new BridgeMojoMapper(Configuration.Goals.DISPLAY_DEPENDENCY_UPDATES,
      DisplayDependencyUpdatesBridgeMojo.class, resultHandler);
  public DisplayDependencyUpdatesSensor(final RulesProfile rulesProfile, final MavenPluginExecutor mavenPluginExecutor, final MavenProject mavenProject) {
    super(rulesProfile, mavenPluginExecutor, mavenProject);
  }

  @Override
  public void analyse(final Project project, final SensorContext context) {

    DisplayDependencyUpdatesBridgeMojoResultHandler handler = (DisplayDependencyUpdatesBridgeMojoResultHandler) bridgeMojoMapper.getResultTransferHandler();

    handler.setMavenProject(getMavenProject());
    handler.analyse(project, context);
  }

  @Override
  public MavenPluginHandler getMavenPluginHandler(final Project project) {
    return MavenPluginHandlerFactory.createHandler(Configuration.BASE_IDENTIFIER + Configuration.Goals.DISPLAY_DEPENDENCY_UPDATES);
  }

  @Override
  protected BridgeMojoMapper getHandler() {
    return bridgeMojoMapper;
  }
}