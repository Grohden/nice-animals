package com.grohden.niceanimals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.grohden.niceanimals.helpers.URLList;
import com.grohden.niceanimals.ui.adapters.NAAdapter;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nice_animals_rv)
    RecyclerView mNiceRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureNiceAnimalsRV();

        Picasso.get().setIndicatorsEnabled(true);
    }


    private void configureNiceAnimalsRV() {
        mNiceRecycleView.setHasFixedSize(false);
        mNiceRecycleView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        final List<URL> urlList = URLList.fromStrings(
                "http://cdn.shibe.online/shibes/fc5146267270854843051886b5025795f336a3ea.jpg",
                "http://cdn.shibe.online/shibes/9e527f2cbc3d9f83464c7c6034122975d78a463d.jpg",
                "http://cdn.shibe.online/shibes/cca104dc071718539ffa3bc459040181241c0681.jpg",
                "http://cdn.shibe.online/shibes/26a661c481d8180ef88c47bd0f1ad9ba34c16cff.jpg",
                "http://cdn.shibe.online/shibes/04c1bd58d715b83a2a7ef5b3aa906fb431af2129.jpg",
                "http://cdn.shibe.online/shibes/dec4d04ee2259e45e979375bd404f23d6b795eb5.jpg",
                "http://cdn.shibe.online/shibes/512257c2f6a7f05bb60df6e2d463f008a8bd6dc5.jpg",
                "http://cdn.shibe.online/shibes/067744ad7e77b5fbf88677a9ec16881047a5e832.jpg",
                "http://cdn.shibe.online/shibes/43c4d675f172014e7fbdcb77075b338734d13ea0.jpg",
                "http://cdn.shibe.online/shibes/c5677c5533bd37de1f1d045a27a0f506ba853194.jpg"
        );

        mNiceRecycleView.setAdapter(new NAAdapter(urlList));
    }

}
