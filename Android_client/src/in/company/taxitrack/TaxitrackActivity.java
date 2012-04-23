package in.company.taxitrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;

public class TaxitrackActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Parse.initialize(this, "3EN6GtbpYtprWOJyqHNaPjXrJixp66F2qTQVOS30", "Mr6GL62FERH5l3NX5FEhewjxuCeRO79RiYC6uRc8");
        
    }
    public void startLocationTracing(View view) {
    	String userName = ((EditText)findViewById(R.id.editText1)).getText().toString();
    	String phoneNumber = ((EditText)findViewById(R.id.editText2)).getText().toString();
    	String vendorName = ((EditText)findViewById(R.id.vendorText)).getText().toString();
    	DBHelper storeDb = new DBHelper(this.getApplicationContext());
    	storeDb.insertData(userName, vendorName, phoneNumber);
    	Intent intent = new Intent(this, LocationService.class);
    	intent.putExtra("username", userName);
    	intent.putExtra("phonenumber", phoneNumber);
    	intent.putExtra("vendorname", vendorName);
    	startService(intent);
    	this.finish();
    	
    }
    
    private void saveDataToDB(String username, String phoneNumber) {
    	
    }

}