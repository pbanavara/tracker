package in.company.taxitrack;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class LocationService extends Service{
	private LocationManager locationManager;
	
	private LocationListener listener;
	private String userName;
	private String phoneNumber;
	private String vendorName;
	//private DBHelper helper;
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
			this.userName = intent.getExtras().getString("username");
			this.phoneNumber = intent.getExtras().getString("phonenumber");
			this.vendorName = intent.getExtras().getString("vendorname");
		try{
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			//helper = new DBHelper(getApplicationContext());
			listener = new MyLocationListener();
			if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0, listener);
			} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 0, listener);
			}
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
			ParseObject data;
			// TODO Auto-generated method stub
			try {
				//Save data to database as well
				
				data = new ParseObject("taxilocations");
				ParseGeoPoint geoPoint = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
				data.put("ID", phoneNumber);
				data.put("name", userName);
				data.put("vendor", vendorName);
				data.put("locations", geoPoint);
				data.put("speed", location.getSpeed());
				data.put("accuracy", location.getAccuracy());
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
			if(provider.equals(LocationManager.GPS_PROVIDER)) {
				System.out.println("Unable to get a hold of GPS");
				
			}

		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
