package com.example.pushuptrecker.ui.settings;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pushuptrecker.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private final int BLUETOOTH_REQUEST = 11;
    private final int BLUETOOTH_SCAN_REQUEST = 21;
    private final int BLUETOOTH_ON_RESULT = 10;
    private View root;
    private FragmentSettingsBinding binding;
    private Button findDevicesBtn;
    private ListView devicesLV;

    String bluetoothPermission;
    String bluetoothScanPermission;
    private boolean isScanning;

    private BluetoothLeScanner bluetoothScanner;
    private Handler handler;
    private ArrayAdapter<BluetoothDevice> devicesLVAdapter;
    private List<BluetoothDevice> devices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = init(inflater, container);

        bluetoothPermission = Manifest.permission.BLUETOOTH;
        bluetoothScanPermission = Manifest.permission.BLUETOOTH_SCAN;

        bluetoothScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        handler = new Handler();

        findDevicesBtn.setOnClickListener(v -> {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, BLUETOOTH_REQUEST);
                findBLEDevices();
                for (BluetoothDevice device : devices) {
                    //Checks bluetooth scanning permission
                    if (!checkBluetoothScanPermission() || !checkBluetoothPermission()) {
                        requestBluetoothScanPermission();
                        requestBluetoothPermission();
                    }
                    Log.println(Log.INFO, "BLE device", device.getName() + device.getName());
                }
            }else{
                Intent requestBluetoothAccess = new Intent(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
                startActivityForResult(requestBluetoothAccess, BLUETOOTH_ON_RESULT);
            }
        });


        return root;
    }

    private View init(@NonNull LayoutInflater inflater, ViewGroup container) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        findDevicesBtn = binding.findDevicesBtn;
        devicesLV = binding.devicesList;
        devices = new ArrayList<>();
        devicesLVAdapter = new ArrayAdapter<BluetoothDevice>(this.getContext(),
                android.R.layout.simple_list_item_1, devices);
        devicesLV.setAdapter(devicesLVAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final int SCAN_PERIOD = 1000;
    private void findBLEDevices() {
        BLEScanCallback scanCallback = new BLEScanCallback();

        if (!isScanning) {
            // Stops scanning after a defined scan period.
            handler.postDelayed(() -> {
                isScanning = false;
                //Checks bluetooth scanning permission
                if (!checkBluetoothScanPermission()) {
                    requestBluetoothScanPermission();
                }
                bluetoothScanner.stopScan(scanCallback);
            }, SCAN_PERIOD);
            isScanning = true;
            bluetoothScanner.startScan(scanCallback);
        } else {
            isScanning = false;
            bluetoothScanner.stopScan(scanCallback);
        }
        devices.clear();
        devices.addAll(scanCallback.getDevices());
    }

    private void pickBLEDevice(){
        Intent i = new Intent("com.android.ble");

    }

    private boolean checkBluetoothPermission(){
        return ContextCompat.checkSelfPermission(this.getContext(), bluetoothPermission) ==
                (PackageManager.PERMISSION_GRANTED);
    }

    private void requestBluetoothPermission(){
        requestPermissions(new String[]{bluetoothPermission}, BLUETOOTH_REQUEST);
    }

    private boolean checkBluetoothScanPermission(){
        return ContextCompat.checkSelfPermission(this.getContext(), bluetoothScanPermission) ==
                (PackageManager.PERMISSION_GRANTED);
    }

    private void requestBluetoothScanPermission(){
        requestPermissions(new String[]{bluetoothScanPermission}, BLUETOOTH_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case BLUETOOTH_REQUEST:{
                if(grantResults.length > 0){
                    boolean bluetoothAccepted = checkBluetoothPermission();
                    if(!bluetoothAccepted){
                        Toast.makeText(this.getContext(), "Please Enable Bluetooth Permissions", Toast.LENGTH_LONG).show();
                        requestBluetoothPermission();
                    }
                }
            }
            break;
            case BLUETOOTH_SCAN_REQUEST:{
                if(grantResults.length > 0){
                    boolean bluetoothAccepted = checkBluetoothScanPermission();
                    if(!bluetoothAccepted){
                        Toast.makeText(this.getContext(), "Please Enable Bluetooth Scan Permissions", Toast.LENGTH_LONG).show();
                        requestBluetoothScanPermission();
                    }
                }
            }
        }
    }


}