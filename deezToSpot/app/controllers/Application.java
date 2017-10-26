package controllers;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.models.ClientCredentials;
import models.Album;
import models.User;
import net.sf.json.JSON;
import play.mvc.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class Application extends Controller {

    public static String app_id = "218244";
    public static String firstRedirectUri = "http://localhost:9000/follow/?";
    public static String key = "33c0e034a853e3014fc9ee671b15e99d";
    public static List<Album> albumsToSpot;

    public static void index() {
        render();
    }
    public static void channel() {
        render("channel.html");
    }

    public static void connectUser(String email) {
        String urlUser = callURL("https://connect.deezer.com/oauth/auth.php?app_id=" + app_id + "&redirect_uri=" + firstRedirectUri + "&perms=basic_access,email");
        //renderHtml(urlUser);

    }

    public static void getDeezAlbum(String data){
        Gson gson=new Gson();

        Album album = new Gson().fromJson(data,Album.class);

        render("viewAlbum.html",album);

    }

    public static void indexDeez(String user) throws FileNotFoundException {
        render("indexDeez.html", user);
    }
    public static void getDeezView(String user) throws FileNotFoundException {
        render("deezView.html", user);
    }

    public static void saveUser(Long id, String nickname) {
        User user = new User(id, nickname);
        user.save();
        //String urlUser=callURL("https://api.deezer.com/user/"+id);


        render("View.html", user);
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
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }

    public static void getGson() throws FileNotFoundException {

        JsonReader jsonReader = new JsonReader(new FileReader("public/albumDeezer.json"));
        Gson gson = new Gson();
        List<Album> albums = new ArrayList<Album>();
        Type listType = new TypeToken<ArrayList<Album>>() {
        }.getType();

        List<Album> album = new Gson().fromJson(jsonReader, listType);
        albumsToSpot = album;
        render("afficherAlbum.html", album);

    }

    public static void spotConnection() {
        final String clientId = "2c7ac924a805414aac4cc62d009ffaa0";
        final String clientSecret = "308218fb2c2240c1b0cd0c256aa3fdf5";

        final Api api = Api.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

/* Create a request object. */
        final ClientCredentialsGrantRequest request = api.clientCredentialsGrant().build();

/* Use the request object to make the request, either asynchronously (getAsync) or synchronously (get) */
        final SettableFuture<ClientCredentials> responseFuture = request.getAsync();

/* Add callbacks to handle success and failure */
        Futures.addCallback(responseFuture, new FutureCallback<ClientCredentials>() {
            @Override
            public void onSuccess(ClientCredentials clientCredentials) {
    /* The tokens were retrieved successfully! */
                System.out.println("Successfully retrieved an access token! " + clientCredentials.getAccessToken());
                System.out.println("The access token expires in " + clientCredentials.getExpiresIn() + " seconds");

    /* Set access token on the Api object so that it's used going forward */
                api.setAccessToken(clientCredentials.getAccessToken());

    /* Please note that this flow does not return a refresh token.
   * That's only for the Authorization code flow */
            }

            @Override
            public void onFailure(Throwable throwable) {
    /* An error occurred while getting the access token. This is probably caused by the client id or
     * client secret is invalid. */
            }
        });
    }
}

//
//    public static void putSpotAlbums() {
//        final String clientId = "2c7ac924a805414aac4cc62d009ffaa0";
//        final String clientSecret = "308218fb2c2240c1b0cd0c256aa3fdf5";
//        final String indexDeez = "BQDM28bYRPL3xnteWVnWYz0GB3f8II9Xfz1eGPwqcQnM8HB8veFIvEQXETaSbuPIWtqAkhMw80VLQv5q5uUxFg";
//
//        final Api api = Api.builder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .indexDeez(indexDeez)
//                .build();
//        for (Album deezAlbum : albumsToSpot) {
//            final AlbumSearchRequest request1 = api.searchAlbums(deezAlbum.title).offset(0).limit(1).build();
//
//            try {
//                final Page<SimpleAlbum> albumSearchResult = request1.get();
//
//                System.out.println("Printing results..");
//                for (SimpleAlbum album : albumSearchResult.getItems()) {
//                    System.out.println("Ajout de l'album: " + album.getName() + "avec l'id: " + album.getId());
//                    SpotifyHttpManager.builder().build().put("https://api.spotify.com/v1/me/albums?ids=" + album.getId());
//
//                }
//
//            } catch (Exception e) {
//                System.out.println("Something went wrong!" + e.getMessage());
//            }
//        }
//    }
//}