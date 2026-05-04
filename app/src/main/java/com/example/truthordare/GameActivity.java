package com.example.truthordare;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {

    // Views from activity_game.xml.
    private TextView currentPlayerTextView;
    private TextView questionTextView;
    private Button truthButton;
    private Button dareButton;

    private ArrayList<String> players;

    // This index stores whose turn it is.
    private int currentPlayerIndex = 0;
    private Random random;
    private ToneGenerator toneGenerator;

    // Predefined truth questions.
    private String[] truths = {
            "What is your funniest childhood memory?",
            "What is one secret talent you have?",
            "Who was your first crush?",
            "What is the most embarrassing thing you have done?",
            "What is one thing you are afraid of?",
            "Have you ever lied to avoid homework?",
            "What is your favorite memory with your friends?",
            "What is one habit you want to change?"
    };

    // Predefined dare tasks.
    private String[] dares = {
            "Sing a song for 20 seconds.",
            "Do 10 jumping jacks.",
            "Act like your favorite movie character.",
            "Speak in a funny voice until your next turn.",
            "Dance for 15 seconds.",
            "Tell a joke to everyone.",
            "Make a funny face for 10 seconds.",
            "Say the alphabet backwards as far as you can."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currentPlayerTextView = findViewById(R.id.currentPlayerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        truthButton = findViewById(R.id.truthButton);
        dareButton = findViewById(R.id.dareButton);
        Button nextTurnButton = findViewById(R.id.nextTurnButton);

        // Receive the player list sent from MainActivity.
        players = getIntent().getStringArrayListExtra("playerList");
        random = new Random();
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);

        showCurrentPlayer();

        truthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRandomTruth();
            }
        });

        dareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRandomDare();
            }
        });

        nextTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextPlayer();
            }
        });
    }

    private void showCurrentPlayer() {
        String currentPlayer = players.get(currentPlayerIndex);
        currentPlayerTextView.setText(currentPlayer + "'s Turn");
        questionTextView.setText("Choose Truth or Dare");

        // Enable buttons again for the new player's turn.
        truthButton.setEnabled(true);
        dareButton.setEnabled(true);
    }

    private void showRandomTruth() {
        playClickSound();

        // nextInt(length) gives a random valid index for the array.
        int randomIndex = random.nextInt(truths.length);
        questionTextView.setText("Truth: " + truths[randomIndex]);

        // Disable both buttons so one player gets only one question per turn.
        truthButton.setEnabled(false);
        dareButton.setEnabled(false);
    }

    private void showRandomDare() {
        playClickSound();

        // Pick a random dare from the dare array.
        int randomIndex = random.nextInt(dares.length);
        questionTextView.setText("Dare: " + dares[randomIndex]);

        truthButton.setEnabled(false);
        dareButton.setEnabled(false);
    }

    private void moveToNextPlayer() {
        playClickSound();

        currentPlayerIndex++;

        // If the last player is finished, go back to the first player.
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }

        showCurrentPlayer();
    }

    private void playClickSound() {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 80);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toneGenerator.release();
    }
}
