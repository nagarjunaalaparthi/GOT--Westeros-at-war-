package com.westerosatwar.got;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by Arjun.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"android:src"})
    public static void setImage(ImageView image, int resource) {
        image.setImageResource(resource);
    }


}
