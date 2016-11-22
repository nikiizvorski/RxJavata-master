package app.dependencies;

import java.util.Observable;

import javax.inject.Singleton;

import app.model.Response;
import app.service.BlizzardService;
import app.service.MountAPI;
import app.service.MountService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@Module
public class ApiModule {

    @Provides
    @CustomScope
    MountService provideMountApiService(Retrofit retrofit){
        return retrofit.create(MountService.class);
    }
}
