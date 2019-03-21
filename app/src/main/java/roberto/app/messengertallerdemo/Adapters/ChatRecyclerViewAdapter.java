package roberto.app.messengertallerdemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import roberto.app.messengertallerdemo.Objects.ChatMessage;
import roberto.app.messengertallerdemo.R;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>{

    ArrayList<ChatMessage> messages;
    Context context;
    String userId;

    public ChatRecyclerViewAdapter(Context context, ArrayList<ChatMessage> messages, String userId) {
        this.context = context;
        this.messages = messages;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recycler_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //getting layout and its params
        LinearLayout layout = viewHolder.messageHolder;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();

        //changing layout view
        //we sent the message
        if(messages.get(position).getSenderId().equals(userId)) {
            //we sent the message
            params.setMargins(160, 0, 30, 0);
            params.gravity = Gravity.RIGHT;
            layout.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_chat_box));
            viewHolder.sender.setText("Yo");
        }
        else {
            //other sent the message
            params.setMargins(30, 0, 160, 0);
            layout.setBackground(ContextCompat.getDrawable(context, R.drawable.gray_chat_box));
            params.gravity = Gravity.LEFT;
            viewHolder.sender.setText(messages.get(position).getSenderId());
        }
        //setting up final params
        layout.setLayoutParams(params);
        layout.setPadding(20, 20, 20, 20);

        //setup message text
        viewHolder.message.setText(messages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //declaring message individual view
        TextView sender;
        TextView message;
        LinearLayout messageHolder;
        public ViewHolder(View itemView) {
            super(itemView);
            //setup text views and linear layout
            sender = itemView.findViewById(R.id.sender);
            message = itemView.findViewById(R.id.message);
            messageHolder = itemView.findViewById(R.id.message_holder);
        }
    }
}
