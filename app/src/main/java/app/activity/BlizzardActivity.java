package app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;
import com.example.rxjavata.app.R;
import javax.inject.Inject;
import app.adapter.CardPetsAdapter;
import app.application.BlizzardApplication;
import app.model.ResponsePets;
import app.service.MountService;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BlizzardActivity extends ActionBarActivity {

    @Inject
    MountService mountService;
    @BindView(R.id.button_clear)
    Button bClear;
    @BindView(R.id.button_fetch)
    Button bFetch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inject Dagger
        resolveDependency();

        //Inject Butterknife
        ButterKnife.bind(this);

         //Set up Android CardView/RecycleView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //final CardMountsAdapter mCardAdapter = new CardMountsAdapter();
        final CardPetsAdapter mCardAdapter = new CardPetsAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

         //Clear
        bClear.setOnClickListener(v -> mCardAdapter.clear());

        //Fetch
        bFetch.setOnClickListener(v -> getSub(mCardAdapter));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getSub(CardPetsAdapter mCardAdapter) {
        subscription = mountService.getPets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCardAdapter::addData);
    }

    private void resolveDependency() {
        //Add Application Components Injection Location
        ((BlizzardApplication) getApplication()).getApiComponent().inject(BlizzardActivity.this);
    }
}
