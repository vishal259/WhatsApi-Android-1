package nl.giovanniterlingen.whatsapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Android adaptation from the PHP WhatsAPI by WHAnonymous {@link https
 * ://github.com/WHAnonymous/Chat-API/}
 * 
 * @author Giovanni Terlingen
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "msgstore.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE messages (`from` TEXT, `to` TEXT, message TEXT, id TEXT, t TEXT);");
	}

	public List<String> getContacts(SQLiteDatabase db) {
		List<String> List = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT DISTINCT `from` FROM messages DESC";
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				List.add(cursor.getString(0));
				List.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		return List;
	}
	
	public List<String> getMessages(SQLiteDatabase db, String number) {
		List<String> List = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT `from`,`to`, message FROM messages WHERE `from` LIKE '%" + number + "%' OR `to` LIKE '%" + number + "%'";
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				List.add(cursor.getString(0)+": "+cursor.getString(2));
			} while (cursor.moveToNext());
		}
		return List;
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}