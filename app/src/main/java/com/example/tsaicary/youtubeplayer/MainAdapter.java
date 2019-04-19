package com.example.tsaicary.youtubeplayer;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tsaicary.youtubeplayer.model.ResourceId;
import com.example.tsaicary.youtubeplayer.model.YouTubeData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tsaicary on 2018/3/29.
 */

public
class MainAdapter extends RecyclerView.Adapter<MainAdapter.ListHolder> {
    private YouTubeData mYouTubeData;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public
    MainAdapter(Context context, YouTubeData youTubeData) {
        this.mContext = context;
        this.mYouTubeData = youTubeData;
    }

    public
    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public
    ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemyoutubelist, parent, false);
        return new ListHolder(itemView);
    }

    @Override
    public
    void onBindViewHolder(ListHolder holder, final int position) {

        holder.mTitle.setText(mYouTubeData.getItems().get(position).getSnippet().getTitle());
        String publishedAt = mYouTubeData.getItems().get(position).getSnippet().getPublishedAt();

        publishedAt = publishedAt.replace("T", "");
        publishedAt = publishedAt.substring(0, publishedAt.length() - 13)+"\n"+"主講人：何頻";

        Log.d("publishedAt",publishedAt);

        holder.mPublishedAt.setText(publishedAt);
        holder.mChannelTitle.setText(mYouTubeData.getItems().get(position).getSnippet().getChannelTitle());
        Picasso.with(mContext).load(mYouTubeData.getItems().get(position).getSnippet().getThumbnails().getMedium().getUrl())
                .fit()
                .centerCrop()
                .into(holder.mDefault);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return mYouTubeData.getItems().size();
    }

    public
    class ListHolder extends RecyclerView.ViewHolder {
        private ImageView mDefault;
        private TextView mPublishedAt, mTitle,mChannelTitle;

        public
        ListHolder(View itemView) {
            super(itemView);


            mDefault = itemView.findViewById(R.id.default_imageview);
            mPublishedAt = itemView.findViewById(R.id.publishedAt_textview);
            mTitle = itemView.findViewById(R.id.title_textview);
            mChannelTitle = itemView.findViewById(R.id.channelTitle_textview);

        }


    }

    public
    interface OnItemClickListener {
        void onItemClick(int position);


    }

    private OnItemClickListener onItemClickListener = null;
}
