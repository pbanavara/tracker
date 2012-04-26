/**
 * 
 */
package in.company.taxitrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author pradeep
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME="TAXIGUIDE";
	private static final String TABLE_NAME = "USERS";
	public static final String COLUMN_ID="_id";
	private static final String USER_COLUMN = "DRIVER_NAME";
	private static final String VENDOR_COLUMN = "VENDOR_NAME";
	private static final String PHONE_COLUMN = "PHONE_NAME";
	private static final String LATITUDE_COLUMN = "LATITUDE";
	private static final String LONGITUDE_COLUMN = "LONGITUDE";
	private static final String SPEED_COLUMN = "SPEED";
	private static final String TIMESTAMP_COLUMN = "TIMESTAMP";
	 
	private static final int DB_VERSION=1;
	private static final String dbCreate = "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ USER_COLUMN + " text not null, " + VENDOR_COLUMN + " text not null, " + PHONE_COLUMN + " text not null," + LATITUDE_COLUMN
					+ " real not null," + LONGITUDE_COLUMN + " real not null," + SPEED_COLUMN + " real not null, "+ TIMESTAMP_COLUMN + " text not null);";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		try {
			database.beginTransaction();
			database.execSQL(dbCreate);
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
		}
	}
	
	public void insertData(String userName, String vendorName, String phoneNumber, double latitude, double longitude, float speed, String timestamp) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(USER_COLUMN, userName);
		cv.put(VENDOR_COLUMN, vendorName);
		cv.put(PHONE_COLUMN, phoneNumber);
		cv.put(LATITUDE_COLUMN, latitude);
		cv.put(LONGITUDE_COLUMN, longitude);
		cv.put(SPEED_COLUMN, speed);
		cv.put(TIMESTAMP_COLUMN, timestamp);
		db.insert(TABLE_NAME, COLUMN_ID, cv);
		System.out.println("Insert successful");
		db.close();
	}
	
	public Cursor getData() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "Select * from + " + TABLE_NAME + ";";
		Cursor data = db.rawQuery(sql, null);
		db.close();
		return data;
	}
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	

}
