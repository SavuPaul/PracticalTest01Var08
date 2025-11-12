package ro.pub.cs.systems.eim.practicaltest01var08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {

    private String resultGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_var08_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                EditText riddle = findViewById(R.id.riddle);
                EditText answer = findViewById(R.id.answer);

                String riddleText = riddle.getText().toString();
                String answerText = answer.getText().toString();

                if (!riddleText.isEmpty() && !answerText.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08PlayActivity.class);
                    intent.putExtra("answer", answerText);
                    startActivityForResult(intent, 1);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Victory", Toast.LENGTH_SHORT).show();
                resultGame = "Victory";
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                resultGame = "Fail";
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("resultGame", resultGame);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("resultGame")) {
           resultGame = savedInstanceState.getString("resultGame", "NULL");

            Toast.makeText(this, resultGame, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Result of game before destroying: " + resultGame);
        }
    }
}