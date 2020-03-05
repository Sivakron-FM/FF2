package com.example.appication.ui.ViewAdaptor;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appication.R;

public class MailViewHolder extends BaseViewHolder {

    private ImageView imageView;
    private TextView titleView;
    private TextView textView;

    public MailViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.mail);
        titleView = (TextView) itemView.findViewById(R.id.Title);
        textView = (TextView) itemView.findViewById(R.id.Text);

    }

    public void setImageView (int image) {
        imageView.setImageResource(image);
    }

    public void setTitleView (String title) {
        titleView.setText(title);
    }

    public void setTextView (String text) {
        textView.setText(text);
    }
}
