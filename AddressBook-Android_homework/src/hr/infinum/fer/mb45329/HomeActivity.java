package hr.infinum.fer.mb45329;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * Class representing the activity that will be called when the application starts.
 * Activity has a button to add new contacts on top of the screen and a scrollable list of the current
 * contacts filling the rest of the screen.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class HomeActivity extends Activity {
	
	public static List<Contact> listOfContacts = new ArrayList<>();

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * 
	 * Method called when the activity is called. Method creates the top button and fills the 
	 * contact list with information.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		if(listOfContacts.isEmpty()) {
			getContacts();
		}
		
		ListView listView = (ListView) findViewById(R.id.contactList);
		ContactAdapter adapter = new ContactAdapter(this, listOfContacts);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(
			 * android.widget.AdapterView, android.view.View, int, long)
			 * 
			 * Method called when a single contact from the contact list is clicked.
			 * It prepares all the info on the selected contact and stores it in to the intent
			 * which calls the ProfileActivity.
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Contact contact = listOfContacts.get(position);
				
				Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
				intent.putExtra("contactName", contact.getName());
				intent.putExtra("contactPhone", contact.getPhoneNumber());
				intent.putExtra("contactEmail", contact.getEmail());
				intent.putExtra("contactNote", contact.getNote());
				intent.putExtra("contactProfile", contact.getFbProfile());
				startActivity(intent);
			}
		});
		
		Button addButton = (Button) findViewById(R.id.btnAdd);
		addButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 
			 * Method called when the "Add a new contact" button is pressed.
			 * It creates a new intent calling the class AddContactActivity to add a new contact.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Method used to read and parse the .json file containing the contact information. The read
	 * contact information is saved in a static internal list for further use.
	 */
	private void getContacts() {
		JSONArray jsonArray = null;
		try {
			JSONObject obj = new JSONObject(loadJSONFromAsset());
			jsonArray = obj.getJSONArray("people");
		} catch (JSONException e1) {
			Log.e("AddressBook", "Can not get JSON object people from JSON array.", e1);
		}
		
		for(int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject person = jsonArray.getJSONObject(i);
				String name = person.getJSONObject("person").optString("name");
				String phone = person.getJSONObject("person").optString("phone");
				String email = person.getJSONObject("person").optString("email");
				String note = person.getJSONObject("person").optString("note");
				String fbProfile = person.getJSONObject("person").optString("facebook_profile");
				Contact contact = new Contact(name, phone, email, note, fbProfile);
				listOfContacts.add(contact);
			} catch (JSONException e) {
				Log.e("AddressBook", "Error getting JSON object.", e);
			}
		}
	}
	
	/**
	 * Method used to read the .json file with contact information.
	 * 
	 * @return
	 */
	private String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream stream = getAssets().open("people.json");
			int size = stream.available();
			byte[] buffer = new byte[size];
			stream.read(buffer);
			stream.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
}
