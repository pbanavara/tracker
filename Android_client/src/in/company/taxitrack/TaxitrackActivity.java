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
        Parse.initialize(this, "J3Q5NuBIcYNTeHFmMy3SzNQL2Mi9UKKG5Vga0H9O", "R16qeeoQB3DaILRvD7U8qhMk4nAmqCUW3bxmaE7u");
        
    }
    public void startLocationTracing(View view) {
    	String userName = ((EditText)findViewById(R.id.editText1)).getText().toString();
    	String phoneNumber = ((EditText)findViewById(R.id.editText2)).getText().toString();
    	String vendorName = ((EditText)findViewById(R.id.vendorText)).getText().toString();
    	Intent intent = new Intent(this, LocationService.class);
    	//Intent intent = new Intent(this, LocationActivity.class);
    	intent.putExtra("username", userName);
    	intent.putExtra("phonenumber", phoneNumber);
    	intent.putExtra("vendorname", vendorName);
    	startService(intent);
    	//startActivity(intent);
    	this.finish();
    }

}