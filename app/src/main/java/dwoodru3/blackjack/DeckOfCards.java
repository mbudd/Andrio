package dwoodru3.blackjack;

/**
 * Created by David on 11/27/2016.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfCards
{
    private int currentCard;

    private static final List<Card> myDeck = new ArrayList<Card>();

    // constructor fills deck of cards
    public DeckOfCards()
    {
        currentCard = 0;
        //populate deck with Cards
        for (Card.Suit suit : Card.Suit.values())
            for (Card.Rank rank : Card.Rank.values())
            myDeck.add(new Card(suit, rank));

    }// end Deck Constructor

    // shuffles cards
    public void shuffle()
    {
        // sets card to first in deck
        currentCard = 0;
        Collections.shuffle(this.myDeck);
    }// end shuffle()

    // deals a card
    public Card dealCard()
    {
        if (currentCard < myDeck.size())
            // return myDeck[currentCard++]; // returns a new card
            return myDeck.get(currentCard++);
        else
            return null; // all cards were dealt
    }// end  dealCard()

}// ends Class DeckOfCards