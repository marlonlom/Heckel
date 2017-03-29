package com.github.marlonlom.heckel.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.marlonlom.heckel.Heckel;

/**
 * Main activity for displaying Heckel library demo.
 *
 * @author marlonlom
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demostrateImageLoading();
    }

    private void demostrateImageLoading() {
        final ImageView mDemoImageView = (ImageView) findViewById(R.id.imageview_demo);

        Heckel.load(getString(R.string.example_image_url))
                .into(mDemoImageView);
    }
}
