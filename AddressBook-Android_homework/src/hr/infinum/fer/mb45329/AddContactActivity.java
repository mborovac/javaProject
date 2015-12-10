package hr.infinum.fer.mb45329;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class representing the activity called when the user wishes to add a new contact to the address book.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class AddContactActivity extends Activity {
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * 
	 * Method executed when the activity is called.
	 * Method give the user the opportunity to fill in contact information and save it or cancel
	 * the new contact addition and navigate back to the starting page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_contact);
		
		Button saveButton = (Button) findViewById(R.id.btnSave);
		saveButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 
			 * Method checks whether the given contact's information is filled and valid and saves the
			 * new contact if everything is fine or shows the user the appropriate messages if something 
			 * is not fine.
			 */
			@Override
			public void onClick(View v) {
				checkFields();
			}
		});
		
		Button cancelButton = (Button) findViewById(R.id.btnCancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 * 
			 * cancels the new contact addition and navigates back to the start screen.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddContactActivity.this, HomeActivity.class);
				startActivity(intent);	
			}
		});
	}
	
	/**
	 * Method used to check whether all the required fields have been filled out and shows the adequate
	 * message if any of the required fields are left empty or the e-mail is not of a valid e-mail format.
	 */
	private void checkFields() {
		
		boolean errors = false;
		
		// contact name
		String newName = ((TextView) findViewById(R.id.tvNewContactName)).getText().toString();
		TextView nameErrorView = (TextView) findViewById(R.id.tvContactNameError);
		if(newName == null || newName.isEmpty()) {
			errors = true;
			nameErrorView.setText("Contact must have a name!");
		} else {
			nameErrorView.setText("");
		}
		
		// contact phone number
		String newPhone = ((TextView) findViewById(R.id.tvNewContactPhone)).getText().toString();
		TextView phoneNumberErrorView = (TextView) findViewById(R.id.tvContactPhoneError);
		if(newPhone == null || newPhone.isEmpty()) {
			errors = true;
			phoneNumberErrorView.setText("Contact must have a phone number!");
		} else {
			phoneNumberErrorView.setText("");
		}
		
		// contact e-mail
		String newEmail= ((TextView) findViewById(R.id.tvNewContactEmail)).getText().toString();
		TextView emailErrorView = (TextView) findViewById(R.id.tvContactEmailError);
		if(newEmail == null || newEmail.isEmpty()) {
			errors = true;
			emailErrorView.setText("Contact must have an E-mail!");
		} else if (!newEmail.matches(".+@.+\\..+")) {
			errors = true;
			emailErrorView.setText("Invalid E-mail address! Example:"
					+ "ivan@foofoo.com");
		} else {
			emailErrorView.setText("");
		}
		
		// contact note
		String newNote = ((TextView) findViewById(R.id.tvNewContactNote)).getText().toString();
		
		// contact Facebook profile address
		String newAddress = ((TextView) findViewById(R.id.tvNewContactAddress)).getText().toString();
		
		// save the new contact if all the required fields are filled out
		if(!errors) {
			if(newNote == null || newNote.isEmpty()) {
				newNote = "No note.";
			}
			
			if(newAddress == null || newAddress.isEmpty()) {
				newAddress = "No address.";
			}
			HomeActivity.listOfContacts.add(new Contact(newName, newPhone, newEmail, 
					newNote, newAddress));
			Intent intent = new Intent(AddContactActivity.this, HomeActivity.class);
			startActivity(intent);
		}
	}
}
