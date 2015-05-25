package com.examp.meetingsms;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.examp.meetingsms.Helper.DataSourceDAO;
import com.examp.meetingsms.Helper.DbModel;

public class MainlistviewAvtivity extends Activity {

	DataSourceDAO todb;
	DbModel elementmodel = new DbModel();
	Long lid;
	public static String strSeparator = "\\|";
	List<String> namelist = new ArrayList<String>();
	List<String> numberlist = new ArrayList<String>();
	MyAdapter myadapter;
	
	
	String[] numbers;
	String[] name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainl_istview_summary);
		ListView lv = (ListView) findViewById(R.id.listView1);
	
		DataSourceDAO todb = new DataSourceDAO(getApplicationContext());
		lid=getIntent().getLongExtra("position",0);
		System.out.println(""+lid.toString());
		
		todb.open();
		DbModel dbmodel;
		dbmodel=todb.getelementbyid(lid.toString());
		
		todb.close();
	
		TextView title=(TextView)findViewById(R.id.user_title);
        TextView venue=(TextView)findViewById(R.id.user_venue);
        TextView pointer=(TextView)findViewById(R.id.User_pointer);
        TextView date =(TextView) findViewById(R.id.User_Date);
        TextView time = (TextView) findViewById(R.id.user_time);
        
        title.setText(dbmodel.getTitle());
        venue.setText(dbmodel.getVenue());
        pointer.setText(dbmodel.getPointer());
        
        String setdate=""+dbmodel.getMonth()+"-"+dbmodel.getDay()+"-"+dbmodel.getYear();
        String settime=""+dbmodel.getHour()+":"+dbmodel.getMin();
        date.setText(setdate);
        time.setText(settime);
        
        String namel=dbmodel.getNamestr();
       // String numberl=dbmodel.getNumstr();
        name = namel.split(strSeparator);
    	//numbers = numberl.split(strSeparator);

    	for (int i = 0; i < name.length; ++i) {
    		System.out.println(name[i]);
    		namelist.add(name[i]);
    	}
    	
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
	mInflater = (LayoutInflater) MainlistviewAvtivity.this
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
	tv1.setText("");
	System.out.println(namelist.get(position));
	tv.setText(namelist.get(position));
	//tv1.setText(numberlist.get(position));
	
	return vi;
}

@Override
public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {
	// TODO Auto-generated method stub

}
}
}
