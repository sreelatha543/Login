package com.yeldi.userdetails;

import java.util.ArrayList;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DBhelper myDbHelper;
	EditText uname,pwd;
	Button login;
     User_help mItem ;
	//private User_help mItem;
	private ArrayList<User_help> mItems = new ArrayList<User_help>();
	public static ArrayList<User_help> items = new ArrayList<User_help>();
	public static ArrayList<User_help> addedqtyValues = new ArrayList<User_help>();
	User_help item = new User_help();
	//private ArrayList<String> mUsers = new ArrayList<String>();
	String urname,pd;
	String[] un;
	String[] pwdd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myDbHelper = new DBhelper(MainActivity.this,
				"User.sqlite");
		myDbHelper.createDataBase();
		myDbHelper.openDataBase();
		items=myDbHelper.getRowbyQty();
		//mItems=myDbHelper.getRowbyQty();
		uname=(EditText)findViewById(R.id.editText1);
		pwd=(EditText)findViewById(R.id.editText2);
		login=(Button)findViewById(R.id.button1);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=uname.getText().toString();
								Log.v("name", "===="+name);
				String passwd=pwd.getText().toString();
				Log.v("passwd", "===="+passwd);
				for (User_help mItem : items) {
					Log.v("------------", ""+mItem.getUsername());
					if (mItem.getUsername().equals(name) && mItem.getPwd().equals(passwd) ) {
						
						item.setUsername(mItem.getUsername());
						item.setPwd(mItem.getPwd());
						addedqtyValues.add(item);
						Log.v("items", "" + items.equals(name));
						Log.v("added qty values", "" + mItem.getUsername()+""+mItem.getPwd());
						Log.v("===========", ""+item.getUsername()+""+item.getPwd());
						
					}  
					 /*else {
						 Toast.makeText(MainActivity.this,"User Name or Password does not match", Toast.LENGTH_LONG).show();
					}*/
					} 
				 if (item.getUsername().equals(name)&&  item.getPwd().equals(passwd)) {
					 
					 Intent intent=new Intent(MainActivity.this,LoginActivity.class);
	                	startActivity(intent);
				}
				 else {
					 Toast.makeText(MainActivity.this,"User Name or Password does not match", Toast.LENGTH_LONG).show();
				}
				
				
				}			
			      // check if the Stored password matches with  Password entered by user           
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
