/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import com.google.inject.Inject;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;
import controllers.ApplicationController;

public class Routes
    implements ApplicationRoutes
{

  @Inject
  NinjaProperties ninjaProperties;

  @Override
  public void init(Router router)
  {
    router.GET().route("/").with(ApplicationController.class, "index");
    router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");

    // /////////////////////////////////////////////////////////////////////
    // RSS cache workers
    // /////////////////////////////////////////////////////////////////////
    if (ninjaProperties.isProd())
    {
      // TODO : Production routes, crons
    }
    router.GET().route("/cron/headlines").with(ApplicationController.class, "pullHeadlines");

    // /////////////////////////////////////////////////////////////////////
    // Assets (pictures / javascript)
    // /////////////////////////////////////////////////////////////////////
    router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
    // /////////////////////////////////////////////////////////////////////
    // Index / Catchall shows index page
    // /////////////////////////////////////////////////////////////////////
    // router.GET().route("/.*").with(ApplicationController.class, "index");
  }

}
