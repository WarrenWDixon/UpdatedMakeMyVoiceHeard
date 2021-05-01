package com.example.android.makemyvoiceheard;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class MakeMyVoiceHeardWidget extends AppWidgetProvider {
    static OfficialsViewModel mViewModel;
    static String senator1Name;
    static String senator2Name;
    static String representativeName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //mViewModel = ViewModelProviders.of((FragmentActivity) context).get(OfficialsViewModel.class);
        /* mViewModel.getAllMovies().observe((FragmentActivity) context, new Observer<List<Officials>>() {
                    @Override
                    public void onChanged(List<Officials> officialsList) {
                        int numOfficials = officialsList.size();
                        int i;
                        //JsonUtil.updateFavoriteMovies(movies);
                        senator1Name = officialsList.get(0).getSenator1Name();
                        senator2Name = officialsList.get(0).getSenator2Name();
                        representativeName = officialsList.get(0).getRepresentativeName();
                        //Log.d("WWD", " ------------------ widget logic senator1Name length is " + senator1Name.length());
                    }
                }); */
        //senator1Name = JsonUtil.getSenator1Name();

        //CharSequence widgetText1 = senator1Name;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.make_my_voice_heard_widget);
        //views.setTextViewText(R.id.widget_representative, widgetText1);

        //CharSequence widgetText2 = JsonUtil.getSenator2Name();
        //views.setTextViewText(R.id.widget_senator1, widgetText2);

        //CharSequence widgetText3 = JsonUtil.getRepresentativeName();
       // views.setTextViewText(R.id.widget_senator2, widgetText3);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}