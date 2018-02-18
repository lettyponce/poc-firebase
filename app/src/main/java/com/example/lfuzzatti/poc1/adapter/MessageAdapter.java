package com.example.lfuzzatti.poc1.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lfuzzatti.poc1.R;
import com.example.lfuzzatti.poc1.model.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    ImageView photoMessage;
    TextView textMessage;
    TextView ownerMessage;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater()
                            .inflate(R.layout.item_message, parent, false);
        }

        //Loading widget
        initializeViews(convertView);

        //Get message posistion
        Message message = getItem(position);

        //verify type message
        boolean isPhoto = message.getPhotoUrl() != null;

        if (isPhoto) {
            photoMessage.setVisibility(View.VISIBLE);
            textMessage.setVisibility(View.GONE);

            Glide.with(photoMessage.getContext())
                        .load(message.getPhotoUrl())
                        .into(photoMessage);
        } else {
            photoMessage.setVisibility(View.GONE);
            textMessage.setVisibility(View.VISIBLE);
            textMessage.setText(message.getMessage());
        }

        ownerMessage.setVisibility(View.VISIBLE);
        ownerMessage.setText(message.getOwner());

        return convertView;
    }

    private void initializeViews(@Nullable View convertView) {
        photoMessage = (ImageView) convertView.findViewById(R.id.photoMessage);
        textMessage = (TextView) convertView.findViewById(R.id.message);
        ownerMessage = (TextView) convertView.findViewById(R.id.ownerMessage);
    }
}
