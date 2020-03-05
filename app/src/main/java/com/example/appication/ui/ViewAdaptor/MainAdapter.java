package com.example.appication.ui.ViewAdaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appication.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BaseItem> itemList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v ;

        if (viewType == Viewtype.TYPE_MAIL_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_view, parent, false);
            return new MailViewHolder(v);
        }

        throw new RuntimeException("Type is not match");
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        BaseItem baseItem = itemList.get(position);
        if (holder instanceof MailViewHolder) {
             MailViewItem mailViewItem = (MailViewItem) baseItem;
            ((MailViewHolder) holder).setImageView(mailViewItem.getMailImageView());
            ((MailViewHolder) holder).setTitleView(mailViewItem.getMailTitleView());
            ((MailViewHolder) holder).setTextView(mailViewItem.getMailTextView());
        }
    }


    @Override
    public int getItemCount() {
        if (!itemList.isEmpty() || itemList != null) {
            return itemList.size();
        }

        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }


    public void setItemList (List<BaseItem> itemList) {
        this.itemList = itemList;

        notifyDataSetChanged();
    }


}
