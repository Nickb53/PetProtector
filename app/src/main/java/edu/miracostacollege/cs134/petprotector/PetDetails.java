package edu.miracostacollege.cs134.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import edu.miracostacollege.cs134.petprotector.Model.Pet;

/**
 * Class to display details of a single pet
 */
public class PetDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        //connecting the views
        TextView petNameDetailsTextView = findViewById(R.id.petNameDetailsTextView);
        TextView petDetailsDetailsTextView = findViewById(R.id.petDetailsDetailsTextView);
        TextView detailsPhoneTextView = findViewById(R.id.detailsPhoneTextView);
        ImageView detailsImageView = findViewById(R.id.detailsImageView);

        //receiving the intent
        Intent detailsIntent = getIntent();

        //getting the pet item
        Pet pet = detailsIntent.getParcelableExtra("SelectedPet");

        Log.i("Details", "RPet: " + pet.toString());

        //creating the uri
        Uri uri = Uri.parse(pet.getImageURIString());
        detailsImageView.setImageURI(uri);

        //setting the views
        petNameDetailsTextView.setText(pet.getName());
        petDetailsDetailsTextView.setText(pet.getDetails());
        detailsPhoneTextView.setText(pet.getPhone());
    }
}
