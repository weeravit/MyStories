package io.github.w33vit.mystories.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import io.github.w33vit.mystories.R;

/**
 * Created by Weeravit on 30/10/2558.
 */
public class Theme {

    private static Theme theme;
    private Context context;
    private SharedPreferences sharedPreferences;

    public static final int PINK = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;

    private int[] resTheme = {
            R.style.AppTheme_Pink,
            R.style.AppTheme_Green,
            R.style.AppTheme_Blue
    };

    private final int DEFAULT = 0;
    private final String RES_THEME_STYLE = "theme";

    private Theme() {
    }

    public static Theme getInstance() {
        if (theme == null)
            theme = new Theme();
        return theme;
    }

    public void setupContext(Context context) {
        this.context = context;
    }

    public void setupSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setupTheme(Activity activity) {
        activity.setTheme(getThemeStyle());
    }

    public void settingTheme(int theme) {
        setThemeStyle(theme);
    }

    public int getCurrentTheme() {
        int theme = getThemeStyle();
        if (theme == resTheme[PINK]) {
            return PINK;
        } else if (theme == resTheme[GREEN]) {
            return GREEN;
        } else {
            return BLUE;
        }
    }

    private int getThemeStyle() {
        int resStyle = sharedPreferences.getInt(RES_THEME_STYLE, resTheme[DEFAULT]);
        return resStyle;
    }

    private void setThemeStyle(int theme) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(RES_THEME_STYLE, resTheme[theme]);
        editor.commit();
    }

}
