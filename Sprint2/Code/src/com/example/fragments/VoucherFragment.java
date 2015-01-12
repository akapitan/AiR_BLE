package com.example.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import com.example.seierfriendapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VoucherFragment extends ListFragment {

    // Array of strings storing country names
    String[]  discounts = new String[] {
            "Discount 1",
            "Discount 2",
            "Discount 3",
            "Discount 4",
            "Discount 5",
            "Discount 6",
            "Discount 7",
            "Discount 7",
            "Discount 8",
            "Discount 9"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", discounts[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "txt" };

        // Ids of views in listview_layout
        int[] to = { R.id.txt};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_voucher, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
