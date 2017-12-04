package com.mobileallin.mysongapp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.data.model.AssetsSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AssetsSongsAdapter extends RecyclerView.Adapter<AssetsSongsAdapter.ViewHolder> {
    private final View emptyView;
    private List<AssetsSong> items;
    private IOnItemClickListener itemClickListener;

    public interface IOnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(AssetsSongsAdapter.IOnItemClickListener listener) {
        itemClickListener = listener;
    }

    public AssetsSongsAdapter(View emptyView) {
        this(new ArrayList<>(), emptyView);
    }

    private AssetsSongsAdapter(List<AssetsSong> items, View emptyView) {
        this.items = items;
        this.emptyView = emptyView;
        showEmptyView(items);
    }

    public void setItems(List<AssetsSong> items) {
        this.items = items;
        showEmptyView(items);
        notifyDataSetChanged();
    }

    @Override
    public AssetsSongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_list_item,
                parent, false);
        return new AssetsSongsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssetsSongsAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.song_title)
        TextView songTitleView;
        @BindView(R.id.song_author)
        TextView songAuthorView;
        @BindView(R.id.song_release_date)
        TextView songDateView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bind(AssetsSong song) {
            songTitleView.setText(song.title());
            songAuthorView.setText(song.author());
            songDateView.setText(String.valueOf(song.releaseDate()));
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    private void showEmptyView(List<AssetsSong> list) {
        if (emptyView != null) {
            int visibility = (list == null || list.isEmpty()) ? View.VISIBLE : View.INVISIBLE;
            this.emptyView.setVisibility(visibility);
        }
    }
}
