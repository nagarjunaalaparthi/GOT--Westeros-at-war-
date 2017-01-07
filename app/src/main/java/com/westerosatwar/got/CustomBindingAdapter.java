package com.westerosatwar.got;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by Arjun on 1/8/2017.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"android:src"})
    public static void setImage(ImageView image, int resource) {
        image.setImageResource(resource);
    }


}
