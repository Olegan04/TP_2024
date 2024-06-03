package com.example.pushuptrecker.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Date;

public class Client extends Thread{
    private final int PORT = 3030;
    private final String SERVER_IP = "10.114.7.4";
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public Client() {
        start();
    }

    private synchronized void sendMessageToServer(String message) throws IOException {
        out.write(message);
        out.flush();
    }

    private synchronized String receiveCallback() throws IOException {
        return in.readLine();
    }

    public static void main(String[] args){
        new Client();
    }

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket(SERVER_IP, PORT);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while(true) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } finally {
                if (clientSocket != null) clientSocket.close();
                if (out != null) out.close();
                if (in != null) in.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private final byte ID_REQUEST_LOGIN = 100;
    private final String LOGIN_ERROR = "error";
    public int getIdAfterLogin(String email, String password) throws IOException {
        this.notify();
        sendMessageToServer(ID_REQUEST_LOGIN + "#" + email + "#" + password);
        String callback = receiveCallback();
        if (callback != null && Integer.parseInt(callback) != -1) {
            if(callback.equals(LOGIN_ERROR)){
                return 0;
            }
            int id = Integer.parseInt(callback);
            return id;
        }
        throw new EOFException();
    }
    private final byte ID_REGISTRATION_REQUEST = 101;
    public int getIdAfterRegistration(String name, String password, Date birthDate,
                                 String sex, String country, String city,
                                 String email) throws IOException {
        this.notify();
        sendMessageToServer(ID_REGISTRATION_REQUEST + email + password + name +
                birthDate + sex + country + city);
        String callback = receiveCallback();
        if (callback != null && Integer.parseInt(callback) != -1) {
            int id = Integer.parseInt(callback);
            return id;
        }
        throw new EOFException();
    }
    //TODO
    private final byte PROFILE_INFORMATION_REQUEST = 102;
    public String[] getProfileInformation(int id) throws IOException {
        this.notify();
        sendMessageToServer(String.valueOf(PROFILE_INFORMATION_REQUEST) + String.valueOf(id));
        String callback = receiveCallback();
        String[] information = new String[0];
        if (callback != null && Integer.parseInt(callback) != -1) {

        }
        throw new EOFException();
    }
    //TODO
    private final byte PROFILE_INFORMATION_POST = 103;
    public void updateProfileInformation(){
    }
    //TODO
    private final byte ID_STANDARD_TRAINING = 1;
    private final byte ID_WIDE_TRAINING = 2;
    private final byte ID_NARROW_TRAINING = 3;
    private final byte ID_GRASSHOPPERS_TRAINING = 4;
    private final byte ID_FISTS_TRAINING = 5;
    private final byte DAYS_IN_ROW_POST = 105;
    public void updateDayInRow(){
    }
    //TODO
    private final byte CALIBRATION_REQUEST = 106;
    public double getCalibration(){
        return 0;
    }
    //TODO
    private final byte CALIBRATION_POST = 107;
    public void updateCalibration(){
    }
    //TODO
    private final byte PUSH_UP_QUANTITY_REQUEST = 108;
    public int getPushUpsQuantity(){
        return 0;
    }
    //TODO
    private final byte PUSH_UP_QUANTITY_POST = 109;
    public void updatePushUpsQuantity(){
    }
}
