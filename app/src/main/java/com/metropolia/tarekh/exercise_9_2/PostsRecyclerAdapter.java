package com.metropolia.tarekh.exercise_9_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Post> postsList;


    private PostsRecyclerAdapter(Context context, List<Post> postsList) {
        this.context = context;
        this.postsList = postsList;

    }

    @Override
    public PostsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts_item_layout, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(PostsRecyclerAdapter.ViewHolder holder, int position) {
        Post post = postsList.get(position);

        holder.displayName.setText(post.getDisplayName());
        holder.timestamp.setText(post.getTimestamp());
        holder.text.setText(post.getText());


    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String userID;
        private TextView displayName;
        private TextView timestamp;
        public TextView text;


        private ViewHolder(View view, Context c) {
            super(view);
            context = c;
            userID = null;
            displayName = view.findViewById(R.id.postDisplayName);
            timestamp = view.findViewById(R.id.timestampList);
            text = view.findViewById(R.id.postTextList);

        }
    }

}
