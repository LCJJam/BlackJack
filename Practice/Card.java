package GUI.Practice;

public class Card {
	private int cardValue;
	private int cardSuit;
	public Card()
	{
		cardValue = 0;
		cardSuit = 0;
	}
	public Card(int suit, int val)
	{
		cardValue = val;
		cardSuit = suit;
	}
	public void setValue(int val)
	{
		cardValue = val;
	}
	public void setSuit(int suit)
	{
		cardSuit = suit;
	}
	public int getValue()
	{
		return cardValue;
	}
	public int getSuit()
	{
		return cardSuit;
	}
	public int getY()
	{
		return 60*cardSuit;
	}
	public int getX()
	{
		return 40*(cardValue-1);
	}
}
