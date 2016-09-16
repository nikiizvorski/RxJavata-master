package app.service;

import app.model.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by nikiizvorski on 1/31/2016.
 */
public interface MountAPI {
    String BASE_URL = "https://eu.api.battle.net/wow/mount/";

    @GET("?locale=en_GB&apikey=amd7ujwtgawpy97kswhhk38xsxtbdn2m")
    Observable<Response> getMounts();

    class Factory {
        private static MountAPI service;

        public static MountAPI getIstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL).build();
                service = retrofit.create(MountAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }
}
