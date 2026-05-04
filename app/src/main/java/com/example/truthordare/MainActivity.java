package com.example.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.media.ToneGenerator;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    // These variables are used by different methods in this activity.
    private EditText playerNameEditText;
    private TextView playerListTextView;
    private ArrayList<String> players;
    private ToneGenerator toneGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerNameEditText = findViewById(R.id.playerNameEditText);
        playerListTextView = findViewById(R.id.playerListTextView);
        Button addPlayerButton = findViewById(R.id.addPlayerButton);
        Button removePlayerButton = findViewById(R.id.removePlayerButton);
        Button startGameButton = findViewById(R.id.startGameButton);

        // ArrayList is used because players can be added and removed.
        players = new ArrayList<>();
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer();
            }
        });

        removePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeLastPlayer();
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void addPlayer() {
        playClickSound();

        // trim() removes extra spaces before and after the name.
        String name = playerNameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a player name", Toast.LENGTH_SHORT).show();
            return;
        }

        players.add(name);
        playerNameEditText.setText("");
        updatePlayerList();
    }

    private void removeLastPlayer() {
        playClickSound();

        if (players.isEmpty()) {
            Toast.makeText(this, "No player to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        players.remove(players.size() - 1);
        updatePlayerList();
    }

    private void updatePlayerList() {
        if (players.isEmpty()) {
            playerListTextView.setText("No players added yet");
            return;
        }

        // StringBuilder helps us create one string from all player names.
        StringBuilder listText = new StringBuilder();

        for (int i = 0; i < players.size(); i++) {
            listText.append(i + 1).append(". ").append(players.get(i)).append("\n");
        }

        playerListTextView.setText(listText.toString());
    }

    private void startGame() {
        playClickSound();

        if (players.size() < 2) {
            Toast.makeText(this, "Add at least 2 players", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent is used to open GameActivity and send the player list.
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putStringArrayListExtra("playerList", players);
        startActivity(intent);
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
