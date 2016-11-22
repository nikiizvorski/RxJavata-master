package app.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.rxjavata.app.R;

import javax.inject.Inject;

import app.Data;
import app.adapter.CardMountsAdapter;
import app.adapter.CardPetsAdapter;
import app.application.BlizzardApplication;
import app.model.Response;
import app.model.ResponsePets;
import app.service.MountService;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;


public class BlizzardActivity extends ActionBarActivity {

    @Inject
    MountService mountService;
    @BindView(R.id.button_clear)
    Button bClear;
    @BindView(R.id.button_fetch)
    Button bFetch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolveDependency();

        //Bind all components
        ButterKnife.bind(this);

         //Set up Android CardView/RecycleView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //final CardMountsAdapter mCardAdapter = new CardMountsAdapter();
        final CardPetsAdapter mCardAdapter = new CardPetsAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

         //START: button set up
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mountService.getPets()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponsePets>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponsePets responsePets) {
                                mCardAdapter.addData(responsePets);
                            }
                        });
                }
        });
    }

    private void resolveDependency() {
        //Add Application Components Injection Location
        ((BlizzardApplication) getApplication()).getApiComponent().inject(BlizzardActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
