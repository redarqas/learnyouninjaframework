package services.urlfetch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class UrlFetcher
    implements FetchableUrl
{

  @Override
  public UrlCall createCall(String url, String encoding)
  {
    return new FectchUrlCall(url, encoding);
  }

  public class FectchUrlCall
      extends UrlCall
  {

    public FectchUrlCall(String url, String encoding)
    {
      super(url, encoding);
    }

    @Override
    public HTTPResponse get()
    {
      HTTPResponse response = null;
      try
      {
        final HTTPRequest request = new HTTPRequest(new URL(prepareUrl()), HTTPMethod.GET, FetchOptions.Builder.followRedirects().setDeadline(timeout));
        response = URLFetchServiceFactory.getURLFetchService().fetch(request);
      }
      catch (MalformedURLException e)
      {
        // TODO : How to manage this error
        e.printStackTrace();
      }
      catch (IOException e)
      {
        // TODO : How to manage this error
        e.printStackTrace();
      }
      return response;
    }

    private String prepareUrl()
    {
      // must add params to queryString/url
      StringBuilder sb = new StringBuilder(url);
      if (url.indexOf("?") > 0)
      {
        sb.append('&');
      }
      else
      {
        sb.append('?');
      }
      int count = 0;
      for (Map.Entry<String, Object> e : parameters.entrySet())
      {
        count++;
        String key = e.getKey();
        Object value = e.getValue();
        if (value == null)
          continue;

        if (value instanceof Collection<?> || value.getClass().isArray())
        {
          Collection<?> values = value.getClass().isArray() ? Arrays.asList((Object[]) value) : (Collection<?>) value;
          for (Object v : values)
          {
            if (count > 1)
            {
              sb.append('&');
            }
            sb.append(encode(key));
            sb.append('=');
            sb.append(encode(v.toString()));
          }
        }
        else
        {
          if (count > 1)
          {
            sb.append('&');
          }
          sb.append(encode(key));
          sb.append('=');
          sb.append(encode(value.toString()));
        }

      }

      return sb.toString();
    }

    private String encode(String part)
    {
      try
      {
        return URLEncoder.encode(part, encoding);
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
    }

  }

}
