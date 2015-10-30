package io.github.w33vit.mystories.dao;

/**
 * Created by Weeravit on 28/10/2558.
 */
public class DaoProvider {

    private static DaoProvider daoProvider;
    private DaoSession daoSession;

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        if (daoProvider == null) {
            daoProvider = new DaoProvider();
        }
        return daoProvider;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

}
