package app.dependencies;

import app.activity.BlizzardActivity;
import app.activity.MainActivity;
import dagger.Component;

@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {
    void inject(BlizzardActivity blizzardActivity);
}
