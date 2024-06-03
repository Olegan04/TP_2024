package com.example.pushuptrecker.ui.settings;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BLEScanCallback extends ScanCallback {
    List <BluetoothDevice> devices;
    public BLEScanCallback(){
        devices = new ArrayList<>();
    }
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        switch (callbackType){
            case(ScanSettings.CALLBACK_TYPE_ALL_MATCHES):{

            }
            break;
            case(ScanSettings.CALLBACK_TYPE_FIRST_MATCH):{
                devices.add(result.getDevice());
            }
            break;
            case(ScanSettings.CALLBACK_TYPE_MATCH_LOST):{

            }
            break;
        }
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {

    }
    @Override
    public void onScanFailed(int errorCode) {
        Log.println(Log.ERROR, "BLE Scanning Failed", "Scanning failed");
    }

    public List<BluetoothDevice> getDevices() {
        return devices;
    }
}
