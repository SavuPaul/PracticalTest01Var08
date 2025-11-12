package ro.pub.cs.systems.eim.practicaltest01var08.service;

import android.content.Context;
import android.content.Intent;

import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context;
    private boolean isRunning = true;
    private String answer = null;
    private Random random = new Random();

    // Define extra key for broadcast
    private final String BROADCAST_EXTRA = "extra";

    public ProcessingThread(Context context, String answer) {
        this.answer = answer;
        this.context = context;
    }

    @Override
    public void run() {
        while (isRunning) {
            constructString();
            sleepThread();
        }
    }

    private void constructString() {
        String newString = "";
        Intent intent = new Intent();
        intent.setAction(BROADCAST_EXTRA);

        int lengthAnswer = this.answer.length();
        for (int i = 0; i < lengthAnswer; i++) {
            newString = newString.concat("*");
        }

        int randomPos = random.nextInt(lengthAnswer);

        // Replace
        newString = newString.substring(0, randomPos) + this.answer.charAt(randomPos) + newString.substring(randomPos + 1);

        intent.putExtra("newString", newString);

        context.sendBroadcast(intent);
    }

    private void sleepThread() {
        try {
            Thread.sleep(5000); // 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
