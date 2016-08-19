package com.malkomich.cliget;

import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.github.malkomich.climet.ClimeT;
import com.github.malkomich.climet.domain.CurrentWeatherData;
import com.github.malkomich.climet.domain.Weather;
import com.github.malkomich.climet.exceptions.CityNotFoundException;

/**
 * Created by malkomich on 19/08/16.
 */
public class GetCurrentWeather extends AsyncTask<String, Integer, CurrentWeatherData> {

    private AppWidgetManager appWidgetManager;
    private RemoteViews views;
    private int appWidgetId;

    public GetCurrentWeather(AppWidgetManager appWidgetManager, RemoteViews views, int appWidgetId) {
        this.appWidgetManager = appWidgetManager;
        this.views = views;
        this.appWidgetId = appWidgetId;
    }

    @Override
    protected CurrentWeatherData doInBackground(String... params) {

        String city = params[0];
        CurrentWeatherData data = null;
        try {
            data = ClimeT.getCurrentWeather(city);
        } catch (CityNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(CurrentWeatherData data) {

        Weather weather = data.getWeather();

        String cityName = data.getCity().getName();

        String text = weather.getTemp().getCurrentTemp(Weather.CELSIUS) + "º";

        views.setTextViewText(R.id.appwidget_text, text);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
