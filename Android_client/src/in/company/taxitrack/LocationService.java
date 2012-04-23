package in.company.taxitrack;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class LocationService extends Service{
	private String phoneNumber;
	private String userName;
	private String vendorName;
	private LocationManager locationManager;
	private ParseObject data;
	LocationListener listener;
	@Override
	public void onCreate() {
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 * The timeout value is hardcoded here. Change the value to alter the frequency of GPS activation.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
			userName = intent.getExtras().getString("username");
			phoneNumber = intent.getExtras().getString("phonenumber");
			vendorName = intent.getExtras().getString("vendorname");
		try{
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			listener = new MyLocationListener();
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0, listener);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}


	/*
	 * Location listener implementation, upload data to parse every time the listener is invoked.
	 */
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			try {
				//TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				//if(phoneNumber == null) {
				//	phoneNumber = tManager.getDeviceId();
				//}
				data = new ParseObject("taxilocations");
				ParseGeoPoint geoPoint = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
				data.put("ID", phoneNumber);
				data.put("vendor", vendorName);
				data.put("locations", geoPoint);
				data.put("speed", location.getSpeed());
				data.saveInBackground();

				/*
				HttpClient client = new DefaultHttpClient();
				JSONObject jId = new JSONObject();
				String url = "http://ec2-122-248-211-48.ap-southeast-1.compute.amazonaws.com:8888/" + "?id=" + phoneNumber;
				HttpPost post = new HttpPost(url);
				JSONObject jLocation = new JSONObject();
				jLocation.put("Latitude",location.getLatitude());
				jLocation.put("Longitude", location.getLongitude());
				jId.put("ID",phoneNumber);
				jId.put("LOCATION",jLocation);
				StringEntity se = new StringEntity(jId.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				post.setEntity(se);
				HttpResponse response = client.execute(post);
				if (response != null) {
					InputStream in = response.getEntity().getContent();
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub


		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
