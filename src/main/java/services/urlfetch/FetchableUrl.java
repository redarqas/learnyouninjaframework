package services.urlfetch;

public interface FetchableUrl
{
  UrlCall createCall(String url, String encoding);
}
