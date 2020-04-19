package com.example.blood;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class List_Of_Users extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__of__users);
        lv=findViewById(R.id.list_view);
        final String check_blood=getIntent().getStringExtra("blood");
        String check_near=getIntent().getStringExtra("near");
        if(check_near.equals("1")) {


            FirebaseDatabase.getInstance().getReference("any").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        user_information p = dataSnapshot.getValue(user_information.class);
                        Query query = FirebaseDatabase.getInstance().getReference("users").child(p.getPost()).orderByChild("blood").equalTo(check_blood);
                        FirebaseListOptions<user_information> options = new FirebaseListOptions.Builder<user_information>()
                                .setLifecycleOwner(List_Of_Users.this)
                                .setLayout(R.layout.user_list)
                                .setQuery(query, user_information.class).build();
                        adapter = new FirebaseListAdapter(options) {
                            @Override
                            protected void populateView(View v, Object model, int position) {
                                TextView name = v.findViewById(R.id.list_name);
                                TextView phone = v.findViewById(R.id.list_phone);
                                TextView age = v.findViewById(R.id.list_age);
                                TextView gender = v.findViewById(R.id.list_gender);
                                TextView adress = v.findViewById(R.id.list_adress);
                                TextView blood = v.findViewById(R.id.list_blood);
                                TextView userID = v.findViewById(R.id.userID);
                                final ImageView prof = v.findViewById(R.id.list_dp);
                                TextView email = v.findViewById(R.id.list_email);
                                final user_information users = (user_information) model;
                                //calculation part if user is pressed near by options
                                Picasso.get().load(users.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(prof, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(users.getUrl()).into(prof);
                                    }
                                });
                                name.setText("Name:" + users.getName());
                                adress.setText(users.getAdress());
                                email.setText(users.getEmail());
                                phone.setText(users.getPhone());
                                age.setText("Age:" + users.getAge());
                                gender.setText("Gender:" + users.getGender());
                                blood.setText("Blood Group:" + users.getBlood());
                                userID.setText(users.getUserid());


                            }
                        };
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //call next page and send this user id
                                String userid = String.valueOf(((TextView) view.findViewById(R.id.userID)).getText());
                                find_and_confirm_popup(userid);

                                Toast.makeText(List_Of_Users.this, ((TextView) view.findViewById(R.id.userID)).getText(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
           // for all user list

                        Query query = FirebaseDatabase.getInstance().getReference("any").orderByChild("blood").equalTo(check_blood);
                        FirebaseListOptions<user_information> options = new FirebaseListOptions.Builder<user_information>()
                                .setLifecycleOwner(List_Of_Users.this)
                                .setLayout(R.layout.user_list)
                                .setQuery(query, user_information.class).build();
                        adapter = new FirebaseListAdapter(options) {
                            @Override
                            protected void populateView(View v, Object model, int position) {
                                TextView name = v.findViewById(R.id.list_name);
                                TextView phone = v.findViewById(R.id.list_phone);
                                TextView age = v.findViewById(R.id.list_age);
                                TextView gender = v.findViewById(R.id.list_gender);
                                TextView adress = v.findViewById(R.id.list_adress);
                                TextView blood = v.findViewById(R.id.list_blood);
                                TextView userID = v.findViewById(R.id.userID);
                                final ImageView prof = v.findViewById(R.id.list_dp);
                                TextView email = v.findViewById(R.id.list_email);
                                final user_information users = (user_information) model;
                                //calculation part if user is pressed near by options
                                Picasso.get().load(users.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(prof, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(users.getUrl()).into(prof);
                                    }
                                });
                                name.setText("Name:" + users.getName());
                                adress.setText(users.getAdress());
                                email.setText(users.getEmail());
                                phone.setText(users.getPhone());
                                age.setText("Age:" + users.getAge());
                                gender.setText("Gender:" + users.getGender());
                                blood.setText("Blood Group:" + users.getBlood());
                                userID.setText(users.getUserid());


                            }
                        };
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //call next page and send this user id
                                final String userid = String.valueOf(((TextView) view.findViewById(R.id.userID)).getText());
                                find_and_confirm_popup(userid);

                                Toast.makeText(List_Of_Users.this, ((TextView) view.findViewById(R.id.userID)).getText(), Toast.LENGTH_LONG).show();
                            }
                        });



        }

    }

    public void find_and_confirm_popup(final String id){
        final Dialog MyDialog = new Dialog(List_Of_Users.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_find_and_confirm_popup);
        Button hello = (Button) MyDialog.findViewById(R.id.hello);
        Button close = (Button) MyDialog.findViewById(R.id.close);
        TextView cut = (TextView) MyDialog.findViewById(R.id.cut);
        hello.setEnabled(true);
        close.setEnabled(true);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user_information user=dataSnapshot.getValue(user_information.class);
                        String phone_number=user.getPhone();
                        //call your intent and transfer phone number to that page
                        Intent intent = new Intent(List_Of_Users.this, sms.class);
                        intent.putExtra("ph",phone_number);
                        startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                 MyDialog.dismiss();
                Toast.makeText(List_Of_Users.this, "Hari work", Toast.LENGTH_SHORT).show();
            }
        });
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(List_Of_Users.this, "charith work", Toast.LENGTH_SHORT).show();

                MyDialog.cancel();
                //go to Map Page  (Charith work)
                FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user_information curent_user=dataSnapshot.getValue(user_information.class);
                        FirebaseDatabase.getInstance().getReference(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user_information requested_user=dataSnapshot.getValue(user_information.class);
                                //curent user andre nanu, requested use andre donor,,,getLongi() getLatti() etc function use madi ning bekadaddu thegi and next intent kari

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }

}
