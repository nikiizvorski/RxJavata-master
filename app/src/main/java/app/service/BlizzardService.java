package app.service;

import app.model.Blizzard;

import retrofit2.http.GET;
import rx.Observable;

public interface BlizzardService {
    String SERVICE_ENDPOINT = "https://eu.api.battle.net/wow/mount/";

    @GET("?locale=en_GB&apikey=amd7ujwtgawpy97kswhhk38xsxtbdn2m")
    Observable<Blizzard> getMounts();
}
