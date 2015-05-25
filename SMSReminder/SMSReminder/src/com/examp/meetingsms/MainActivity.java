package com.examp.meetingsms;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.examp.meetingsms.Helper.DataSourceDAO;
import com.examp.meetingsms.Helper.DbModel;

public class MainActivity extends Activity {
int position1;
	private DataSourceDAO mySQLiteAdapter;
	List<String> titlelist = new ArrayList<String>();
	MymainAdapter myadapter;
	DataSourceDAO todb;
	List<DbModel> elementmodel;
	String myId;
	//String[] title;
	//public static String strSeparator = "\\|";
	
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		//String namestring = elementmodel.getNamestr();
		/*name = namestring.split(strSeparator);
		for (int i = 0; i < name.length; ++i) {
			System.out.println(name[i]);
			namelist.add(name[i]);
			
		*/	ListView lv = (ListView) findViewById(R.id.listViewmain);
			myadapter = new MymainAdapter();
			lv.setAdapter(myadapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					
					Intent show = new Intent(MainActivity.this, MainlistviewAvtivity.class);
					show.putExtra("position", elementmodel.get(position).getId());
					startActivity(show);
					
				}
			});
			
			
			//edit for delete row
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						position1 = pos;
						removeItemFromList(position1);
						return true;
					}
				});
			//edit for delete row
		

    }
	
	// removes an element from the list
		protected void removeItemFromList(final int position1) {
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
			alert.setTitle("Delete");
			alert.setMessage("Do you want delete this item?");
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		              // do something when the button is clicked
		              public void onClick(DialogInterface arg0, int arg1) {
		               todb.open();
		               todb.delete_byID(elementmodel.get(position1).getId());
		               
		               elementmodel = todb.getAllComments();
		               todb.close();
		               titlelist = new ArrayList<String>();
		       		//title=new String[50];
		       		for(int i=0;i<elementmodel.size();i++) {
		       			
		       			titlelist.add( elementmodel.get(i).getTitle());
		       			
		       			
		       		}
		               myadapter.notifyDataSetChanged();
		         //updateList();
		               }
		              });
		        
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		              // do something when the button is clicked
		              public void onClick(DialogInterface arg0, int arg1) {
		     
		               }
		              });
		        
			alert.show();
			
			
			
		}
		// removes an element from the list
	class MymainAdapter extends BaseAdapter implements
	CompoundButton.OnCheckedChangeListener {
LayoutInflater mInflater;
TextView tv1, tv;

// CheckBox cb;
MymainAdapter() {
	// mCheckStates = new SparseBooleanArray(name.size());
	mInflater = (LayoutInflater) MainActivity.this
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

@Override
public int getCount() {
	// TODO Auto-generated method stub
	return titlelist.size();
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
		vi = mInflater.inflate(R.layout.mainactivity_row, null);
	TextView tv = (TextView) vi.findViewById(R.id.contact_name);
	//tv1 = (TextView) vi.findViewById(R.id.phone_number);
	//System.out.println(title.get(position));
	tv.setText(titlelist.get(position));
	//tv1.setText(numberlist.get(position));
	
	return vi;
	
	
}

@Override
public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {
	// TODO Auto-generated method stub

}
}

	@Override
	protected void onResume() {
		todb = new DataSourceDAO(getApplicationContext());
		myId = getIntent().getStringExtra("ID");
		
		todb.open();
		elementmodel = todb.getAllComments();
		todb.close();
		titlelist = new ArrayList<String>();
		//title=new String[50];
		for(int i=0;i<elementmodel.size();i++) {
			
			titlelist.add( elementmodel.get(i).getTitle());
			
			
		}
		myadapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	// Inflating the menu items
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	Intent intent= new Intent(MainActivity.this, Meeting_detail.class);
    	startActivity(intent);
    	
        
        return super.onOptionsItemSelected(item);
    }
    
   /* private void updateList(){
    	 cursor.requery();
    	  }*/
}
