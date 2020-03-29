public class UrlHandler {
    static String url;

    public UrlHandler(String ur){
        url = ur;
    }
    public String getUrl(){
        return url;
    }

    public void setUrl(String u){
        url = u;
    }

    public String toString(){
        return url;
    }

}
