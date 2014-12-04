package com.example.fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.core.BaseApplication;
import com.example.seierfriendapp.MainActivity;
import com.example.seierfriendapp.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class BleFragment extends Fragment implements Observer {

    BaseApplication baseApp;
    ArrayList<String> listaUredjaja;
    ArrayAdapter<String> listAdapter;
    ListView lv;

    public BleFragment(){}

    public BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ble, container, false);
        return rootView;
    }

   /* @Override
    public void onActivityCreated(){
        super.onActivityCreated();
        ((MainActivity)this.getActivity()).getBaseApp();
    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        baseApp = ((MainActivity)this.getActivity()).getBaseApp();


        baseApp.setCurrentContext(getView().getContext());
        baseApp.getObserver().addObserver(this);

        Button On = (Button) view.findViewById(R.id.btnBle1);
        Button Off=(Button) view.findViewById(R.id.btnBle3);
        Button Scan=(Button) view.findViewById(R.id.btnBle2);
        lv=(ListView) view.findViewById(R.id.listView);


        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on(view);
            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan(view);
            }
        });



        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                off(view);
            }
        });

        listaUredjaja=new ArrayList<String>();
        listAdapter=new ArrayAdapter<String>(baseApp.getCurrentContext(), R.layout.simplerow, listaUredjaja);
    }

    public void on(View view){
        try{
            if(BA.isEnabled() == false){
                Intent turnOn=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn,0);
                Log.e("BUTTON ON", "BLUETOOTH ON");
                Toast.makeText(getView().getContext(), "Bluetooth - turning on", Toast.LENGTH_LONG).show();
            }else {
                Log.e("BUTTON ON", "BLUETOOTH ALREADY ON");;
                Toast.makeText(getView().getContext(), "Bluetooth - already on", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getView().getContext(), "Bluetooth - error", Toast.LENGTH_LONG).show();
            Log.e("BUTTON ON", "BLUETOOTH ERROR");
        }
    }

    public void Scan(View view){
        baseApp.bleScan.scanForDevices(baseApp.getCurrentContext());
    }

    public void off(View view){
        BA.disable();
        //Toast.makeText(this, "Gasim Bluetooth", Toast.LENGTH_LONG).show();
        Toast.makeText(getView().getContext(), "Bluetooth - turning off", Toast.LENGTH_LONG).show();
        Log.e("BUTTON ON", "BLUETOOTH OFF");
    }

    @Override
    public void update(Observable observable, Object o) {

        /*MyActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MyActivity.this, "MAC: ", Toast.LENGTH_SHORT).show();
            }
        });*/getActivity().runOnUiThread(new Runnable() {
            public void run() {
                listaUredjaja.clear();
                for (int i = 0; i < baseApp.bleScan.getDeviceList().size(); i++) {
                    BluetoothDevice obj = baseApp.bleScan.getDeviceList().valueAt(i);
                    listaUredjaja.add("Device number: " + (i+1) +" device name: " + obj.getName() + " MAC: " + obj.getAddress());
                }
                Log.e("PRVA ADRESA: ", listaUredjaja.get(0).toString());
                Log.e("BROJ UREDJAJA: ", String.valueOf(baseApp.bleScan.getDeviceList().size()));
                lv.setAdapter(listAdapter);
            }
        });
        Log.d("UPDATE OCC55555555555555555555555555555555555555555555555555555UREDdd", "I am notified" );
    }

}
