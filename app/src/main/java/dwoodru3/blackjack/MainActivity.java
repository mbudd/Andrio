package dwoodru3.blackjack;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static dwoodru3.blackjack.Card.Rank.*;

public class MainActivity extends AppCompatActivity {

    private enum Status {CONTINUE, WON, LOST, BUST} // game state
    Status gameStatus;


    // GUI elements
    private TextView dealerWinsTV;
    private TextView playerWinsTV;

    private TextView dealerScoreTV;
    private TextView playerScoreTV;

    private TextView bankAmountTV;
    private EditText betAmountET;

    private ImageView d1IV;
    private ImageView d2IV;
    private ImageView d3IV;
    private ImageView d4IV;
    private ImageView d5IV;

    private ImageView p1IV;
    private ImageView p2IV;
    private ImageView p3IV;
    private ImageView p4IV;
    private ImageView p5IV;

    private Button hitButton;
    private Button standButton;


    int pCard = 0;
    int dCard = 0;

    //game  objects
    private DeckOfCards myDeck = new DeckOfCards();
    private Hand playerHand = new Hand();
    private Hand dealerHand = new Hand();

    Card cardDrawn;

    //game variables
    int playerScore = 0;
    int dealerScore = 0;

    int playerWins = 0;
    int dealerWins = 0;

    int bank = StartActivity.buyInTotal;
    int bet = 0;

    boolean isFirstHit = false;

    //TODO ADD AN ADVERTISEMENT TO THE GUI
    //TODO ADD AN ADVERTISEMENT TO THE GUI
    //TODO ADD AN ADVERTISEMENT TO THE GUI
    //TODO ADD AN ADVERTISEMENT TO THE GUI

    //TODO Rules
    //if there is a tie between player and dealer they and the dealer has 3 or more cards the player wins
    //two cards are started only one dealer up
    // hit new card
    // stand take a pass
    // split two hands from cards with same value player gets to play, first decision of a hand set bet equal to the first
    //double down double bet and get one more action
    //surrender, players first action take half of the bet back

    //TODO Dealer Rules
    // dealer must draw cards when totaling 16 or less
    // dealer must stand on 17 or more

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
                .build();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6894784728941933/2103089601");



        setUpActivity();
    }

    private void setUpActivity()
    {

        dealerWinsTV = (TextView) findViewById(R.id.dealer_wins_textview);
        playerWinsTV = (TextView) findViewById(R.id.player_wins_textview);

        dealerScoreTV = (TextView) findViewById(R.id.dealer_score_textview);
        playerScoreTV = (TextView) findViewById(R.id.player_score_textview);

        bankAmountTV = (TextView) findViewById(R.id.bank_amount_textview);
        betAmountET = (EditText)  findViewById(R.id.bet_edittext);

        d1IV = (ImageView) findViewById(R.id.d1_card_imageview);
        d2IV = (ImageView) findViewById(R.id.d2_card_imageview);
        d3IV = (ImageView) findViewById(R.id.d3_card_imageview);
        d4IV = (ImageView) findViewById(R.id.d4_card_imageview);
        d5IV = (ImageView) findViewById(R.id.d5_card_imageview);

        p1IV = (ImageView) findViewById(R.id.p1_card_imageview);
        p2IV = (ImageView) findViewById(R.id.p2_card_imageview);
        p3IV = (ImageView) findViewById(R.id.p3_card_imageview);
        p4IV = (ImageView) findViewById(R.id.p4_card_imageview);
        p5IV = (ImageView) findViewById(R.id.p5_card_imageview);

        hitButton = (Button) findViewById(R.id.hit_button);
        standButton = (Button) findViewById(R.id.stand_button);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);

        newGame();
    }

    public void clickResetGame(View v)
    {
        newGame();
        // yourCardTextView.setText("Game Reset!");
    }// end clickResetGame()

    // sets up new game
    public void newGame()
    {
        myDeck = null;
        myDeck = new DeckOfCards();
        myDeck.shuffle();

        playerHand = new Hand();
        dealerHand = new Hand();

        playerScore = 0;
        dealerScore = 0;

        playerScore = hit(playerHand);


        p1IV.setImageResource(android.R.color.transparent);
        p2IV.setImageResource(android.R.color.transparent);
        p3IV.setImageResource(android.R.color.transparent);
        p4IV.setImageResource(android.R.color.transparent);
        p5IV.setImageResource(android.R.color.transparent);

        d1IV.setImageResource(android.R.color.transparent);
        d2IV.setImageResource(android.R.color.transparent);
        d3IV.setImageResource(android.R.color.transparent);
        d4IV.setImageResource(android.R.color.transparent);
        d5IV.setImageResource(android.R.color.transparent);


        getCardImage(playerHand.getCard(0));//
        p1IV.setImageResource(getCardImage(playerHand.getCard(0)));

        dealerScore = hit(dealerHand);
        d1IV.setImageResource(R.drawable.cardback);

        playerScore = hit(playerHand);
        p2IV.setImageResource(getCardImage(playerHand.getCard(1)));

        dealerScore = hit(dealerHand);
        d2IV.setImageResource(getCardImage(dealerHand.getCard(1)));

        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        isFirstHit = true;
        displayText();

        bankAmountTV.setText("" + bank);

        dealerWinsTV.setText("Dealer Wins: " + dealerWins);
        playerWinsTV.setText("Dealer Wins: " + playerWins);

    }// end newGame()

    //private int hit(int score, Hand hand)
    private int hit(Hand hand)
    {
        int score = 0;
        int numAces = 0;

        // Draws a Card
        cardDrawn = myDeck.dealCard();

        // adds card drawn to Hand
        hand.addCard(cardDrawn);

        for(int i = 0; i <= hand.length(); i++)
        {

            // checks amount of aces
            if (hand.getCard(i).rank() == ACE) numAces += 1;

            score = score + hand.getCard(i).getRankValue();
        }

        int aceValue = 0;
        // calculates value of aces
        if (numAces > 1)
            aceValue = (numAces);
        else if (numAces == 1 && (11 + score) <= 21)
            aceValue = 11;
        else if (numAces == 1 && (11 + score) > 21)
            aceValue = 1;

        score += aceValue;

        if (score > 21)
            gameStatus = Status.BUST;
        else
            gameStatus = Status.CONTINUE;

        return score;

    }// end hit()



    /*
    Click Hit to draw another card.
    Click Stand to end your turn.
    Click New Hand to start a round.
    Tap your bet to change it between rounds.


    Whoever is closets to 21 without being over it wins.
    If you go over 21 you bust and lose
    If there is a tie the dealer wins
     */

    public void clickRules(View v)
    {
        Context context = getApplicationContext();
        CharSequence text = "Click Hit to draw another card.\n" +
                "Click Stand to end your turn.\n" +
                "Click New Hand to start a round.\n" +
                "Tap your bet to change it between rounds.\n" +
                "\n" +
                "Whoever is closets to 21 without being over it wins.\n" +
                "If you go over 21 you bust and lose\n" +
                "If there is a tie the dealer wins";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void clickHit(View v)
    {
        if (isFirstHit == true && (betAmountET.getText().toString().matches("") || Integer.parseInt(betAmountET.getText().toString()) <= 0))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please place a bet.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else if (isFirstHit == true && Integer.parseInt(betAmountET.getText().toString()) <= bank)
            bet = Integer.parseInt(betAmountET.getText().toString());
        else if (isFirstHit == true && Integer.parseInt(betAmountET.getText().toString()) > bank)
        {
            Context context = getApplicationContext();
            CharSequence text = "You can't bet more than your bank";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (gameStatus == Status.CONTINUE)
        {
            //playerScore += hit(playerScore, playerHand);
            playerScore = hit(playerHand);

            switch(playerHand.length() + 1)
            {
                case 1:
                    p1IV.setImageResource(getCardImage(playerHand.getCard(playerHand.length())));
                    break;
                case 2:
                    p2IV.setImageResource(getCardImage(playerHand.getCard(playerHand.length())));
                    break;
                case 3:
                    p3IV.setImageResource(getCardImage(playerHand.getCard(playerHand.length())));
                    break;
                case 4:
                    p4IV.setImageResource(getCardImage(playerHand.getCard(playerHand.length())));
                    break;
                case 5:
                    p5IV.setImageResource(getCardImage(playerHand.getCard(playerHand.length())));
            }

            displayText();
        }

        if (gameStatus == Status.BUST)
        {
            checkResult();
        }
        displayText();
    }// end clickHit()

    public void clickStand(View v)
    {
        if (isFirstHit == true && (betAmountET.getText().toString().matches("") || Integer.parseInt(betAmountET.getText().toString()) <= 0))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please place a bet.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else if (isFirstHit == true && Integer.parseInt(betAmountET.getText().toString()) <= bank)
            bet = Integer.parseInt(betAmountET.getText().toString());
        else if (isFirstHit == true && Integer.parseInt(betAmountET.getText().toString()) > bank)
        {
            Context context = getApplicationContext();
            CharSequence text = "You can't bet more than your bank";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (gameStatus == Status.CONTINUE)
            dealerPlay();
        checkResult();
    }// end clickStand


    private void dealerPlay()
    {

        while(dealerScore < 17)
            dealerScore = hit(dealerHand);

        switch(dealerHand.length() + 1)
        {
            case 1:
                d1IV.setImageResource(getCardImage(dealerHand.getCard(dealerHand.length())));
                break;
            case 2:
                d2IV.setImageResource(getCardImage(dealerHand.getCard(dealerHand.length())));
                break;
            case 3:
                d3IV.setImageResource(getCardImage(dealerHand.getCard(dealerHand.length())));
                break;
            case 4:
                d4IV.setImageResource(getCardImage(dealerHand.getCard(dealerHand.length())));
                break;
            case 5:
                d5IV.setImageResource(getCardImage(dealerHand.getCard(dealerHand.length())));
        }

    }// end dealerPlay()

    private void checkResult()
    {
        if (playerScore > 21)
        {
            gameStatus = Status.LOST;
        }
        else if (playerScore == dealerScore)
        {
            gameStatus = Status.LOST;
        }
        else if (playerScore <= 21 && dealerScore > 21)
        {
            gameStatus = Status.WON;
        }
        else if (playerScore > dealerScore && playerScore <= 21)
        {
            gameStatus = Status.WON;
        }
        else if (playerScore < dealerScore && dealerScore <= 21)
        {
            gameStatus = Status.LOST;
        }
        else if (gameStatus == Status.BUST)
        {
            gameStatus = Status.LOST;
        }
        displayText();

    }//end checkResult()

    private void displayText()
    {
        if (gameStatus == Status.CONTINUE)
        {
            playerScoreTV.setText("Player Score: " + playerScore);
            dealerScoreTV.setText("Dealer Score: " + (dealerScore - dealerHand.getCard(0).getRankValue()));
        }
        else if(gameStatus == Status.WON)
        {
            Context context = getApplicationContext();
            CharSequence text = "You Win!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            playerScoreTV.setText("Player Score: " + playerScore);
            dealerScoreTV.setText("Dealer Score: " + dealerScore);

            d1IV.setImageResource(getCardImage(dealerHand.getCard(0)));

            playerWins += 1;
            playerWinsTV.setText("Player Wins: " + playerWins);

            hitButton.setEnabled(false);
            standButton.setEnabled(false);

            bank = bank + bet;
            bankAmountTV.setText("" + bank);
            isFirstHit = true;

        }
        else if(gameStatus == Status.LOST)
        {

            Context context = getApplicationContext();
            CharSequence text = "You Lose!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            playerScoreTV.setText("Player Score: " + playerScore);
            dealerScoreTV.setText("Dealer Score: " + dealerScore);
            d1IV.setImageResource(getCardImage(dealerHand.getCard(0)));

            dealerWins += 1;
            dealerWinsTV.setText("Dealer Wins: " + dealerWins);

            hitButton.setEnabled(false);
            standButton.setEnabled(false);

            bank = bank - (bet/2);
            bankAmountTV.setText("" + bank);
            isFirstHit = true;

        }
        else
        {
            playerScoreTV.setText("Player Score: " + playerScore);
            dealerScoreTV.setText("Dealer Score: " + (dealerScore - dealerHand.getCard(0).getRankValue()));
        }

    }// end displayText()


    // below this line was used for debugging
    //----------------------------------------------------------------------------------------
    public void clickCyclePlayerHand(View v)
    {
        Card card;

         if (pCard > playerHand.length())
        {
            pCard = 0;
        }

        card = playerHand.getCard(pCard);
        //TODO
        //showHandTextView.setText("Player Card number " + (pCard + 1) + " of " + (playerHand.length() + 1) + " " + card);
        pCard += 1;

    }// end clickCycleDealerHand

    public void clickCycleDealerHand(View v)
    {
        Card card;

        if (dCard > dealerHand.length())
        {
            dCard = 0;
        }
        card = dealerHand.getCard(dCard);
        //TODO
        //showHandTextView.setText("Dealer Card number " + (dCard + 1) + " of " + (dealerHand.length() + 1) + " " + card);
        dCard += 1;
    }// end clickCycleDealerHand

    // returns what card to show
    private int getCardImage(Card card)
    {
        int rankValue = 0;
        String suit = "c";

        switch (card.rank())
        {
            case ACE:
                rankValue = 1;
                break;
            case DEUCE:
                rankValue = 2;
                break;
            case THREE:
                rankValue = 3;
                break;
            case FOUR:
                rankValue = 4;
                break;
            case FIVE:
                rankValue = 5;
                break;
            case SIX:
                rankValue = 6;
                break;
            case SEVEN:
                rankValue = 7;
                break;
            case EIGHT:
                rankValue = 8;
                break;
            case NINE:
                rankValue = 9;
                break;
            case TEN:
                rankValue = 10;
                break;
            case JACK:
                rankValue = 11;
                break;
            case QUEEN:
                rankValue = 12;
                break;
            case KING:
                rankValue = 13;
                break;
            default:
                rankValue = 0;
                break;
        }

        switch(card.suit())
        {
            case CLUBS:
                suit = "c";
                break;
            case DIAMONDS:
                suit = "d";
                break;
            case HEARTS:
                suit = "h";
                break;
            case SPADES:
                suit = "s";
                break;
        }

        // grabs ID for proper card
        Resources r = getResources();
        int id = r.getIdentifier("" + suit + rankValue, "drawable", getPackageName());

        return id;

    }


}// end Class
