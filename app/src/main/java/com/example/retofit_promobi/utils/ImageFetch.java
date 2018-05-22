package com.example.retofit_promobi.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.retofit_promobi.R;

/**
 * Created by Ramana on 5/20/2018.
 */

public class ImageFetch {

    public static ImageFetch imageFetch;

    public static ImageFetch getInstance(){
        if(null==imageFetch){
            imageFetch = new ImageFetch();
        }

        return imageFetch;
    }
    public  void loadImage(Context context, ImageView view, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(view);
    }
}
