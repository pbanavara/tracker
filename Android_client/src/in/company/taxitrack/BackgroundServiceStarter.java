package in.company.taxitrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class BackgroundServiceStarter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// TODO Add code to start the LocationService with the required arguments in the intent.Intentioanlly not done.
		DBHelper helper = new DBHelper(context);
		Cursor data = helper.getData();
		Intent locationIntent = new Intent();

	}

}
