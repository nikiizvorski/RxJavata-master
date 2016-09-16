package app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.rxjavata.app.R;

import app.adapter.CardMountsAdapter;
import app.service.MountAPI;
import app.model.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BlizzardActivity extends ActionBarActivity {
    @BindView(R.id.button_clear)
    Button bClear;
    @BindView(R.id.button_fetch)
    Button bFetch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind all components
        ButterKnife.bind(this);

         //Set up Android CardView/RecycleView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CardMountsAdapter mCardAdapter = new CardMountsAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

         //START: button set up
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MountAPI.Factory.getIstance().getMounts()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Response>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("GithubDemo", e.getMessage());
                            }

                            @Override
                            public void onNext(Response response) {
                                mCardAdapter.addData(response);
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
