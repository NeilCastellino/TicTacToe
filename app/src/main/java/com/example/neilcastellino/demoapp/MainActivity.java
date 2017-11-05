package com.example.neilcastellino.demoapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0; // 0=0 and 1=X
	int nextTimePlayer = 1;

    boolean gameIsActive = true;

    int scoreX = 0, scoreY = 0;

    int[] gameState = {2,2,2,2,2,2,2,2,2};// 2 means unplayed

    int [][] winningPositions =
            {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        ImageView counter =(ImageView)view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        TextView txtScoreX = (TextView)findViewById(R.id.textView3);
        TextView txtScoreY = (TextView)findViewById(R.id.textView4);

        if(gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);
            counter.setRotation(-1440);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.y);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.x);
                activePlayer = 0;
            }
            counter.animate()
                    .translationYBy(1000f)
                    .rotation(360)
                    .setDuration(300);

            TextView winnermsg = (TextView)findViewById(R.id.txtwon);

            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){
                    gameIsActive = false;

                    if(gameState[winningPosition[0]] == 0){
                        winnermsg.setText("Player O WINS");
                        scoreY++;

                        txtScoreX.setText("X: " + scoreX);
                        txtScoreY.setText("O: " + scoreY);

                        txtScoreY.setTranslationX(500f);
                        txtScoreY.animate().translationXBy(-500f).setDuration(350);
                    }else if(gameState[winningPosition[0]] == 1){
                        winnermsg.setText("Player X WINS");
                        scoreX++;

                        txtScoreX.setText("X: " + scoreX);
                        txtScoreY.setText("O: " + scoreY);

                        txtScoreX.setTranslationX(500f);
                        txtScoreX.animate().translationXBy(-500f).setDuration(350);
                    }

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    boolean gameIsOver = true;
                    for(int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                    if(gameIsOver){
                        winnermsg.setText("It's a DRAW");
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void newGame(View view){
        gameIsActive = true;

        Button button = (Button) findViewById(R.id.btnnew);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        linearLayout.setVisibility(View.INVISIBLE);
		
		if(nextTimePlayer == 0){
			activePlayer = 0; // 0=0 and 1=X
			nextTimePlayer = 1;
		}else{
			activePlayer = 1; // 0=0 and 1=X
			nextTimePlayer = 0;
		}

		if(activePlayer == 0){
            Toast.makeText(MainActivity.this, "O plays First", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "X plays First", Toast.LENGTH_SHORT).show();
        }

        for(int i = 0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid1);

        for(int i = 0; i<gridLayout.getChildCount(); i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

        TextView txtScoreX = (TextView)findViewById(R.id.textView3);
        TextView txtScoreY = (TextView)findViewById(R.id.textView4);

        txtScoreX.setText("X: " + scoreX);
        txtScoreY.setText("O: " + scoreY);
    }

    public void resetScore(View view){
        scoreX = 0;
        scoreY = 0;

        TextView txtScoreX = (TextView)findViewById(R.id.textView3);
        TextView txtScoreY = (TextView)findViewById(R.id.textView4);

        txtScoreX.setText("X: " + scoreX);
        txtScoreY.setText("O: " + scoreY);

        txtScoreX.setTranslationX(-500f);
        txtScoreX.animate().translationXBy(500f).setDuration(350);
        txtScoreY.setTranslationX(-500f);
        txtScoreY.animate().translationXBy(500f).setDuration(350);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtScoreX = (TextView)findViewById(R.id.textView3);
        TextView txtScoreY = (TextView)findViewById(R.id.textView4);

        txtScoreX.setText("X: " + scoreX);
        txtScoreY.setText("O: " + scoreY);
    }
}
