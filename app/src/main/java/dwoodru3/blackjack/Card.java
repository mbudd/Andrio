package dwoodru3.blackjack;


/**
 * Created by David on 11/27/2016.
 */

public class Card {

    public enum Rank {ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}
    private final Rank rank;

    public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
    private final Suit suit;

    public Card (Suit cardSuit, Rank cardRank)
    {
        this.suit = cardSuit;
        this.rank = cardRank;
    }// end Card Constructor

    // returns string representation of card
    public String toString()
    {
        return rank + " of " + suit;

    }// end toString()

    public Rank rank()
    {
        return rank;
    }

    public Suit suit()
    {
        return suit;
    }


    public int getRankValue()
    {
        int rankValue;

        switch (rank) {
            case ACE: //TODO make aces count as 1 or 11
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
                // value is 10
            case JACK:
                // value is 10
            case QUEEN:
                // value is 10
            case KING:
                rankValue = 10;
                break;
            default:
                rankValue = 0; //This should never happen
                break;
        }// end switch

        return rankValue;
    }// end getRankValue

}// ends class Card
