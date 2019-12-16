package hr254.utils;

import okhttp3.*;

import java.util.Base64;

public class WebClient {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    private static String bearer="";

    // private final HttpClient Client = new DefaultHttpClient();
    private static  String Content;
    private String ACTION;
    //JSONObject data = null;//new JSONObject();
    private String Error = null;


    public String mpesaendpoint,authurl,app_secret,app_key;



    int sizeData = 0;
    public WebClient()
    {}

    public WebClient(String mpesaendpoint,String authurl,String app_secret,String app_key)
    {
        this.mpesaendpoint=mpesaendpoint;
        this.authurl=authurl;
        this.app_secret=app_secret;
        this.app_key=app_key;

    }
    public String webRequest(String url,String data,String post_get,String params)
    {

        try{


            RequestBody body = RequestBody.create(JSON, data);
            Request request =null;
            if(post_get.equalsIgnoreCase("POST")) {
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .url(url)
                        .post(body)
                        .build();
            }
            else
            {
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .url(url)
                        .get()
                        .build();
            }
            Response response = client.newCall(request).execute();
            Content= response.body().string();

        }
        catch(Exception e)
        {

            e.printStackTrace();
            System.out.println("ERROR"+e.getMessage());
        }

        return Content;
    }


}
