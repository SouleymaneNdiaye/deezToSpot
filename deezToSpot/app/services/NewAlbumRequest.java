package services;

import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.AbstractRequest;
import com.wrapper.spotify.methods.AlbumRequest;
import com.wrapper.spotify.methods.AlbumSearchRequest;
import com.wrapper.spotify.models.Album;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.SimpleAlbum;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by Souleymane on 21/05/2017.
 */
public class NewAlbumRequest extends AbstractRequest {
    public NewAlbumRequest(NewAlbumRequest.Builder builder) {
        super(builder);
    }

    public SettableFuture<Album> getAsync() {
        SettableFuture albumFuture = SettableFuture.create();

        try {
            String jsonString = this.getJson();
            albumFuture.set(JsonUtil.createAlbum(jsonString));
        } catch (Exception var3) {
            albumFuture.setException(var3);
        }

        return albumFuture;
    }

    public Album get() throws IOException, WebApiException {
        String jsonString = this.getJson();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return JsonUtil.createAlbum(jsonString);
    }

    public static NewAlbumRequest.Builder builder() {
        return new NewAlbumRequest.Builder();
    }

    public static final class Builder extends com.wrapper.spotify.methods.AbstractRequest.Builder<NewAlbumRequest.Builder> {
        public Builder() {
        }

        public NewAlbumRequest.Builder id(String id) {
            assert id != null;

            return (NewAlbumRequest.Builder)this.path(String.format("/v1/albums/%s", new Object[]{id}));
        }

        public NewAlbumRequest build() {
            return new NewAlbumRequest(this);
        }
    }

}
