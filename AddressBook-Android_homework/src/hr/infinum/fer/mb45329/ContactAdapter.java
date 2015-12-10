package hr.infinum.fer.mb45329;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class used to define what each element of the ListView in the HomeActivity will
 * show as part of the list.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
	
	public ContactAdapter(Context context, List<Contact> contacts) {
		super(context, 0, contacts);
	}
	
	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 * 
	 * Method sets the top TextView to show the contact's name and the bottom TextView to show the
	 * contact's phone number and the e-mail.
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null) {
			
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.list_item, parent, false);
		}
		
		TextView nameTextView  = (TextView) view.findViewById(R.id.tvName);
		TextView lastNameTextView = (TextView) view.findViewById(R.id.tvContacts);
		
		nameTextView.setText(getItem(position).getName());
		lastNameTextView.setText(getItem(position).getPhoneNumber() + ", " + getItem(position).getEmail());
		
		return view;
	}
}
