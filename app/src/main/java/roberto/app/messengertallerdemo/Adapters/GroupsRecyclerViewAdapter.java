package roberto.app.messengertallerdemo.Adapters;

import android.content.Context;
import android.content.Intent;
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

import roberto.app.messengertallerdemo.ChatActivity;
import roberto.app.messengertallerdemo.R;

public class GroupsRecyclerViewAdapter extends RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> groups;
    Context context;

    public GroupsRecyclerViewAdapter(Context context, ArrayList<String> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aqui va el view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_recycler_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //setting up text of the caht
        viewHolder.groupName.setText(groups.get(position));
        //setting up click listener for group
        viewHolder.groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on click will open ChatActivity passing as parameter the group name
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("group", groups.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //declaring message individual view
        TextView groupName;

        public ViewHolder(View itemView) {
            //setup text views and linear layout
            super(itemView);
            groupName = itemView.findViewById(R.id.group);
        }
    }
}