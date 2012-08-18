package in.jmkl.dcsms.frame.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Widget extends AppWidgetProvider {
public static final String WIDGETTAG = "DCSMSLOG";
private RemoteViews views;
private  int appWidgetId;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
	    Log.i(WIDGETTAG, "onUpdate");
		
		final int N = appWidgetIds.length;
		
		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i=0; i<N; i++) {
		    appWidgetId = appWidgetIds[i];
		    		    
		    Log.i(WIDGETTAG, "updating widget[id] " + appWidgetId);

		    views = new RemoteViews(context.getPackageName(), R.layout.widgetcustom);
		    
		    Intent intent = new Intent(context, Info.class);
		    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		    views.setOnClickPendingIntent(R.id.img, pendingIntent);
		    Log.i(WIDGETTAG, "pending intent set");
		    updateAppWidget(context, appWidgetManager, appWidgetId);
		    
		    // Tell the AppWidgetManager to perform an update on the current App Widget
		    appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	public static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int mAppWidgetId) {
		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widgetcustom);
		updateViews.setImageViewResource(R.id.img, R.drawable.defaultimage);
		appWidgetManager.updateAppWidget(mAppWidgetId, updateViews);
		
		Toast.makeText(context, "updateAppWidget(): " + String.valueOf(mAppWidgetId) + "\n", Toast.LENGTH_LONG).show();
		
	}


}