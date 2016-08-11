package uk.co.senab.photoview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by css on 8/11/16.
 */
public class PicassoSamplePopUpActivity extends AppCompatActivity{

    private static final String TAG = "POPUP-ACTIVITY";

    FrameLayout container;
    ImageView imageView;
    PhotoView photoView;
    private Boolean isPopUpVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_popup);

        container = (FrameLayout) findViewById(R.id.fl_container);

        imageView = (ImageView) findViewById(R.id.iv_image);
        Picasso.with(this)
                .load("http://pbs.twimg.com/media/Bist9mvIYAAeAyQ.jpg")
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.setVisibility(View.VISIBLE);
                displayPopUp();
                isPopUpVisible = true;
            }
        });

    }

    private void displayPopUp() {
        photoView = (PhotoView) findViewById(R.id.iv_photo);
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        Picasso.with(this)
                .load("http://pbs.twimg.com/media/Bist9mvIYAAeAyQ.jpg")
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Log.i(TAG, "onPhoto Tap ");
                dismissPopUp();
            }

            @Override
            public void onOutsidePhotoTap() {
                Log.i(TAG, "onOutside Tap ");
                dismissPopUp();
            }

        });

    }

    @Override
    public void onBackPressed() {
        if (isPopUpVisible) {
            dismissPopUp();
        } else {
            super.onBackPressed();
        }
    }

    private void dismissPopUp() {
        photoView.setImageDrawable(null);
        container.setVisibility(View.GONE);
        isPopUpVisible = false;
    }

}
