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

    // Predefined truth questions. These are school-safe and family-friendly.
    private String[] truths = {
            "What is your funniest childhood memory?",
            "What is one secret talent you have?",
            "What is the most embarrassing thing you have done?",
            "What is one thing you are afraid of?",
            "Have you ever lied to avoid homework?",
            "What is your favorite memory with your friends?",
            "What is one habit you want to change?",
            "What is your favorite subject in school?",
            "What is your least favorite subject in school?",
            "What is your favorite food?",
            "What is your favorite fruit?",
            "What is your favorite game?",
            "What is your favorite cartoon or animated movie?",
            "What is your favorite festival?",
            "What is your favorite color?",
            "What is your favorite book or story?",
            "What is your favorite song?",
            "What is your favorite sport?",
            "What is your favorite place to visit?",
            "What is your dream job?",
            "What is one skill you want to learn?",
            "What is one thing that makes you happy?",
            "What is one thing that makes you nervous?",
            "What is one good habit you have?",
            "What is one thing you are proud of?",
            "What is one goal you have for this year?",
            "What is one thing you do well?",
            "What is one thing you want to improve?",
            "What is your favorite family tradition?",
            "What is your favorite thing to do on weekends?",
            "What is your favorite holiday activity?",
            "What is the best gift you have received?",
            "What is the best gift you have given someone?",
            "What is one kind thing someone did for you?",
            "What is one kind thing you did for someone?",
            "What is your favorite indoor activity?",
            "What is your favorite outdoor activity?",
            "What is one place you want to travel to?",
            "What is your favorite app for learning?",
            "What is your favorite school memory?",
            "What is the funniest joke you know?",
            "What is your favorite snack?",
            "What is your favorite drink?",
            "What is your favorite season?",
            "What is your favorite time of day?",
            "What is your favorite animal?",
            "What is one rule you always follow?",
            "What is one rule you sometimes forget?",
            "What is one thing you are thankful for?",
            "What is one thing that helps you relax?",
            "What is your favorite thing about your best friend?",
            "What is your favorite thing about your classroom?",
            "What is one thing you would do if you were class monitor?",
            "What is one thing you would invent if you could?",
            "What is one superpower you would like to have?",
            "What is one thing you like about yourself?",
            "What is one compliment you received that made you happy?",
            "What is one thing you find difficult but want to try?",
            "What is your favorite chapter from any subject?",
            "What is one movie that made you laugh?",
            "What is one activity you enjoy with your family?",
            "What is one thing you learned recently?",
            "What is one thing you want to learn from your teacher?",
            "What is one thing you would change about your daily routine?",
            "What is one responsibility you handle at home?",
            "What is one thing you do when you feel bored?",
            "What is one positive word that describes you?"
    };

    // Predefined dare tasks. These are simple, safe, and classroom-friendly.
    private String[] dares = {
            "Sing a song for 20 seconds.",
            "Do 10 jumping jacks.",
            "Act like your favorite movie character.",
            "Speak in a funny voice until your next turn.",
            "Dance for 15 seconds.",
            "Tell a joke to everyone.",
            "Make a funny face for 10 seconds.",
            "Say the alphabet backwards as far as you can.",
            "Clap your hands 10 times.",
            "Count from 1 to 20 quickly.",
            "Say your name backwards if you can.",
            "Pretend to be a teacher for 15 seconds.",
            "Pretend to be a news reporter for 15 seconds.",
            "Pretend to be a robot for 15 seconds.",
            "Walk like a slow-motion actor for 10 seconds.",
            "Balance on one foot for 10 seconds.",
            "Spin around once and say hello.",
            "Draw a star in the air with your finger.",
            "Say five colors quickly.",
            "Say five fruits quickly.",
            "Say five school subjects quickly.",
            "Name five animals quickly.",
            "Name five countries quickly.",
            "Name five things in the classroom quickly.",
            "Give a thumbs-up to everyone.",
            "Say one nice thing about the person on your right.",
            "Say one nice thing about the person on your left.",
            "Pretend to play a guitar for 10 seconds.",
            "Pretend to play drums for 10 seconds.",
            "Pretend to be a statue for 10 seconds.",
            "Smile without speaking for 10 seconds.",
            "Say a tongue twister one time.",
            "Say your favorite subject loudly.",
            "Say your favorite food in a funny voice.",
            "Act like you are reading a very serious news headline.",
            "Pretend to be a sports commentator for 15 seconds.",
            "Do a simple salute.",
            "Make a heart shape with your hands.",
            "Draw an imaginary circle in the air.",
            "Pretend to take a selfie pose.",
            "Say good morning in three different tones.",
            "Say thank you in three different tones.",
            "Pretend to open an invisible door.",
            "Pretend to lift a heavy school bag.",
            "Pretend to write very fast in a notebook.",
            "Pretend to search for a lost pencil.",
            "Say the days of the week quickly.",
            "Say the months of the year as far as you can.",
            "Spell your first name aloud.",
            "Spell the word SCHOOL aloud.",
            "Spell the word FRIEND aloud.",
            "Act like you just won a quiz competition.",
            "Act like you are surprised by a test result.",
            "Pretend to eat your favorite snack.",
            "Pretend to drink a cold juice.",
            "Make three different happy expressions.",
            "Make three different surprised expressions.",
            "Do five shoulder rolls.",
            "Do five simple stretches with your arms.",
            "Tap your desk lightly five times.",
            "Snap your fingers if you can.",
            "Wave like a celebrity for 10 seconds.",
            "Pretend to be an airplane for 10 seconds.",
            "Pretend to be a train for 10 seconds.",
            "Say one motivational line.",
            "Say one polite sentence.",
            "Give yourself a positive compliment.",
            "Lead everyone in one clap."
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
