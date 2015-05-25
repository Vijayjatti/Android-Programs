package com.examp.meetingsms;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetContacts extends Activity implements OnItemClickListener {

	List<String> name1 = new ArrayList<String>();
	List<String> phno1 = new ArrayList<String>();
	MyAdapter ma;
	Button select;
	public static String strSeparator = "|";
	String namestr = "";
	String numstr = "";
	String etitle;
	String evenue;
	String epointer;
	String eDay;
	String emin;
	String eYear;
	String eHour;
	String eMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_contacts);
		etitle = getIntent().getStringExtra("TITLE");
		epointer = getIntent().getStringExtra("POINTER");
		evenue = getIntent().getStringExtra("VENUE");
		eHour = getIntent().getStringExtra("HOUR");
		emin = getIntent().getStringExtra("MIN");
		eYear = getIntent().getStringExtra("YEAR");
		eMonth = getIntent().getStringExtra("MONTH");
		eDay = getIntent().getStringExtra("DAY");

		getAllContacts(this.getContentResolver());
		ListView lv = (ListView) findViewById(R.id.lv);
		ma = new MyAdapter();
		lv.setAdapter(ma);
		lv.setOnItemClickListener(this);
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		// adding
		select = (Button) findViewById(R.id.button1);
		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder checkedcontacts = new StringBuilder();
				System.out.println(".............." + ma.mCheckStates.size());
				String[] Name = new String[name1.size()];
				String[] Phone = new String[phno1.size()];
				for (int i = 0; i < name1.size(); i++)

				{
					if (ma.mCheckStates.get(i) == true) {
						/*
						 * Phone[i]=phno1.get(i).toString();
						 * Name[i]=name1.get(i).toString();
						 * checkedcontacts.append(name1.get(i).toString());
						 * checkedcontacts.append("\n");
						 * 
						 * for (int j = 0;j<Name.length; j++) { namestr =
						 * namestr+Name[j]; // Do not append comma at the end of
						 * last element if(j<namestr.length()-1){ namestr =
						 * namestr+strSeparator;
						 * 
						 * } } System.out.println("Name: "+namestr);
						 */

						namestr += name1.get(i).toString() + strSeparator;
						numstr += phno1.get(i).toString() + strSeparator;
						// Converting array string into string for numbers
						/*
						 * for (int j = 0;j<Phone.length; j++) { numstr =
						 * numstr+Name[j]; // Do not append comma at the end of
						 * last element if(j<numstr.length()-1){ numstr =
						 * numstr+strSeparator; } }
						 */
						System.out.println("Number: " + numstr);

					} else {
						System.out.println("Not Checked......"
								+ name1.get(i).toString());
					}

					// Converting array string into array for name

				}

				Intent intent = new Intent(SetContacts.this,
						MeetingSummary.class);
				/*
				 * intent.putExtra("TITLE", etitle); intent.putExtra("POINTER",
				 * epointer); intent.putExtra("VENUE", evenue);
				 * intent.putExtra("HOUR", eHour); intent.putExtra("MIN", emin);
				 * intent.putExtra("YEAR", eYear); intent.putExtra("MONTH",
				 * eMonth); intent.putExtra("DAY", eDay);
				 */

				intent.putExtra("NAME", namestr);
				intent.putExtra("NUMBER", numstr);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ma.toggle(arg2);
	}

	public void getAllContacts(ContentResolver cr) {

		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			System.out.println(".................." + phoneNumber);
			name1.add(name);
			phno1.add(phoneNumber);
		}

		phones.close();
	}

	class MyAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private SparseBooleanArray mCheckStates;
		LayoutInflater mInflater;
		TextView tv1, tv;
		CheckBox cb;

		MyAdapter() {
			mCheckStates = new SparseBooleanArray(name1.size());
			mInflater = (LayoutInflater) SetContacts.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = mInflater.inflate(R.layout.row, null);
			TextView tv = (TextView) vi.findViewById(R.id.contact_name);
			tv1 = (TextView) vi.findViewById(R.id.phone_number);
			cb = (CheckBox) vi.findViewById(R.id.checkBox_id);
			tv.setText("" + name1.get(position));
			tv1.setText("" + phno1.get(position));
			cb.setTag(position);
			cb.setChecked(mCheckStates.get(position, false));
			cb.setOnCheckedChangeListener(this);

			return vi;
		}

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
			System.out.println("hello...........");
			notifyDataSetChanged();
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			mCheckStates.put((Integer) buttonView.getTag(), isChecked);
		}
	}
}