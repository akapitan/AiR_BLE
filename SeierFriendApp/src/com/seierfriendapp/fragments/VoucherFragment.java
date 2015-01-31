package com.seierfriendapp.fragments;

import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import com.activeandroid.query.Select;
import hr.foi.seierfriendapp.R;
import com.seierfriendapp.localdata.Voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VoucherFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean queryOk = false;
        List<Voucher> vouchers = null;
        Resources res = getResources();

        try {
            vouchers = new Select().all().from(Voucher.class).execute();
            queryOk = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (vouchers.size() > 0 && queryOk == true) {
            Log.d("VOCHERS LIST: ", vouchers.toString());
            vouchers = (ArrayList<Voucher>) vouchers;
        } else if (vouchers.size() == 0 && queryOk == true) {
            Log.d("VOUCHERS: ", "No vouchers in local database");
        }

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (Voucher v : vouchers) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("voucher_name", v.getName());
            hm.put("voucher_store", v.getVoucherStoreName() + " | " + v.getVoucherStoreLocation());
            hm.put("voucher_to", res.getString(R.string.label_voucher_to) + " " + v.getVoucherValidUntil());
            aList.add(hm);
        }


        String[] from = {"voucher_name", "voucher_store", "voucher_to"};

        // Ids of views in listview_layout
        int[] to = {R.id.voucher_name, R.id.voucher_store, R.id.voucher_to};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_voucher, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}


