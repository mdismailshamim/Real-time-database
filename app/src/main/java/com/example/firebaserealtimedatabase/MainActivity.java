package com.example.firebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText nameEtv,genderEtv,phoneNumberEtv;
    private Button saveBtn;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        onClicked();
        initRecyclerView();
        getDatafromDB();
    }

    private void initialize() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        userList = new ArrayList<>();
        nameEtv = findViewById(R.id.nameEtv);
        genderEtv = findViewById(R.id.genderEtv);
        phoneNumberEtv = findViewById(R.id.phoneNumberEtv);
        saveBtn = findViewById(R.id.saveBtn);
        recyclerView = findViewById(R.id.recyclerViewId);
    }
    private void onClicked() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEtv.getText().toString();
                String gender = genderEtv.getText().toString();
                String phoneNumer = phoneNumberEtv.getText().toString();
                saveToDB(new User(name,gender,phoneNumer));
            }
        });
    }
    private void initRecyclerView() {
        //if need input single value in list  --Start--
        //userList.add(new User("Shamim","Male","01928431438"));
        //                       --end--
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(userList,this);
        recyclerView.setAdapter(customAdapter);
    }

    private void saveToDB(User user) {
        DatabaseReference userDB =firebaseDatabase.getReference().child("UserList");
        String userid = userDB.push().getKey();
        user.setUserId(userid);
        userDB.child(userid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully Saved!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getDatafromDB() {
        DatabaseReference userDB = firebaseDatabase.getReference().child("UserList");
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    userList.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        //if need only one child(like name) --start--
                        //String name = data.child("name").getValue().toString();
                        //--end--
                        User user = data.getValue(User.class);
                        userList.add(user);
                        customAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

