package com.prozy.recycletrash.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prozy.recycletrash.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class JualActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Uri imageUri;

    private String  CategoryName,Price,namauser, nomorhp, jenis_sampah,namabarang, saveCurrentDate, saveCurrentTime,profilimage;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductNametoko, InputProductDescription, InputProductbarang,InputProductharga,InputNohp;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private ImageView back_btn;
    private Button kirim_jual;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);
        back_btn =  findViewById(R.id.back);
        kirim_jual = findViewById(R.id.kirim_jual_btn);

        CategoryName = getIntent().getExtras().get("kategori").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Gambar Products");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("produk");

        spinner = findViewById(R.id.spinner_bahan);
        InputProductImage = findViewById(R.id.update);
        InputProductbarang = findViewById(R.id.nama_barang);
        InputProductharga = findViewById(R.id.harga_barang);
        InputNohp = findViewById(R.id.nomor_telpon);
        AddNewProductButton = findViewById(R.id.kirim_jual_btn);
        loadingBar = new ProgressDialog(this);

        namauser = getIntent().getExtras().get("profilnama").toString();
//        profilimage = getIntent().getExtras().get("profilimage").toString();

        spinner();


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(JualActivity.this, MenuMainActivity.class);
                startActivity(intentBack);
            }
        });

        kirim_jual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(JualActivity.this, "Bahan : "+spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });

    }



    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {
        nomorhp = InputNohp.getText().toString();
        jenis_sampah = spinner.getSelectedItem().toString();
        namabarang = InputProductbarang.getText().toString();
        Price = InputProductharga.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "gambar kosong", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(namabarang)){

            Toast.makeText(this, "nama barang kosong", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(jenis_sampah)){

            Toast.makeText(this, "jenis belum dipilih", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(nomorhp)){

            Toast.makeText(this, "Nomor hp belum di input", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Price)){

            Toast.makeText(this, "harga belum di isi", Toast.LENGTH_SHORT).show();
        }else {

            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Unggah Barang");
        loadingBar.setMessage("Tolong Tunggu unggahan Sedang di proses.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime + "TheFrozy" + namauser;


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(JualActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(JualActivity.this, "sukses unggah barang", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(JualActivity.this, "berhasil ", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {

        HashMap<String , Object> productMap= new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("namabarang", namabarang);
        productMap.put("image", downloadImageUrl);
        productMap.put("kategori", CategoryName);
        productMap.put("price", Price);
        productMap.put("nomorhp", nomorhp);
        productMap.put("jenis", jenis_sampah);
  //      productMap.put("profilimage",profilimage);
        productMap.put("profilname", namauser);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(JualActivity.this, MenuMainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(JualActivity.this, "sukses ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(JualActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void spinner(){
        spinner = findViewById(R.id.spinner_bahan);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bahan_bahan, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Anda Belum Memilih Bahan", Toast.LENGTH_SHORT).show();
    }
}
