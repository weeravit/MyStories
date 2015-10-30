package io.github.w33vit.mystories.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.SwipeableRecyclerViewTouchListener;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.parceler.Parcels;

import java.util.List;

import io.github.w33vit.mystories.MainApplication;
import io.github.w33vit.mystories.R;
import io.github.w33vit.mystories.adapter.StoriesAdapter;
import io.github.w33vit.mystories.dao.Stories;
import io.github.w33vit.mystories.dao.StoriesDao;
import io.github.w33vit.mystories.dao.DaoProvider;
import io.github.w33vit.mystories.utils.Theme;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    UltimateRecyclerView recyclerView;
    StoriesAdapter storiesAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void setupTheme() {
        Theme.getInstance().setupTheme(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStories();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (UltimateRecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(recyclerView.mRecyclerView, recyclerViewOnItemClickListener);
        SwipeableRecyclerViewTouchListener swipeableRecyclerViewTouchListener = new SwipeableRecyclerViewTouchListener(recyclerView.mRecyclerView, swipeListener);

        recyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);
        recyclerView.addOnItemTouchListener(swipeableRecyclerViewTouchListener);
        fab.setOnClickListener(onClickListener);
    }

    private void loadStories() {
        StoriesDao storiesDao = DaoProvider.getInstance().getDaoSession().getStoriesDao();
        List<Stories> storiesList = storiesDao.queryBuilder().orderDesc(StoriesDao.Properties.Stories_date).list();
        if (storiesAdapter == null) {
            storiesAdapter = new StoriesAdapter(storiesList, R.layout.item_stories, R.layout.item_stories_header);
            recyclerView.setAdapter(storiesAdapter);

            //Set Header
            StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(storiesAdapter);
            recyclerView.addItemDecoration(headersDecor);
        } else {
            storiesAdapter.setStories(storiesList);
            storiesAdapter.notifyDataSetChanged();
        }
    }

    private void removeStories(int position) {
        Stories story = storiesAdapter.getStories().get(position); // temp
        storiesAdapter.getStories().remove(position); // delete in list
        storiesAdapter.notifyDataSetChanged();

        StoriesDao storiesDao = DaoProvider.getInstance().getDaoSession().getStoriesDao();
        storiesDao.delete(story);
    }

    ItemTouchListenerAdapter.RecyclerViewOnItemClickListener recyclerViewOnItemClickListener = new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
        @Override
        public void onItemClick(RecyclerView parent, View clickedView, int position) {
            Stories story = storiesAdapter.getStories().get(position);
            Parcelable parcelable = Parcels.wrap(story);
            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("update", true);
            intent.putExtra("story", parcelable);
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

        }
    };

    SwipeableRecyclerViewTouchListener.SwipeListener swipeListener = new SwipeableRecyclerViewTouchListener.SwipeListener() {
        @Override
        public boolean canSwipe(int position) {
            return true;
        }

        @Override
        public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                removeStories(position);
            }
        }

        @Override
        public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                removeStories(position);
            }
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("add", true);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_theme:
                Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
