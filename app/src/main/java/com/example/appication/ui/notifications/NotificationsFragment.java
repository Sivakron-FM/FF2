package com.example.appication.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appication.R;
import com.example.appication.ui.ViewAdaptor.BaseItem;
import com.example.appication.ui.ViewAdaptor.MailViewItem;
import com.example.appication.ui.ViewAdaptor.MainAdapter;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View root;
    private RecyclerView recyclerView;
    private MainAdapter adapter;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        View v = getLayoutInflater().inflate(R.layout.address, null);
        recyclerView = root.findViewById(R.id.data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new MainAdapter();
        adapter.setItemList(createItem());
        recyclerView.setAdapter(adapter);
        return root;
    }

        private List<BaseItem> createItem() {
            List<BaseItem> itemList = new ArrayList<>();
                itemList.add(new MailViewItem()
                .setMailImageView(R.drawable.ic_mail_black_24dp)
                .setMailTitleView("New message")
                .setMailTextView("ข้อมูลการส่งสินค้า"));
            return itemList;
        }





}








