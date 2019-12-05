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

    private Button btnchoose, btnupload, btnSell;//Method 2
    private ImageView imageView;//Method 2
    private Uri filePath;//Method 2
    private final int PICK_IMAGE_REQUEST = 71;//Method 2
    String url = "";
    String filename = "NoPhotoAvailable.png";
    boolean flag = false;

    //Firebase
    FirebaseStorage storage;//Method 2
    StorageReference storageReference;// Method 2




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

//
        //Initialize Views  Method 2
        btnchoose = (Button) findViewById(R.id.btnchoose);//Method 2
        //btnupload = (Button) findViewById(R.id.btnupload);//Method 2
        btnSell = (Button) findViewById(R.id.uploadButton);
        imageView = (ImageView) findViewById(R.id.imgview);//Method 2

        storage = FirebaseStorage.getInstance();//Method 2
        storageReference = storage.getReference();//Method 2

//        btnchoose.setOnClickListener(new View.OnClickListener() {//Method 2
//            @Override//Method 2
//            public void onClick(View v) {//Method 2
//                chooseImage();//Method 2
//            }//Method 2
//        });//Method 2
//
//        btnupload.setOnClickListener(new View.OnClickListener() {//Method 2
//            @Override//Method 2
//            public void onClick(View v) {//Method 2
//
//                //Holds values for when they are erased
//                EditText productName = (EditText) findViewById(R.id.inputName);
//                String name = productName.getText().toString();
//                //Log.d("name", name);
//
//                EditText productPrice = (EditText) findViewById(R.id.price);
//                String price = productPrice.getText().toString();
//                //Log.d("dPrice", price);
//
//                Spinner productCondition = (Spinner) findViewById(R.id.condition_spinner);
//                String condition = productCondition.getSelectedItem().toString();
//                //Log.d("productCondition", condition);
//
//                Spinner productCategory = (Spinner) findViewById(R.id.category_spinner);
//                String category = productCategory.getSelectedItem().toString();
//                //Log.d("category", category);
//
//                //=====================================================
//
//
//                //String filename = "NoPhotoAvailable.png";
//                //Log.d("filename before", filename);
//
//                uploadImage();//Method 2
//
//                Log.d("URL-Outside", url);
//
//                //Log.d("accurate filename", "-"+filename+"-");
//
//                //Finds url from Filename
////                storageReference.child("NoPhotoAvailable.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                    @Override
////                    public void onSuccess(Uri uri) {
////                        // Got the download URL for 'users/me/profile.png'
////                        url = uri.toString();
////                        Log.d("URL Retreival:", "Success");
////                        Log.d("url:", url);
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception exception) {
////                        // Handle any errors
////                        Log.d("URL Retreival:", "Failed");
////                    }
////                });
//
////                storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                    @Override
////                    public void onSuccess(Uri uri) {
////                        // Got the download URL for 'users/me/profile.png'
////                        url = uri.toString();
////                        Log.d("URL Retreival:", "Success");
////                        Log.d("url:", url);
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception exception) {
////                        // Handle any errors
////                        Log.d("URL Retreival:", "Failed");
////                    }
////                });
//
////              This sets the textView to the url
////                setContentView(R.layout.activity_upload);
////                TextView tv1 = (TextView)findViewById(R.id.fileChosen);
////                tv1.setText(url);
//
//                //=======================================================================================================================================
//                //goToSell(v, name, price, condition, category);
//                //goToSell(v);
//                //finalupload(name, price, condition, category, filename);
//                //=======================================================================================================================================
//
//            }//Method 2
//        });//Method 2


    }

    public void chooseImage(View view) {//Method 2
        flag = true;
        Intent intent = new Intent();//Method 2
        intent.setType("image/*");//Method 2
        intent.setAction(Intent.ACTION_GET_CONTENT);//Method 2
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//Method 2
    }//Method 2

    @Override//Method 2
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Method 2
        super.onActivityResult(requestCode, resultCode, data);//Method 2
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK//Method 2
                && data != null && data.getData() != null )//Method 2
        {//Method 2
            filePath = data.getData();//Method 2
            try {//Method 2
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);//-----Method 2
                imageView.setImageBitmap(bitmap);//Method 2
            }//Method 2
            catch (IOException e)//Method 2
            {//Method 2
                e.printStackTrace();//Method 2
            }//Method 2
        }//Method 2
    }//Method 2

    private String uploadImage() {//Method 2
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

        //====================================================

        if(filePath != null)//Method 2
        {//Method 2
            final ProgressDialog progressDialog = new ProgressDialog(this);//Method 2
            progressDialog.setTitle("Uploading...");//Method 2
            progressDialog.show();//Method 2

            url = UUID.randomUUID().toString();
            //Log.d("HELLO", url);
            //String downloadUrl;

            filename = "Images"+ UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(filename);//Method 2



            //Log.d("possible url", ref.toString());//-
            //Log.d("possible filename", filename);

            ref.putFile(filePath)//Method 2

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//Method 2
                        @Override//Method 2
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//Method 2
                            progressDialog.dismiss();//Method 2
                            //url = uri.toString();
                            Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();//----Method 2
                        }//Method 2
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                            generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
//                        }
                    })//Method 2
                    .addOnFailureListener(new OnFailureListener() {//Method 2
                        @Override//Method 2
                        public void onFailure(@NonNull Exception e) {//Method 2
                            progressDialog.setMessage("Image Upload Failed!");//Method 2
                        }//Method 2
                    })//Method 2
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {//Method 2
                        @Override//Method 2
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {//Method 2
                            progressDialog.setMessage("Uploading... ");//Method 2
                        }//Method 2
                    });//Method 2

                Log.d("AccurateFilename:", filename);

            //Finds url from Filename

                storageReference.child("NoPhotoAvailable.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        url = uri.toString();
                        Log.d("URL Retreival:", "Success");
                        Log.d("url:", url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d("URL Retreival:", "Failed");
                    }
                });

//                storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        // Got the download URL for 'users/me/profile.png'
//                        url = uri.toString();
//                        Log.d("URL Retreival:", "Success");
//                        Log.d("url:", url);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                        Log.d("URL Retreival:", "Failed");
//                    }
//                });


        }//Method 2


        return filename;
        //onStart();
    }//Method 2


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goToSell(View v) {
//==============================================================================================================================================CODE FROM UPLOAD IMAGE
        final Intent intent = new Intent(this, SellActivity.class); // *********************************************
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

        if(filePath != null)//Method 2
        {//Method 2
            final ProgressDialog progressDialog = new ProgressDialog(this);//Method 2
            progressDialog.setTitle("Uploading...");//Method 2
            progressDialog.show();//Method 2

            url = "";
            //Log.d("HELLO", url);
            //String downloadUrl;

            filename = "Images"+ UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(filename);//Method 2



            //Log.d("possible url", ref.toString());//-
            Log.d("goToSell", "possible filename: " + filename);

            ref.putFile(filePath)  // Method 2
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//Method 2
                        @Override//Method 2
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//Method 2
                            progressDialog.dismiss();//Method 2
                            //url = uri.toString();
                            Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();//----Method 2
                            Log.d("goToSell", "Image upload finished");

                            //storageReference.child("Images2d80c5bc-a2f6-43e3-84d6-ab2bc33df94a").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            storageReference.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    url = uri.toString();
                                    Log.d("goToSell", "URL Retrieval: success");
                                    Log.d("goToSell", "url: " + url);

                                    //This sets the textView to the url
                                    //setContentView(R.layout.activity_upload);
//                                    TextView tv1 = (TextView)findViewById(R.id.fileChosen);
//                                    tv1.setText(url);

                                    //Log.d("goToSell", "inside goToSell message");
                                    EditText n = (EditText) findViewById(R.id.inputName);
                                    EditText p = (EditText) findViewById(R.id.price);
                                    Spinner con = (Spinner) findViewById(R.id.condition_spinner);
                                    Spinner cat = (Spinner) findViewById(R.id.category_spinner);
                                    //EditText downloadUrl = (EditText)findViewById(R.id.fileChosen);
                                    //String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/buffstuff-6f3c8.appspot.com/o/NoPhotoAvailable.png?alt=media&token=9c60780a-2ed6-4ebf-8ad1-89e3d49defde";
                                    //If some information left unfilled, make them fill
                                    if(n.getText().toString().equals("") || p.getText().toString().equals("") || con.getSelectedItem().toString().equals("Select One") || cat.getSelectedItem().toString().equals("Select One") || !flag){
                                        Log.d(TAG, "goToSell: not all things entered");
                                        Toast.makeText(UploadActivity.this, "Please fill out all categories", Toast.LENGTH_SHORT).show();

                                        Log.d("Checking Fields", "Not All Things Entered");
                                    }
                                    else{
                                        //Log.d("filename after after", filename);
                                        String inputName = n.getText().toString();
                                        Double price = Double.parseDouble(p.getText().toString());
                                        String condition = con.getSelectedItem().toString();
                                        String category = cat.getSelectedItem().toString();
                                        mAuth = FirebaseAuth.getInstance();
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        String userId = currentUser.getUid();


                                        Map<String, Object> item = new HashMap<>();

                                        item.put("name", inputName);
                                        item.put("price", price);
                                        item.put("condition", condition);
                                        item.put("category", category);
                                        item.put("user", userId);
                                        //-------
                                        item.put("image", url);

                                        //final Intent intent = new Intent(this, SellActivity.class); // *********************************************

                                        db.collection("items")
                                                .add(item)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        startActivity(intent);  // *********************************************
                                                        Log.d("dbSuccess", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("dbFailure", "Error adding document", e);
                                                    }
                                                });
                                    }

                                    //Erases the page/Clares the Fields
//                                    n.setText("");
//                                    p.setText("");
//                                    downloadUrl.setText("");
//                                    ImageView img = (ImageView)findViewById(R.id.imgview);
//                                    img.setImageBitmap(null);
//                                    con.set
//                                    cat.set
                                            //mPhotoView.setImageBitmap(null);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    Log.e("goToSell", "URL Retrieval: failed with exception: " + exception.toString());
                                }
                            });
                        }//Method 2
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                            generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
//                        }
                    })//Method 2
                    .addOnFailureListener(new OnFailureListener() {//Method 2
                        @Override//Method 2
                        public void onFailure(@NonNull Exception e) {//Method 2
                            progressDialog.setMessage("Image Upload Failed!");//Method 2
                            Log.e("goToSell", "Image upload failed");
                        }//Method 2
                    })//Method 2
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {//Method 2
                        @Override//Method 2
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {//Method 2
                            progressDialog.setMessage("Uploading... ");//Method 2
                        }//Method 2
                    });//Method 2

            //Log.d("goToSell", "AccurateFilename: " + filename);


        }//Method 2

//======================================================================================================================================================END OF CODE FROM UPLOADIMAGE

//        //Log.d("goToSell", "inside goToSell message");
//        EditText n = (EditText) findViewById(R.id.inputName);
//        EditText p = (EditText) findViewById(R.id.price);
//        Spinner con = (Spinner) findViewById(R.id.condition_spinner);
//        Spinner cat = (Spinner) findViewById(R.id.category_spinner);
//        //EditText downloadUrl = (EditText)findViewById(R.id.fileChosen);
//        //String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/buffstuff-6f3c8.appspot.com/o/NoPhotoAvailable.png?alt=media&token=9c60780a-2ed6-4ebf-8ad1-89e3d49defde";
//        //If some information left unfilled, make them fill
//        if(n.getText().toString().equals("") || p.getText().toString().equals("") || con.getSelectedItem().toString().equals("Select") || cat.getSelectedItem().toString().equals("Select")){
//            Log.d(TAG, "goToSell: not all things entered");
//            Toast.makeText(this, "Please fill out all categories",
//                    Toast.LENGTH_SHORT).show();
//        }
//        else{
//            //Log.d("filename after after", filename);
//            String inputName = n.getText().toString();
//            Double price = Double.parseDouble(p.getText().toString());
//            String condition = con.getSelectedItem().toString();
//            String category = cat.getSelectedItem().toString();
//            mAuth = FirebaseAuth.getInstance();
//            FirebaseUser currentUser = mAuth.getCurrentUser();
//            String userId = currentUser.getUid();
//
//
//            Map<String, Object> item = new HashMap<>();
//
//            item.put("name", inputName);
//            item.put("price", price);
//            item.put("condition", condition);
//            item.put("category", category);
//            item.put("user", userId);
//            //item.put("image", downloadUrl);
//
//            final Intent intent = new Intent(this, SellActivity.class);
//
//            db.collection("items")
//                    .add(item)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            startActivity(intent);
//                            Log.d("dbSuccess", "DocumentSnapshot written with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("dbFailure", "Error adding document", e);
//                        }
//                    });
//        }

    }




}
