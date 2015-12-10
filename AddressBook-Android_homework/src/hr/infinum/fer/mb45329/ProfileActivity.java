package hr.infinum.fer.mb45329;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class representing a profile of a single contact from the contact list.
 * Called when the user selects one of the contacts in the list.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ProfileActivity extends Activity{
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * 
	 * Method executed when the activity is called. Method sets all the TextViev's text 
	 * and give the user the option of selecting 2 buttons: Call button and Facebook button.
	 * Call button calls the contact's number while the Facebook button uses the internal phone's
	 * browser to navigate to the given contact's Facebook profile.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile);
		
		TextView contactNameLabel = (TextView) findViewById(R.id.tvContactName);
		String contactName = getIntent().getExtras().getString("contactName", "No Name");
		contactNameLabel.setText(contactName);
		
		TextView contactPhoneLabel = (TextView) findViewById(R.id.tvContactPhone);
		final String contactPhone = getIntent().getExtras().getString("contactPhone", "No Phone");
		contactPhoneLabel.setText(contactPhone);
		
		TextView contactEmailLabel = (TextView) findViewById(R.id.tvContactEmail);
		String contactEmail = getIntent().getExtras().getString("contactEmail", "No E-mail");
		contactEmailLabel.setText(contactEmail);
		
		TextView contactNoteLabel = (TextView) findViewById(R.id.tvContactNote);
		String contactNote = getIntent().getExtras().getString("contactNote", "No Note");
		contactNoteLabel.setText(contactNote);
		
		TextView contactProfileLabel = (TextView) findViewById(R.id.tvContactProfile);
		final String contactProfile = getIntent().getExtras().getString("contactProfile", "No Facebook profile");
		contactProfileLabel.setText(contactProfile);
		
		Button callButton = (Button) findViewById(R.id.btnCall);
		callButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 
			 * Method calls the selected contact's number.
			 */
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactPhone));
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Invalid phone number.",
						    Toast.LENGTH_LONG).show();
				}
			}
		});
		
		Button facebookButton = (Button) findViewById(R.id.btnFacebook);
		facebookButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 
			 * Method uses the internal browser to navigate to the selected contact's Facebook profile.
			 */
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contactProfile));
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Contact's Facebook profile is not a valid profile.",
						    Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
