package app.application;

import android.app.Application;
import app.dependencies.ApiComponent;
import app.dependencies.DaggerApiComponent;
import app.dependencies.DaggerNetworkComponent;
import app.dependencies.NetworkComponent;
import app.dependencies.NetworkModule;
import app.service.MountService;

public class BlizzardApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        resolveDependency();
        super.onCreate();
    }

    private void resolveDependency() {
        mApiComponent = DaggerApiComponent.builder()
                .networkComponent(getNetworkComponent())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(MountService.BASE_URL))
                .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
