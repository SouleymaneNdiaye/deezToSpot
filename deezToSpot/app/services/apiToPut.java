package services;

/**
 * Created by Souleymane on 21/05/2017.
 */
//public class ApiToPut extends Api {
//
//    public static final String DEFAULT_HOST = "api.spotify.com";
//    public static final int DEFAULT_PORT = 443;
//    public static final HttpManager DEFAULT_HTTP_MANAGER = SpotifyHttpManager.builder().build();
//    public static final String DEFAULT_AUTHENTICATION_HOST = "accounts.spotify.com";
//    public static final int DEFAULT_AUTHENTICATION_PORT = 443;
//    private HttpManager httpManager;
//    private UtilProtos.Url.Scheme scheme;
//    private int port;
//    private String host;
//    private String accessToken;
//    private String refreshToken;
//
//
//
//    private void setDefaults(com.wrapper.spotify.methods.AbstractRequest.Builder builder) {
//        builder.httpManager(this.httpManager);
//        builder.scheme(this.scheme);
//        builder.host(this.host);
//        builder.port(this.port);
//        if(this.accessToken != null) {
//            builder.header("Authorization", "Bearer " + this.accessToken);
//        }
//
//    }
//
//    public com.wrapper.spotify.methods.AddTrackToPlaylistRequest.Builder addAlbums(Album album) {
//        com.wrapper.spotify.methods.AddTrackToPlaylistRequest.Builder builder = AddTrackToPlaylistRequest.builder();
//        this.setDefaults(builder);
//        builder.path("https://api.spotify.com/v1/me/albums?ids=" + album.getId());
//        return builder;
//    }
//
//
//    public static ApiToPut.Builder builder() {
//        return new Api.Builder();
//    }
//
//}
