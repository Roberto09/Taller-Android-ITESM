package roberto.app.messengertallerdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import roberto.app.messengertallerdemo.Adapters.ChatRecyclerViewAdapter;
import roberto.app.messengertallerdemo.Objects.ChatMessage;

import static roberto.app.messengertallerdemo.MainActivity.rootReference;

public class ChatActivity extends AppCompatActivity {


    //EditText and Buttons
    EditText messageBox;
    Button send;
    TextView groupName;

    //RecyclerView
    ArrayList<ChatMessage> messages;
    RecyclerView messagesRecyclcerView;
    ChatRecyclerViewAdapter messagesRecyclerViewAdapter;
    LinearLayoutManager messagesRecyclerViewManager;

    //Database references
    private DatabaseReference chatReference;

    //User Info
    String userId = "Pancho";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //setup reference
        chatReference = rootReference.child("Chats").child(getIntent().getStringExtra("group"));

        //setup groupName
        groupName = findViewById(R.id.groupName);
        groupName.setText("Grupo: " + getIntent().getStringExtra("group"));

        //setup messages editText
        messageBox = findViewById(R.id.message);

        //seting up send button
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        //setup recycler view
        messages = new ArrayList<>();
        messagesRecyclcerView = findViewById(R.id.messages_recycler_view);
        messagesRecyclerViewManager = new LinearLayoutManager(this);
        messagesRecyclerViewAdapter = new ChatRecyclerViewAdapter(this, messages, userId);
        messagesRecyclcerView.setLayoutManager(messagesRecyclerViewManager);
        messagesRecyclcerView.setAdapter(messagesRecyclerViewAdapter);

        //adding to recycler view
        chatReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //getting infomration from individual message
                String senderId = dataSnapshot.child("senderId").getValue(String.class);
                String msg = dataSnapshot.child("message").getValue(String.class);
                //adding to messages and notifying adapter
                messages.add(new ChatMessage(senderId, msg));
                messagesRecyclerViewAdapter.notifyDataSetChanged();
                messagesRecyclcerView.smoothScrollToPosition(messages.size() - 1);
            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    void sendMessage() {
        //pushing message to firebase
        String msg = messageBox.getText().toString();
        ChatMessage userMessage = new ChatMessage(userId, msg);
        chatReference.push().setValue(userMessage);
        messageBox.setText("");
    }
}