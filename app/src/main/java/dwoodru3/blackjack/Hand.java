package dwoodru3.blackjack;

/**
 * Created by david on 11/29/2016.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand
{

    private  List<Card> myHand = new ArrayList<Card>();

    public Hand()
    {
        //myHand.add(card);
    }

    public void addCard(Card card)
    {
        myHand.add(card);
    }

    // returns a card at a specific index
    public Card getCard(int index)
    {
        return myHand.get(index);
    }

    public Card getLastCard()
    {
        return  myHand.get(myHand.size() - 1);
    }

    //return all cards
    public List<Card> getAllCards()
    {
        return myHand;
    }

    public int length()
    {
        return myHand.size() - 1;
    }

}// end Class Hand
