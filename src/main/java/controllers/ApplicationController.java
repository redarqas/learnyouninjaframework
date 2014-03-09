/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.utils.NinjaProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.urlfetch.FetchableUrl;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith(AppEngineFilter.class)
public class ApplicationController
{

  private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

  @Inject
  private FetchableUrl urlFetcher;

  @Inject
  private NinjaProperties ninjaProperties;

  public Result pullHeadlines()
  {
    //TODO : Headlines 
    final String url = ninjaProperties.get("metro.rss.xml.mobile.url");
    final String encoding = ninjaProperties.get("app.defaultencoding");
    final Map<String, Object> params = new LinkedHashMap<String, Object>();
    params.put("path", "/");
    final HTTPResponse response = urlFetcher.createCall(url, encoding).setParameters(params).get();
    //TODO : serve it as xml not text
    return Results.xml().render("ok");
  }

  public Result index()
  {
    return Results.html();
  }

  public Result helloWorldJson()
  {
    SimplePojo simplePojo = new SimplePojo();
    simplePojo.content = "Hello World! Hello Json!";
    return Results.json().render(simplePojo);
  }

  public Result userDashbord(@PathParam("id") String id)
  {
    System.out.println("DOkakakak");

    return Results.json().render(id);
  }

  public static class SimplePojo
  {
    public String content;
  }

}
