package ro.pub.cs.systems.eim.practicaltest01var08;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.pub.cs.systems.eim.practicaltest01var08.service.PracticalTest01Var08Service;

public class PracticalTest01Var08PlayActivity extends AppCompatActivity {

    private String answerFromMain = null;
    private String newString = null;
    private final String BROADCAST_EXTRA = "extra";

    MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var08_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button checkButton = findViewById(R.id.check);
        checkButton.setOnClickListener(new ButtonClickListener());

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new ButtonClickListener());

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            answerFromMain = intent.getStringExtra("answer");
        }

        Intent intentService = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
        intentService.putExtra("answer", answerFromMain);
        getApplicationContext().startService(intentService);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var08Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.check) {
                EditText answer = findViewById(R.id.answer);
                String answerText = answer.getText().toString();
                if (answerFromMain.equals(answerText)) {
                    setResult(Activity.RESULT_OK, new Intent());
                    finish();
                } else {
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                }
            } else if (v.getId() == R.id.back) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_EXTRA);
        registerReceiver(messageBroadcastReceiver, filter);
        Log.d("MainActivity", "BroadcastReceiver registered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
        Log.d("MainActivity", "BroadcastReceiver unregistered");
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MessageBroadcastReceiver", "onReceive() called");
            // Receive the newString
            newString = intent.getStringExtra("newString");

            // Show the newString in a toast
            Toast.makeText(context, newString, Toast.LENGTH_SHORT).show();
        }
    }
}
