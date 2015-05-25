package com.notes.mnotes;

import java.io.File;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
// Author : Vijay : Map Implementation
// UI : Kenneth
public class PhotoNotesSave extends Activity {

	EditText description;
	EditText title;
	ImageView picture;
	Bitmap bitimage;
	String name;
	String description1;
	String file;
	int flag = 0;
	private static final int REQUEST_TAKE_PHOTO = 0;
	double lat;
	double lon;
	String latstring;
	String lonstring;
	
	String addressString;

	// Referred class slides and sample code
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_notes_save);
		description = (EditText) findViewById(R.id.description);
		title = (EditText) findViewById(R.id.title);
		picture = (ImageView) findViewById(R.id.imageView1);
		
		//-----MAP======//
		LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(context);

        Criteria crta = new Criteria();
        crta.setAccuracy(Criteria.ACCURACY_FINE);
        crta.setAltitudeRequired(false);
        crta.setBearingRequired(false);
        crta.setCostAllowed(true);
        crta.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(crta, true);

        // String provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(location);

        locationManager.requestLocationUpdates(provider, 1000, 0,
                locationListener);
		
		//-----MAP======//

		// File directory creation
		File storageDir = new File(
				Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_PICTURES),
				"PicPack");
		storageDir.mkdir();

		// create file path for photo to be used
		file = storageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
		
		
	}

	// file Parameter passing to save pictures
	public void takepicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(file)));
		startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	}

	// save to DB
	public void savetodb() {
		// check for picture
		if (flag == 0) {
			Toast.makeText(getApplicationContext(), "NO PICTURE ADDED!!",
					Toast.LENGTH_LONG).show();
		}

		// check for text
		description1 = description.getText().toString();
		name = title.getText().toString();
		if (name.length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Enter Title!")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {/*do nothing*/}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}

		// Check if photo is taken and title field is not empty then save
		if (flag != 0 && name.length() != 0) {
			SQLiteDatabase data = new PhotoDbHelper(this).getWritableDatabase();
			ContentValues newValues = new ContentValues();
			newValues.put(PhotoDbHelper.TITLE_COLUMN, name);
			newValues.put(PhotoDbHelper.MAP_LAT, latstring);
			newValues.put(PhotoDbHelper.MAP_LONG, lonstring);
			newValues.put(PhotoDbHelper.MAP_ADD,addressString);
			newValues.put(PhotoDbHelper.CAPTION_COLUMN, description1);
			newValues.put(PhotoDbHelper.FILE_PATH_COLUMN, file);
			data.insert(PhotoDbHelper.DATABASE_TABLE, null, newValues);
			// return to previous activity
			finish();
		}
	}

	
	
	//====MAP=====//

	private final LocationListener locationListener = new LocationListener()
    {

        @Override
        public void onLocationChanged(Location location)
        {
            updateWithNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            updateWithNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
        }

    };

    private void updateWithNewLocation(Location location)
    {
        String latLong;
        

        addressString = "no address found";

        if (location != null)
        {
             lat = location.getLatitude();
             lon = location.getLongitude();
            latLong = "Lat:" + lat + "\nLong:" + lon;

            double lattitude = location.getLatitude();
            double longitude = location.getLongitude();
            latstring=String.valueOf(lat);
            lonstring=String.valueOf(lon);

            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try
            {
                List<Address> addresses = gc.getFromLocation(lattitude,
                        longitude, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0)
                {
                    Address address = (Address) addresses.get(0);
                    sb.append(address.getAddressLine(0)).append("\n");
                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                }
                addressString = sb.toString();
            } catch (Exception e)
            {
            }
        } else
        {
            latLong = " NO Location Found ";
        }
    }
	
	
	//====MAP====//
	// Inflate the menu, this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}
	// Menu item manager
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.TP:
			// launches the camera intent
			takepicture();
			return true;
		case R.id.SA:
			// launches the save function
			savetodb();
			return true;
		default:
			// default code for the default case
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Showing the picture and converting it into bitmap
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 10;
		bitimage = BitmapFactory.decodeFile(file, options);
		picture.setImageBitmap(bitimage);
		flag = 1;
	}
}
