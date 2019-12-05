package com.example.buffstuff.Sell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Login.SignUpActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class UploadActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "Testing";

    private ImageView imageView;//Variable will hold ImageView that pertains to the ImageView in activity_upload.xml
    private Uri filePath;//Varable will hold the filepath
    private final int PICK_IMAGE_REQUEST = 71;//
    String url = "";//url will hold the link to the image in the database
    String filename = "NoPhotoAvailable.png";//Initial filename represents a picture for images without an image
    boolean flag = false;//Variable will be used to determine whether an image is uploaded

    //Firebase
    FirebaseStorage storage;//Firebase storage variable
    StorageReference storageReference;//Storage Reference Variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {//OnCreate Method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);//sets the content view to the upload page's layout.
        //This way we can read the values in the text fields

        //Initialize Views  Method 2
        imageView = (ImageView) findViewById(R.id.imgview);//Like I mentioned before, the imageView, now holds the values of the imageView in the xml file

        storage = FirebaseStorage.getInstance();//Gets Storage instance
        storageReference = storage.getReference();//storageReference now represents the actual database storage

    }

    public void chooseImage(View view) {//chooseImage method
        Intent intent = new Intent();//Initializes a new Intent
        intent.setType("image/*");//Tells the compiler that we are looking for an image type file
        intent.setAction(Intent.ACTION_GET_CONTENT);//Grabs content
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//Calls the new intent
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//onActivityResult Method
        super.onActivityResult(requestCode, resultCode, data);//
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK//
                && data != null && data.getData() != null )//
        {//
            filePath = data.getData();//
            try {//
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);//Sets bitmap to image that is chosen
                imageView.setImageBitmap(bitmap);//Sets the imageview in the layout to the image chosen
                flag = true;//flips flag, now we know an image has been uploaded
            }//
            catch (IOException e)//
            {//
                e.printStackTrace();//
            }//
        }//
    }//

    @Override
    protected void onStart() {//onStart method
        super.onStart();
    }

    // goToSell Method
    // This function will first create a filename for the image that was chosen, and then upload it to the database.
    // Then after the image is successfully uploaded, it will use the filename to find the actual image url
    // Then, it will grab all the values set by the user in the sell page, along with this new url, and create a new item, with these values and uploads it
    // You may notice a lot of work is done inside the onSuccess methods, this is because Android Studio is asynchronous.
    public void goToSell(View v) {
//==============================================================================================================================================CODE FROM UPLOAD IMAGE
        final Intent intent = new Intent(this, SellActivity.class); // Initialize a new intent, that way we can call the new intent once the upload is finished

//        All of this was to be able to check that we are in fact able to read the values on the layout page
//        boolean flag = false;
//        EditText productName = (EditText) findViewById(R.id.inputName);
//        String name = productName.getText().toString();
//        //Log.d("name", name);
//
//        EditText productPrice = (EditText) findViewById(R.id.price);
//        String price = productPrice.getText().toString();
//        //Log.d("dPrice", price);
//
//        Spinner productCondition = (Spinner) findViewById(R.id.condition_spinner);
//        String condition = productCondition.getSelectedItem().toString();
//        //Log.d("productCondition", condition);
//
//        Spinner productCategory = (Spinner) findViewById(R.id.category_spinner);
//        String category = productCategory.getSelectedItem().toString();
//        //Log.d("category", category);


        if(filePath != null)//
        {//
            final ProgressDialog progressDialog = new ProgressDialog(this);//Creates a progressDialog variable, that will print to the screen
            progressDialog.setTitle("Uploading...");//Sets the progressDialog to print "Uploading..."
            progressDialog.show();//Shows the progressDialog to the user.

            url = "";//this variable will hold the actual image url, for now it is set to "" so we can check if they do upload a file

            filename = "Images"+ UUID.randomUUID().toString();// creates the filename for the image that will be uploaded to the database, we store it in a variable that way we can use the filname later on as well
            StorageReference ref = storageReference.child(filename);//Adds the image to the database


            Log.d("goToSell", "possible filename: " + filename);//This is a check to makesure that the filename matches the database, and it does

            ref.putFile(filePath)  //
                    // if image upload is successful
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//
                        @Override//
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //
                            progressDialog.dismiss();//Dismisses the previous progressDialog
                            Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();//prints to the user that the image upload finished
                            Log.d("goToSell", "Image upload finished");//prints to us that the image upload finished

                            //This was a test filename I previously used to make sure the url returned is correct
                            //storageReference.child("Images2d80c5bc-a2f6-43e3-84d6-ab2bc33df94a").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { // This is a method, defined by Firebase, to find an image's url
                                @Override
                                public void onSuccess(Uri uri) { //onSuccess
                                    // Got the download URL for 'filename'
                                    url = uri.toString();//this sets the url variable to the actual url tha we need
                                    Log.d("goToSell", "URL Retrieval: success");// prints to us that it made it into onSuccess
                                    Log.d("goToSell", "url: " + url); // prints to us the url that it returned

                                    //This sets a textView that I had previously to the image url.
                                    //This was removed, so it no longer is necessary
                                    //setContentView(R.layout.activity_upload);
                                    //TextView tv1 = (TextView)findViewById(R.id.fileChosen);
                                    //tv1.setText(url);

                                    //Log.d("goToSell", "inside goToSell message"); // prints to us that we are inside the right method
                                    EditText n = (EditText) findViewById(R.id.inputName); // creates an editext variable to hold the values inside an edittext in the layout file, in this case the product name
                                    EditText p = (EditText) findViewById(R.id.price); // creates an editext variable to hold the values inside an edittext in the layout file, in this case the product price
                                    Spinner con = (Spinner) findViewById(R.id.condition_spinner); // creates a spinner variable to hold the values inside an spinner in the layout file, in this case the product condition
                                    Spinner cat = (Spinner) findViewById(R.id.category_spinner); // creates a spinner variable to hold the values inside an spinner in the layout file, in this case the product category

                                    //EditText downloadUrl = (EditText)findViewById(R.id.fileChosen);  // creates an editext variable to hold the values inside an edittext in the layout file, in this case the image url
                                    //String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/buffstuff-6f3c8.appspot.com/o/NoPhotoAvailable.png?alt=media&token=9c60780a-2ed6-4ebf-8ad1-89e3d49defde";

                                    //If some information left unfilled, make them fill
                                    if(n.getText().toString().equals("") || p.getText().toString().equals("") || con.getSelectedItem().toString().equals("Select One") || cat.getSelectedItem().toString().equals("Select One") || !flag)
                                    {
                                        Log.d(TAG, "goToSell: not all things entered");// prints to us, that some fields are empty
                                        Toast.makeText(UploadActivity.this, "Please fill out all categories", Toast.LENGTH_SHORT).show();// prints to the user, that some fields are empty

                                        Log.d("Checking Fields", "Not All Things Entered");// prints to us, that some fields are empty
                                    }
                                    else{ //otherwise, all the fields are filled

                                        //Log.d("filename after after", filename);  // a print to tell us where we are in the program

                                        //This block of code will create new variables to hold the string and double values of all the fields in the layout file
                                        String inputName = n.getText().toString(); // Will hold the product name
                                        Double price = Double.parseDouble(p.getText().toString());  // Will hold the product price
                                        String condition = con.getSelectedItem().toString();  // Will hold the product condition
                                        String category = cat.getSelectedItem().toString();  // Will hold the product category

                                        mAuth = FirebaseAuth.getInstance(); // gets instance
                                        FirebaseUser currentUser = mAuth.getCurrentUser(); // variable that holds the current user
                                        String userId = currentUser.getUid(); // this will now hold the current user's id


                                        Map<String, Object> item = new HashMap<>(); //creates a new item that will be added to the database

                                        item.put("name", inputName);  //  Inputs the item's name
                                        item.put("price", price);  //  Inputs the item's price
                                        item.put("condition", condition);  //  Inputs the item's conditon
                                        item.put("category", category);  //  Inputs the item's category
                                        item.put("user", userId);  //  Inputs the item's owner id
                                        //will let us view the items we are selling
                                        item.put("image", url);  //  Inputs the item's image url

                                        //final Intent intent = new Intent(this, SellActivity.class); // Moved to top of goToSell

                                        // This tells the databse where we want this item to be stored
                                        db.collection("items")
                                                // Says we are adding the item from above
                                                .add(item)
                                                // If successful
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        startActivity(intent);  //Once upload finishes, return to main sell page
                                                        Log.d("dbSuccess", "DocumentSnapshot written with ID: " + documentReference.getId());//  prints to us that the products upload succeeded
                                                    }
                                                })
                                                // If not successful
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("dbFailure", "Error adding document", e);  //  prints to us that the products upload failed
                                                    }
                                                });
                                    }

                                }
                            // if url retrieval fails
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    //This is in the case the retrieval of the image url fails
                                    Log.e("goToSell", "URL Retrieval: failed with exception: " + exception.toString());//  prints to us that the url retrieval failed
                                }
                            });
                        }//
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                            generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
//                        }
                    })//
                    //if image upload fails
                    .addOnFailureListener(new OnFailureListener() {//
                        @Override//
                        public void onFailure(@NonNull Exception e) {//
                            progressDialog.setMessage("Image Upload Failed!");  //  prints to the user that the image upload failed
                            Log.e("goToSell", "Image upload failed");  //  prints to us that the image upload failed
                        }//
                    })//
                    //while the image is uploading
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {//
                        @Override//
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {//
                            progressDialog.setMessage("Uploading... "); //prints to the user, "Uploading..."
                        }//
                    });//

            //Log.d("goToSell", "AccurateFilename: " + filename); // was used to double check that the filename matched what was uploaded

        }//

    }

}
