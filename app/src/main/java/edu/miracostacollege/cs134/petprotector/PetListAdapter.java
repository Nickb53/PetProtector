package edu.miracostacollege.cs134.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.miracostacollege.cs134.petprotector.Model.Pet;

/**
 * Custom list adapter for Pet list
 */
public class PetListAdapter extends ArrayAdapter<Pet> {

    //member variables
    private Context mContext;
    private int mResourceId;
    private List<Pet> mAllPets;

    /**
     * Constructor for a new adapter
     * @param context
     * @param resource
     * @param objects
     */
    public PetListAdapter(Context context, int resource, List<Pet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourceId = resource;
        mAllPets = objects;
    }

    /**
     * GetView method creates A view and inflates all elements
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //For each Pet in the list inflate its view
        Pet focusedPet = mAllPets.get(position);

        final Pet selectedPet = mAllPets.get(position);

        //Create a layout inflater
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(mResourceId, null);
        //Set the two text views
        TextView listNameTextView = view.findViewById(R.id.listNameTextView);
        TextView listDetailsTextView = view.findViewById(R.id.listDetailsTextView);
        ImageView listImageTextView = view.findViewById(R.id.listImageTextView);
        LinearLayout petListLinearLayout = view.findViewById(R.id.petListLinearLayout);

        petListLinearLayout.setTag(selectedPet);
        listNameTextView.setText(focusedPet.getName());
        listDetailsTextView.setText(focusedPet.getDetails());
        listImageTextView.setImageURI(focusedPet.getImageURI());

        return view;

    }
}