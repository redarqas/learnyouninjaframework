package services.urlfetch;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class UrlCall
{
  public String url;

  public final String encoding;

  public double timeout = 60d;

  public Map<String, Object> parameters = new LinkedHashMap<String, Object>();

  public UrlCall()
  {
    this.encoding = "utf-8";
  }

  public UrlCall(String url, String encoding)
  {
    this.url = url;
    this.encoding = encoding;
  }

  public UrlCall setParameter(String name, Object value)
  {
    this.parameters.put(name, value);
    return this;
  }

  public UrlCall params(Map<String, Object> parameters)
  {
    this.parameters = parameters;
    return this;
  }

  public UrlCall setParameters(Map<String, Object> parameters)
  {
    this.parameters = parameters;
    return this;
  }

  public abstract com.google.appengine.api.urlfetch.HTTPResponse get();

}
