package com.example.pm_bluetooth_commander;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String HC_05_ADDR = "98:D3:51:FD:7F:10";
//    private static final String HC_05_ADDR = "98:D3:31:FC:1E:60";
    /* God bless this guy https://arduino.stackexchange.com/questions/31461/identify-uuid-of-hc-06 */
    private static final UUID portUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String PLAY_COMMAND = "A";
    private static final String PAUSE_COMMAND = "B";
    private static final String NEXT_COMMAND = "C";
    private static final String PREVIOUS_COMMAND = "D";

    private BluetoothDevice controller = null;
    private OutputStream outputStream = null;

    public void sendCommand(String command) {
        if (outputStream == null) {
            Toast.makeText(this, "Conectati modulul bluetooth!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            outputStream.write(command.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            Log.d("[COMMANDER]", "[SENT MESSAGE: " + command);
        }
    }

    public void connectToController(View view) {
        Log.d("[COMMANDER]", "[Connecting to controller...]");
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Acest device nu suporta Bluetooth!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enable, 0);
        }

        Set devices = bluetoothAdapter.getBondedDevices();
        if (devices.isEmpty()) {
            Toast.makeText(this, "Conectati modulul bluetooth!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean found = false;
        for (Object obj : devices) {
            BluetoothDevice device = (BluetoothDevice) obj;
            if (device.getAddress().equals(HC_05_ADDR)) {
                found = true;
                controller = device;
            }
        }

        if (!found) {
            Toast.makeText(this, "Nu a putut fi gasit modulul bluetooth!", Toast.LENGTH_SHORT).show();
            return;
        }

        BluetoothSocket socket = null;
        try {
            socket = controller.createRfcommSocketToServiceRecord(portUUID);
            socket.connect();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(this, "Conexiunea nu a putut fi realizata! :(", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
    /* private static final String HC_05_ADDR = "50:97:4A:0E:CF:32"; */
            return;
        }

        Toast.makeText(this, "Conexiunea a fost efectuata cu succes!", Toast.LENGTH_SHORT).show();
    }

    public void sendPlayMessage(View view) {
        Log.d("[COMMANDER]", "[Sending PLAY message to controller...]");
        sendCommand(PLAY_COMMAND);
        Toast.makeText(this, "Keep hitting that play button!", Toast.LENGTH_SHORT).show();
    }

    public void sendPauseMessage(View view) {
        Log.d("[COMMANDER]", "[Sending PAUSE message to controller...]");
        sendCommand(PAUSE_COMMAND);
        Toast.makeText(this, "Pause? Me no like >:(", Toast.LENGTH_SHORT).show();
    }

    public void sendNextSongMessage(View view) {
        Log.d("[COMMANDER]", "[Sending NEXT SONG message to controller...]");
        sendCommand(NEXT_COMMAND);
        Toast.makeText(this, "Skip, skip, skipping...", Toast.LENGTH_SHORT).show();
    }

    public void sendPreviousSongMessage(View view) {
        Log.d("[COMMANDER]", "[Sending PREVIOUS SONG message to controller...]");
        sendCommand(PREVIOUS_COMMAND);
        Toast.makeText(this, "Liked that one, don't we?", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
