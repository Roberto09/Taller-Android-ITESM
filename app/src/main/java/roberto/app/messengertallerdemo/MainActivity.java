package roberto.app.messengertallerdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import roberto.app.messengertallerdemo.Adapters.GroupsRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {

    //Firebase References
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference rootReference = database.getReference();
    private DatabaseReference groupsReference = rootReference.child("Chats");


    //RecyclerView
    RecyclerView groupsRecyclerView;
    LinearLayoutManager groupsManager;
    GroupsRecyclerViewAdapter groupsAdapter;

    ArrayList<String> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup recyclerView
        groups = new ArrayList<>();
        groupsRecyclerView = findViewById(R.id.groups);
        groupsManager = new LinearLayoutManager(this);
        groupsAdapter = new GroupsRecyclerViewAdapter(this, groups);
        groupsRecyclerView.setLayoutManager(groupsManager);
        groupsRecyclerView.setAdapter(groupsAdapter);


        //adding elements to recycler view
        groupsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //adding group and notifying recycler view
                groups.add(dataSnapshot.getKey());
                groupsAdapter.notifyDataSetChanged();
            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}