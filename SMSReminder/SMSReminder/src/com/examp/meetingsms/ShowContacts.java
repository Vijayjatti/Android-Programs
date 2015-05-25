package com.examp.meetingsms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.examp.meetingsms.Helper.DataSourceDAO;
import com.examp.meetingsms.Helper.DbModel;

public class ShowContacts extends Activity {

	List<String> namelist = new ArrayList<String>();
	List<String> numberlist = new ArrayList<String>();
	MyAdapter myadapter;
	String[] name;
	String[] numbers;
	String myId;
	public static String strSeparator = "\\|";
	DataSourceDAO todb;
	DbModel elementmodel = new DbModel();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_contacts);
		DataSourceDAO todb = new DataSourceDAO(getApplicationContext());
		myId = getIntent().getStringExtra("ID");
		DbModel elementmodel = new DbModel();
		todb.open();
		elementmodel = todb.selectrow(myId);
		todb.close();
		String namestring = elementmodel.getNamestr();
		String numstring = elementmodel.getNumstr();
		System.out.println(namestring);
		System.out.println(numstring);
		name = namestring.split(strSeparator);
		numbers = numstring.split(strSeparator);

		for (int i = 0; i < name.length; ++i) {
			System.out.println(name[i]);
			namelist.add(name[i]);
		}
			
		for (int j = 0; j < numbers.length; ++j) {
			numberlist.add(numbers[j]);
		}
		ListView lv = (ListView) findViewById(R.id.listView1);
		myadapter = new MyAdapter();
		lv.setAdapter(myadapter);

	}

	class MyAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		LayoutInflater mInflater;
		TextView tv1, tv;

		// CheckBox cb;
		MyAdapter() {
			// mCheckStates = new SparseBooleanArray(name.size());
			mInflater = (LayoutInflater) ShowContacts.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return namelist.size();
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
				vi = mInflater.inflate(R.layout.selected_row, null);
			TextView tv = (TextView) vi.findViewById(R.id.contact_name);
			tv1 = (TextView) vi.findViewById(R.id.phone_number);
			System.out.println(namelist.get(position));
			Collections.sort(namelist);
			System.out.println("Sorted name list:"+namelist);
			tv.setText(namelist.get(position));
			tv1.setText(numberlist.get(position));
			
			return vi;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

		}
	}
}
