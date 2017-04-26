package controllers;

import play.*;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

import models.*;


public class Application extends Controller {

    public static final String app_id="218244";
    public static final String firstRedirectUri="http://localhost:9000/";
    public static final String key=" 33c0e034a853e3014fc9ee671b15e99d";

    public static void index() {
        render();
    }

    public static void connectUser(String email){
        String urlUser=callURL("https://connect.deezer.com/oauth/auth.php?app_id="+app_id +"&redirect_uri="+firstRedirectUri+"&perms=basic_access,email");
        renderHtml(urlUser);

    }
    public static void accessToken(String code){
        String urlUser=callURL("https://connect.deezer.com/oauth/access_token.php?app_id="+app_id+"&secret="+key+"&code="+code);
        renderHtml(urlUser);
    }

    public static void saveUser(Long id, String nickname){
        User user=new User(id,nickname);
        user.save();
        //String urlUser=callURL("https://api.deezer.com/user/"+id);


        render("View.html",user);
    }

    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }

}