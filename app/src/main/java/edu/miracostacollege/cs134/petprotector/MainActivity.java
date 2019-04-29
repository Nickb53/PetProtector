package edu.miracostacollege.cs134.petprotector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.miracostacollege.cs134.petprotector.Model.DBHelper;
import edu.miracostacollege.cs134.petprotector.Model.Pet;

/**
 * MainActivity Starts the app and is responsible for loading the listView with pets
 * It also contains the methods for adding a new pet, and transferring to the details view
 */
public class MainActivity extends AppCompatActivity {

    //Tag for the app
    public static final String TAG = "PetProtector";

    //Instance variables
    ImageView petImageView;
    private DBHelper mDb;
    private List<Pet> mAllPets;
    private ListView mPetsListView;
    private EditText mPhoneEditText;
    private PetListAdapter mPetListAdapter;
    private EditText mPetNameEditText;
    private EditText mPetDetailsEditText;
    private Uri imageUri;
    public static final int RESULT_LOAD_IMAGE = 200;

    /**
     * On Create connects the views and loads the database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = new DBHelper(this);
        mAllPets = mDb.getAllPets();

        mPetsListView = findViewById(R.id.petsListView);
        mPetNameEditText = findViewById(R.id.petNameEditText);
        mPetDetailsEditText = findViewById(R.id.petDetailsEditText);
        mPhoneEditText = findViewById(R.id.phoneNumberEditText);

        //Connect the petImage to the layout
        petImageView = findViewById(R.id.petImageView);

        //setImageUri to the PetImageView
        petImageView.setImageURI(getUriToResource(this, R.drawable.none));

        mPetListAdapter = new PetListAdapter(this,R.layout.pet_list_item, mAllPets);
        mPetsListView.setAdapter(mPetListAdapter);
    }

    /**
     * onActivityResult is used to determine which image the user chose
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(true){
            Uri uri = data.getData();
            petImageView.setImageURI(uri);
            imageUri = uri;
            Log.i(TAG, "Loaded imageUri: " + uri.toString());
        }
        Log.i(TAG, "Failed to load image");
    }

    /**
     * When the button is clicked, a new pet object is created and added to the list
     * and the database
     * @param v
     */
    public void addPet(View v){
        String name = mPetNameEditText.getText().toString();
        String details = mPetDetailsEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        Pet pet = new Pet(name, details, phone, imageUri);

        mDb.addPet(pet);
        mPetListAdapter.add(pet);
        mPetListAdapter.notifyDataSetChanged();
    }

    /**
     * This method is used for getting a pet image from the gallery and loading it to the
     * database
     * It handles permissions
     * @param v
     */
    public void selectPetImage(View v){
        //1) Make a list of permissions (empty to start)
        //2) As user grants them, add each permission to the list
        List<String> permsList = new ArrayList<>();
        int permReqCode = 100;
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int hasWritePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadPerm = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);

        //Check to see if camera permission is denied
        //If denied, add it to List of permissions requested
        if(hasCameraPerm == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.CAMERA);
        if(hasWritePerm == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasReadPerm == PackageManager.PERMISSION_DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        //Now that we've built the list, let's ask the user
        if(permsList.size() > 0){
            //Convert the list into an array
            String[] perms = new String[permsList.size()];
            permsList.toArray(perms);
            // Make the request to the user
            ActivityCompat.requestPermissions(this, perms, permReqCode);
        }
        //After requesting the permissions, find out which ones user granted
        //Check to see if all permissions were granted
        if(hasCameraPerm == PackageManager.PERMISSION_GRANTED &&
            hasReadPerm == PackageManager.PERMISSION_GRANTED &&
            hasWritePerm == PackageManager.PERMISSION_GRANTED){
            //Open the Gallery!
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            Log.i(TAG, "Starting activity for result");

        } else
        {
            //
        }

    }

    /**
     * Helper method to create default image uri
     * @param context
     * @param id
     * @return the uri
     */
    private static Uri getUriToResource(Context context, int id){
        Resources res = context.getResources();
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(id) + "/"
                + res.getResourceTypeName(id) + "/"
                + res.getResourceEntryName(id);
        return Uri.parse(uri);
    }

    /**
     * When the user selects a list item, its information is sent to the details activity
     * the details activity is then started
     * @param view
     */
    public void viewPetDetails(View view){

        Pet selectedPet = (Pet) view.getTag();
        Log.i(TAG, "Pet: " + view.getTag());

        Intent detailsIntent = new Intent(this, PetDetails.class);
        detailsIntent.putExtra("SelectedPet", selectedPet);

        startActivity(detailsIntent);
    }

    /**
     * Clears all pets from the database
     * @param view
     */
    public void clearAllPets(View view)
    {
        mAllPets.clear();
        // Permanently delete games from the database, buh bye
        mDb.deleteAllPets();
        mPetListAdapter.notifyDataSetChanged();
    }

}
