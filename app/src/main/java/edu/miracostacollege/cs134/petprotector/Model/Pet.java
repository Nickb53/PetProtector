package edu.miracostacollege.cs134.petprotector.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;

/**
 * Create a Pet object
 */
public class Pet implements Parcelable {
    //member variables
    private String mName;
    private String mDetails;
    private long mId;
    private String mPhone;
    private Uri mImageURI;
    private String uriString;

    /**
     * Constructor with all parameters
     * @param name pet name
     * @param details pet description
     * @param id unique id
     * @param phone pet owner phone number
     * @param imageURI uri for image
     */
    public Pet(String name, String details, int id, String phone, Uri imageURI) {
        mName = name;
        mDetails = details;
        mId = id;
        mPhone = phone;
        mImageURI = imageURI;
        uriString = imageURI.toString();
    }

    /**
     * Constructor without id
     * @param name pet name
     * @param details pet details
     * @param phone pet owner phone number
     * @param imageURI uri for image
     */
    public Pet(String name, String details, String phone, Uri imageURI){
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
        uriString = imageURI.toString();
    }

    /**
     * Creator method for parcelable Pet items
     */
    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    //getters and setters for member variables
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Uri getImageURI() {
        return mImageURI;
    }

    public void setImageURI(Uri imageURI) {
        mImageURI = imageURI;
    }

    public long getId() {
        return mId;
    }

    public String getImageURIString(){
        return uriString;
    }

    /**
     * ToString method to show details about Pet
     * @return String
     */
    @Override
    public String toString() {
        return "Pet{" +
                "mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mId=" + mId +
                ", mPhone='" + mPhone + '\'' +
                ", mImageURI=" + uriString +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * For Parcelable Pet objects
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDetails);
        dest.writeLong(mId);
        dest.writeString(mPhone);
        dest.writeString(uriString);
    }

    /**
     * Parcel constructor for pet objects
     * @param parcel
     */
    public Pet(Parcel parcel){
        mName = parcel.readString();
        mDetails = parcel.readString();
        mId = parcel.readLong();
        mPhone = parcel.readString();
        uriString = parcel.readString();
    }
}
