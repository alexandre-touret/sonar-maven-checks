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

import de.lgohlke.sonar.MavenPlugin;
import de.lgohlke.sonar.maven.BridgeMojoMapper;
import de.lgohlke.sonar.maven.MavenBaseSensor;
import de.lgohlke.sonar.maven.MavenBaseSensorI;
import de.lgohlke.sonar.maven.org.codehaus.mojo.versions.rules.DependencyVersionMavenRule;
import lombok.Getter;
import org.apache.maven.project.MavenProject;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.maven.MavenPluginHandler;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.Violation;
import org.sonar.batch.MavenPluginExecutor;
import org.sonar.plugins.xml.language.Xml;

import java.util.List;
import java.util.Map;

import static de.lgohlke.sonar.maven.org.codehaus.mojo.versions.Configuration.BASE_IDENTIFIER;


public class DisplayDependencyUpdatesSensor implements MavenBaseSensorI<DisplayUpdatesBridgeMojoResultHandler> {
  private final DisplayUpdatesBridgeMojoResultHandler resultHandler = new DisplayUpdatesBridgeMojoResultHandler();
  @Getter
  private final BridgeMojoMapper<DisplayUpdatesBridgeMojoResultHandler> handler = new BridgeMojoMapper<DisplayUpdatesBridgeMojoResultHandler>(DisplayDependencyUpdatesBridgeMojo.class, resultHandler);
  private final MavenBaseSensor<DisplayUpdatesBridgeMojoResultHandler> baseSensor;
  private MavenProject mavenProject;


  public DisplayDependencyUpdatesSensor(MavenPluginExecutor mavenPluginExecutor,
                                        MavenProject mavenProject) {
    this.mavenProject = mavenProject;
    baseSensor = new MavenBaseSensor<DisplayUpdatesBridgeMojoResultHandler>(mavenPluginExecutor, mavenProject, BASE_IDENTIFIER, this);
  }

  @Override
  public String toString() {
    return baseSensor.toString();
  }

  @Override
  public MavenPluginHandler getMavenPluginHandler(final Project project) {
    return baseSensor.getMavenPluginHandler(project);
  }

  @Override
  public void analyse(final Project project, final SensorContext context) {
    DisplayUpdatesBridgeMojoResultHandler handler = this.handler.getResultTransferHandler();

    Rule rule = Rule.create(MavenPlugin.REPOSITORY_KEY, new DependencyVersionMavenRule().getKey());
    final File file = new File("", mavenProject.getFile().getName());
    file.setLanguage(Xml.INSTANCE);

    if (handler.getUpdateMap() != null) {
      for (Map.Entry<String, List<ArtifactUpdate>> entry : handler.getUpdateMap().entrySet()) {
        String section = entry.getKey();
        List<ArtifactUpdate> updates = entry.getValue();
        for (ArtifactUpdate update : updates) {
          Violation violation = Violation.create(rule, file);
          violation.setLineId(1);

          String hint = "(found in " + section + ")";
          violation.setMessage(update.toString() + " " + hint);
          context.saveViolation(violation);
        }
      }
    }
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return baseSensor.shouldExecuteOnProject(project);
  }
}
