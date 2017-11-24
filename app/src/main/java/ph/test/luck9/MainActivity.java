package ph.test.luck9;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.test.luck9.model.Card;

public class MainActivity extends AppCompatActivity {

    private static final String CLUBS = "Clubs";
    private static final String DIAMOND = "Diamonds";
    private static final String HEARTS = "Hearts";
    private static final String SPADES = "Spades";

    private static final String JACK = "Jack";
    private static final String QUEEN = "Queen";
    private static final String KING = "King";
    private static final String ACE = "Ace";

    private String[] cardType = { CLUBS, DIAMOND, HEARTS, SPADES };//card suits
    private String[] cardDisplay = { "2", "3", "4", "5", "6", "7", "8", "9", "10", JACK, QUEEN, KING, ACE };

    private ArrayList<Card> listOfCards = new ArrayList<>();
    private Stack stack;


    @BindView(R.id.cards) LinearLayout cardLayout;
    @BindView(R.id.card1_label) TextView yourCard1Label;
    @BindView(R.id.card1_sign) TextView yourCard1Sign;
    @BindView(R.id.card2_label) TextView yourCard2Label;
    @BindView(R.id.card2_sign) TextView yourCard2Sign;
    @BindView(R.id.decision) TextView decision;
    @BindView(R.id.reshuffle) Button reshuffle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAndReSuffleCards();
    }

    private void initAndReSuffleCards(){
        for (int i = 0; i < cardDisplay.length; i++){
            for (int j = 0; j < cardType.length; j++){
                Card card = new Card();
                card.setType(cardType[j]);
                card.setCardDisplay(cardDisplay[i]);
                listOfCards.add(card);
            }
        }

        stack = new Stack();
        // shuffle cards
        Collections.shuffle(listOfCards);

        //Put shuffled card on stack
        for(Card card : listOfCards){
            stack.push(card);
        }
    }

    private int totalNumberOfCard(int valueOfCard){
        if(valueOfCard > 9){
            return valueOfCard - 10;
        }
        return valueOfCard;
    }


    private int getCardValueBasedOnType(Card card){
        if(card.getCardDisplay().equals(ACE)){
            return 1;
        }else if(card.getCardDisplay().equals(JACK)){
            return 0;
        }else if(card.getCardDisplay().equals(QUEEN)){
            return 0;
        }else if(card.getCardDisplay().equals(KING)){
            return 0;
        }else if(card.getCardDisplay().equals("10")){
            return 0;
        }else{
            return Integer.parseInt(card.getCardDisplay());
        }
    }


    @OnClick(R.id.reshuffle)
    public void reshuffleCards(View view){
        initAndReSuffleCards();

        yourCard1Label.setText("");
        yourCard1Sign.setText("");
        yourCard2Label.setText("");
        yourCard2Sign.setText("");
        decision.setText("Game");
    }

    @OnClick(R.id.cards)
    public void getCards(View view){

        if(!stack.isEmpty()){
            Card card1 = (Card)stack.pop();
            Card card2 = (Card)stack.pop();
            Card card3 = (Card)stack.pop();
            Card card4 = (Card)stack.pop();

            int opponentCardValue = totalNumberOfCard((getCardValueBasedOnType(card1) + getCardValueBasedOnType(card3)));
            int yourCardValue = totalNumberOfCard((getCardValueBasedOnType(card2) + getCardValueBasedOnType(card4)));

            displayCard(yourCard1Label, yourCard1Sign, card2);
            displayCard(yourCard2Label, yourCard2Sign, card4);

            Log.i("Lucky9", "opponent card :: "+ card1.toString() + " "+ card3.toString());
            Log.i("Lucky9", "your card :: "+ card2.toString() + " "+ card4.toString());
            if(opponentCardValue > yourCardValue){
                decision.setText("You Loose!");
            }else if (yourCardValue > opponentCardValue){
                decision.setText("You Win!");
            }else{
                decision.setText("Draw");
            }
        }else{
            Toast.makeText(this, "No more cards on the Deck, \n Click Re-Shuffle to start again.", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayCard(TextView label, TextView sign, Card card){

        if(ACE.equals(card.getCardDisplay())){
            label.setText("A");
        }else if(JACK.equals(card.getCardDisplay())){
            label.setText("J");
        }else if(QUEEN.equals(card.getCardDisplay())){
            label.setText("Q");
        }else if(KING.equals(card.getCardDisplay())){
            label.setText("K");
        }else{
            label.setText(card.getCardDisplay());
        }

        if(SPADES.equals(card.getType())){
            sign.setText(getSpannedText("&#x2660"));
        }else if(HEARTS.equals(card.getType())){
            sign.setText(getSpannedText("&#x2665"));
        }else if(CLUBS.equals(card.getType())){
            sign.setText(getSpannedText("&#x2663"));
        }else if(DIAMOND.equals(card.getType())){
            sign.setText(getSpannedText("&#x2666"));
        }
    }


    private Spanned getSpannedText(String text){
        if(Build.VERSION.SDK_INT >= 24){
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        }else{
            return Html.fromHtml(text);
        }
    }

}
