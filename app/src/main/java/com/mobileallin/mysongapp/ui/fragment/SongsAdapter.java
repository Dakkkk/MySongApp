package com.mobileallin.mysongapp.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.data.model.ItunesSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private Context context;
    private View emptyView;
    private List<ItunesSong> items;
    private IOnItemClickListener itemClickListener;

    public interface IOnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(IOnItemClickListener listener) {
        itemClickListener = listener;
    }

    public SongsAdapter(Context context, View emptyView) {
        this(context, new ArrayList<>(), emptyView);
    }

    public SongsAdapter(Context context, List<ItunesSong> items, View emptyView) {
        this.context = context;
        this.items = items;
        this.emptyView = emptyView;
        showEmptyView(items);
    }

    public void setItems(List<ItunesSong> items) {
        this.items = items;
        showEmptyView(items);
        notifyDataSetChanged();
    }

    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_list_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

        public void bind(ItunesSong itunesSong) {

            songTitleView.setText(itunesSong.title());
            songAuthorView.setText(itunesSong.author());
            songDateView.setText(itunesSong.releaseDate());
        }


        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }


    /**
     * util methods
     */

    private void showEmptyView(List<ItunesSong> list) {
        if (emptyView != null) {
            int visibility = (list == null || list.isEmpty()) ? View.VISIBLE : View.INVISIBLE;
            this.emptyView.setVisibility(visibility);
        }
    }
}
