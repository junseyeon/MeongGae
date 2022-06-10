package com.example.meong_gae;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meong_gae.databinding.ActivityDogRegistrationBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.meong_gae.ui.mypage.MypageFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DogRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private final int GALLERY_CODE = 10;
    private ActivityDogRegistrationBinding binding;
    private FirebaseStorage storage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDogRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();

        ArrayAdapter<CharSequence> sexadapter = ArrayAdapter.createFromResource(this,R.array.dogsex, android.R.layout.simple_spinner_item);
        sexadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogsexspinner.setAdapter(sexadapter);
        binding.dogsexspinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> sizeadapter = ArrayAdapter.createFromResource(this,R.array.dogsize, android.R.layout.simple_spinner_item);
        sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dogsizespinner.setAdapter(sizeadapter);
        binding.dogsizespinner.setOnItemSelectedListener(this);

        boolean hasCamPerm = checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasWritePerm = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!hasCamPerm || !hasWritePerm)  // 권한 없을 시  권한설정 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);



        binding.dogimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbum();
            }
        });
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.dogsizespinner.getSelectedItem().toString() != null && binding.dogsexspinner.getSelectedItem().toString() != null &&
                        binding.dognameedt.getText() != null && binding.edtAge.getText() != null && binding.dogdescription.getText() != null)
                {
                    Toast.makeText(DogRegistration.this, "강아지 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    regis();
                }
                else
                {
                    Toast.makeText(DogRegistration.this, "빠진 부분이 없는지 다시 확인해주세요\n모든사항 필수 입력입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

private void regis()
{
    String dogname = binding.dognameedt.getText().toString();
    String dossize = binding.dogsizespinner.getSelectedItem().toString();
    String dogsex= binding.dogsexspinner.getSelectedItem().toString();
    String dogage= binding.edtAge.getText().toString();
    String dogdesc = binding.dogdescription.getText().toString();

    HashMap<Object,String> dogmap = new HashMap<>();

    dogmap.put("dogname",dogname);
    dogmap.put("dossize",dossize);
    dogmap.put("dogsex",dogsex);
    dogmap.put("dogage",dogage);
    dogmap.put("dogdesc",dogdesc);

    db.collection("TDog")
            .add(dogmap)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(DogRegistration.this, "등록에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull @NotNull Exception e) {
            Toast.makeText(DogRegistration.this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    });
}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void loadAlbum()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE)
        {
            Uri filepath = data.getData();
            StorageReference storageRef = storage.getReference();
            StorageReference riverRef = storageRef.child("dog_img/1.png");
            UploadTask uploadTask = riverRef.putFile(filepath);

            try {
                InputStream in = getContentResolver().openInputStream(filepath);
                Bitmap img= BitmapFactory.decodeStream(in);
                in.close();
                binding.dogimage.setImageBitmap(img);

            }catch (Exception e)
            {
              e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(DogRegistration.this, "사진업로드에 실패", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(DogRegistration.this, "사진업로드에 성공", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


}
