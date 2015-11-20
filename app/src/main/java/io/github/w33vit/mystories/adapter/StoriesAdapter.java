package io.github.w33vit.mystories.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import io.github.w33vit.mystories.R;
import io.github.w33vit.mystories.dao.Stories;

/**
 * Created by Weeravit on 28/10/2558.
 */
public class StoriesAdapter extends UltimateViewAdapter<StoriesAdapter.MyHolder> {

    private List<Stories> stories;
    private int resLayout;
    private int headerResLayout;

    public StoriesAdapter(List<Stories> stories, int resLayout, int headerResLayout) {
        this.stories = stories;
        this.resLayout = resLayout;
        this.headerResLayout = headerResLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(headerResLayout, parent, false);
        HeaderHolder headerHolder = new HeaderHolder(view);
        return headerHolder;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        Stories story = stories.get(position);
        HeaderHolder headerHolder = (HeaderHolder) holder;
        headerHolder.date.setText(story.getRealDate());
    }

    @Override
    public long generateHeaderId(int position) {
        String date = stories.get(position).getRealDate();
        long sum = 0;
        for (int i = 0; i < date.length(); i++) {
            sum += date.charAt(i);
        }
        return sum;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resLayout, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Stories storie = stories.get(position);
        holder.title.setText(storie.getTitle());
        holder.content.setText(storie.getContent());
    }

    @Override
    public int getAdapterItemCount() {
        return stories.size();
    }

    // LoadMore
    @Override
    public MyHolder getViewHolder(View view) {
        return null;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

    class MyHolder extends UltimateRecyclerviewViewHolder {

        FrameLayout flTitle;
        TextView title;
        TextView content;

        public MyHolder(View itemView) {
            super(itemView);
            flTitle = (FrameLayout) itemView.findViewById(R.id.flTitle);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }

    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        TextView date;

        public HeaderHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvStoriesDate);
        }

    }

}
