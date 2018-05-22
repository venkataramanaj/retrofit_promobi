package com.example.retofit_promobi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.retofit_promobi.R;
import com.example.retofit_promobi.pojo.Data;
import com.example.retofit_promobi.pojo.Result;
import com.example.retofit_promobi.utils.ImageFetch;

import java.util.List;

/**
 * Created by Ramana on 5/20/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    List<Result> result;
    List<Data> data;
    Context context;
    int imageItem_height_calculation = 0;
    float imageheight = 0;
    String from;

    public DataAdapter(String from, List<Result> result, List<Data> data, Context context) {
        this.result = result;
        this.context = context;
        this.from = from;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView images;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            images = view.findViewById(R.id.images);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }

    public static int dpToPx(int dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if (imageItem_height_calculation == 0) {
            imageItem_height_calculation = 1;
            holder.images.getLayoutParams().height = (int) ((float) ((context
                    .getResources().getDisplayMetrics().widthPixels - dpToPx(10, context)) / 2) / 1.33);
            imageheight = (int) ((float) ((context
                    .getResources().getDisplayMetrics().widthPixels - dpToPx(10, context)) / 2) / 1.33);
        } else {
            holder.images.getLayoutParams().height = (int) imageheight;
        }


        if (from.equals("next")) {
            holder.title.setText(result.get(position).getTitle());
            if (result.get(position).getMultimedia().size() > 0) {
                ImageFetch.getInstance().loadImage(context, holder.images, result.get(position).getMultimedia().get(4).getUrl());
            } else {
                holder.images.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            holder.title.setText(data.get(position).getTitle());

            ImageFetch.getInstance().loadImage(context, holder.images, data.get(position).getImage());

        }
//        holder.images.addView(image);

     /*   for (int i = 0; i < result.get(position).getMultimedia().size(); i++) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setScaleType(ImageView.ScaleType.FIT_XY);

//            image.setMaxHeight(100);
//            image.setMaxWidth(20);
            ImageFetch.getInstance().loadImage(context, image, result.get(position).getMultimedia().get(i).getUrl());
            holder.images.addView(image);
        }*/

    }

    @Override
    public int getItemCount() {
        if (from.equals("next"))
            return result.size();
        else
            return data.size();
    }
}
