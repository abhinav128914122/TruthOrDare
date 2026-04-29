# Truth or Dare Android App

This is a simple beginner-level Android app written in Java.

## Screens

1. `MainActivity`
   - Lets users enter player names.
   - Shows the list of added players.
   - Starts the game by sending the player list to `GameActivity`.

2. `GameActivity`
   - Shows whose turn it is.
   - Lets the player choose Truth or Dare.
   - Displays one random question.
   - Moves to the next player when `Next Turn` is clicked.

## How player turns work

The game stores all player names in an `ArrayList<String>` called `players`.

`GameActivity` has an integer variable:

```java
private int currentPlayerIndex = 0;
```

This index tells the app whose turn it is.

Example:

- `0` means first player
- `1` means second player
- `2` means third player

When the `Next Turn` button is clicked, the index is increased:

```java
currentPlayerIndex++;
```

If the index becomes equal to the number of players, it means everyone has played once. So the index is reset to `0`:

```java
if (currentPlayerIndex == players.size()) {
    currentPlayerIndex = 0;
}
```

This makes the turns continue in a loop.

## How random questions are selected

Truth and dare questions are stored in two string arrays:

```java
private String[] truths = { ... };
private String[] dares = { ... };
```

The app uses Java's `Random` class:

```java
Random random = new Random();
```

To choose one truth question:

```java
int randomIndex = random.nextInt(truths.length);
questionTextView.setText(truths[randomIndex]);
```

`random.nextInt(truths.length)` gives a random number from `0` to the last array index.

## How data is passed between screens

In `MainActivity`, the player list is sent using an `Intent`:

```java
Intent intent = new Intent(MainActivity.this, GameActivity.class);
intent.putStringArrayListExtra("playerList", players);
startActivity(intent);
```

In `GameActivity`, the list is received using the same key:

```java
players = getIntent().getStringArrayListExtra("playerList");
```

The key `"playerList"` must be the same in both activities.

## Optional features included

- Remove last player button.
- Simple beep sound when buttons are clicked.
