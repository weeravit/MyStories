package io.github.w33vit.mystories;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import io.github.w33vit.mystories.dao.DaoMaster;
import io.github.w33vit.mystories.dao.DaoProvider;
import io.github.w33vit.mystories.utils.Theme;

/**
 * Created by Weeravit on 28/10/2558.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        setupTheme();
    }

    private void setupDatabase() {
        String databaseName = "MyStories.db";
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, databaseName, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoProvider.getInstance().setDaoSession(daoMaster.newSession());
    }

    private void setupTheme() {
        Theme.getInstance().setupContext(this);
        Theme.getInstance().setupSharedPreferences();
    }

}
