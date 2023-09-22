package GUI.Practice;

/**
* @(#)BlackJack.java
* BlackJack application
* Author: Brian SonniE
*/
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
public class BlackJack extends JFrame{
    static int sel = 0;
    static boolean cm = true;
    static int GameNum=0; // 추가 게임수
	static String Account = ""; // 계정
	static int cash = 0;
    int PlayerOnecash = 0;
    int PlayerTwocash = 0;
    int currentBet = 0; 
    ArrayList<Card> deck; 
    Card[] usersCards = new Card[10];
    Card[] dealersCards = new Card[10];
    Ranking[] Login = new Ranking[10];
    private JButton buttonHit, buttonHit2, buttonStay, buttonDeal, buttonCashOut; 
    private JPanel userPane, dealerPane; 
    private JLabel labelMoney, labelBet,labelOneMoney,labelTwoMoney; 
    BufferedImage img; 
    final int cardWidth = 40;
    final int cardHeight = 60;
    boolean gameOn = true;

public BlackJack() {
	setTitle("BlackJack");    
	setSize(450,500);    
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	setLayout(new BorderLayout());
	setVisible(true);    
	setBackground(Color.YELLOW);
	if(Select()==1) {
		SinglePlay();
	} else {
		MultiPlay();
	}
}

// 그래픽처리
public void paint(Graphics g) {    
   
   super.paint(g);
   if(sel==1) {
	   g.drawString("Dealers Hand", 20, 80);
	   g.drawString(Account +" Hand", 20, 230);
	   g.drawString("Your Hand", 20, 230);        
	   g.drawString("Sum: "+getUserSum(), 90, 230);
   }
   else {
	   g.drawString("PlayerOne Hand", 20, 80);
	   g.drawString("Sum: "+getDealerSum(), 130, 80);  
	   g.drawString("PlayerTwo Hand", 20, 230);        
	   g.drawString("Sum: "+getUserSum(), 130, 230);
   }
        
   int x1 = 10;    
   int y1 = 250;    
   int x2 = x1+cardWidth;    
   int y2 = y1+cardHeight;  //User    
   int dx1 = 10;    
   int dy1 = 90;
   int dx2 = dx1+cardWidth;    
   int dy2 = dy1+cardHeight; //Dealer
   
   // 딜러나 플레이어1 카드뽑기       
   for(int i = 1; i<=getNumDealerCards(); i++)    
   {    
   g.drawImage(img, dx1+(cardWidth*(i-1)), dy1, dx2+(cardWidth*(i-1)), dy2, dealersCards[i-1].getX(),
        dealersCards[i-1].getY(), dealersCards[i-1].getX()+cardWidth ,dealersCards[i-1].getY()+cardHeight,null);
    }        
   // 유저나 플레이어2 카드뽑기
    for(int i = 1; i<=getNumUserCards(); i++)
    {    
      g.drawImage(img, x1+(cardWidth*(i-1)), y1, x2+(cardWidth*(i-1)), y2, usersCards[i-1].getX(),
         usersCards[i-1].getY(), usersCards[i-1].getX()+cardWidth ,usersCards[i-1].getY()+cardHeight,null);
    }
    if(sel==1) {
    	if(!gameOn) {
    	       g.drawString("Sum: "+getDealerSum(), 110, 80);
    	    }
    	    if(gameOn) {
    	       g.fillRect(10,90,cardWidth,cardHeight);
    	    }
    }
    
}  

public int getNumUserCards() {    
   int numCards = 0;    
   while(usersCards[numCards]!=null)
      numCards++;
   return numCards;
}
public int getNumDealerCards() {
    int numCards = 0;
    while(dealersCards[numCards]!=null)
       numCards++;    
   return numCards; 
} 
public int getUserSum() {    
   int numCards = 0;    
   int sum = 0; 
   int cardVal = 0;
    while(usersCards[numCards]!=null){
       cardVal = usersCards[numCards].getValue();
       if(cardVal == 1 && sum<=10)
          cardVal = 11;
       else if(cardVal == 11)
          cardVal = 10;
       else if(cardVal == 12)
          cardVal = 10;
       else if(cardVal == 13)
          cardVal = 10;
       sum+=cardVal;
       numCards++;
    }    
    return sum;
} 
public int getDealerSum() {    
   int numCards = 0;    
   int sum = 0;    
   int cardVal = 0;    
   while(dealersCards[numCards]!=null){       
   cardVal = dealersCards[numCards].getValue();       
   if(cardVal == 1 && sum<=10)             
      cardVal = 11;
       else if(cardVal == 11)
             cardVal = 10;
       else if(cardVal == 12)
             cardVal = 10;
       else if(cardVal == 13)
             cardVal = 10;
       sum+=cardVal;
       numCards++;
    }    
   return sum;
} 
 // Recursion Implemented Check \
public int setBet(int bet){
    try{
       bet = Integer.parseInt(JOptionPane.showInputDialog("Enter Your Bet: "));
    }
    catch(NumberFormatException ex)
    {   bet=0; }
    if(sel==1) {
    	if(bet <= 0 || bet > cash)
    	       bet=setBet(0);
    	    else 
    	      labelBet.setText(Integer.toString(bet));
    	        return bet;
    }
    else {
    	if(bet <= 0 || bet > PlayerOnecash || bet > PlayerTwocash)
 	       bet=setBet(0);
 	    else 
 	      labelBet.setText(Integer.toString(bet));
 	        return bet;
    }
}
public void buildTable() {//single play buildtable
    //***Dealer Pane***\
    dealerPane = new JPanel();
    dealerPane.setBackground(Color.gray);
    buttonDeal = new JButton("Deal");
    buttonCashOut = new JButton("Walk Away");
    dealerPane.add(buttonCashOut);
    dealerPane.add(buttonDeal);
    buttonDeal.addActionListener(new Dealer());
    buttonCashOut.addActionListener(new Walk());
     
   new JPanel();
    //***UserPane***\
    userPane = new JPanel(new GridLayout(3,2));
    Border pancakes = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Your Money");
    userPane.setBorder(pancakes);
    userPane.setBackground(Color.gray);
    buttonHit = new JButton("Hit");
    buttonStay = new JButton("Stay");
    labelBet = new JLabel("0");
    labelMoney = new JLabel("0");
    userPane.add(new JLabel("Your Total Moneies: "));
    userPane.add(labelMoney);
    userPane.add(new JLabel("Your Bet: "));
    userPane.add(labelBet);
    userPane.add(buttonHit);
    userPane.add(buttonStay);
    buttonHit.addActionListener(new HitMe());
    buttonStay.addActionListener(new Stay());
    add(dealerPane, BorderLayout.NORTH);
    add(userPane, BorderLayout.SOUTH);
     // Set Starting Money
    try { 
        img = ImageIO.read(Objects.requireNonNull(getClass().getResource("../img/Cards.gif")));
    } catch (IOException e) {}
    Account = getStartingAccount();
    cash = getStartingAmmt();
    labelMoney.setText(Integer.toString(cash));
   }

public void buildMultiTable() {
	dealerPane = new JPanel();
	dealerPane.setBackground(Color.gray);
    buttonDeal = new JButton("Deal");
    buttonCashOut = new JButton("Walk Away");
    dealerPane.add(buttonCashOut);
    dealerPane.add(buttonDeal);
    buttonDeal.addActionListener(new Dealer());
    buttonCashOut.addActionListener(new WalkMulti());
     
   new JPanel();
    //***UserPane***\
    userPane = new JPanel(new GridLayout(5,2));
    Border pancakes = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Your Money");
    userPane.setBorder(pancakes);
    userPane.setBackground(Color.gray);
    buttonHit = new JButton("Hit1");
    buttonHit2 = new JButton("Hit2");
    buttonStay = new JButton("Stay");
    labelBet = new JLabel("0");
    labelOneMoney = new JLabel("0");
    labelTwoMoney = new JLabel("0");
    userPane.add(new JLabel("Player One Total Moneies: "));
    userPane.add(labelOneMoney);
    userPane.add(new JLabel("Player Two Total Moneies: "));
    userPane.add(labelTwoMoney);
    userPane.add(new JLabel("Bet: "));
    userPane.add(labelBet);
    userPane.add(buttonHit);
    userPane.add(buttonHit2);
    userPane.add(new JPanel());
    userPane.add(buttonStay);
    buttonHit.addActionListener(new HitMeMulti());
    buttonStay.addActionListener(new StayMulti());
    buttonHit2.addActionListener(new HitMeMulti2());
    add(dealerPane, BorderLayout.NORTH);
    add(userPane, BorderLayout.SOUTH);
     // Set Starting Money
    try { 
    img = ImageIO.read(getClass().getResource("/img/Cards.gif"));
    } catch (IOException e) {}
    cash = getStartingAmmt();
    if(cm=true) {
    	PlayerOnecash = cash;
        PlayerTwocash = cash;
    }
    cm = false;
    labelOneMoney.setText(Integer.toString(PlayerOnecash));
    labelTwoMoney.setText(Integer.toString(PlayerTwocash));
    }

public static String getStartingAccount() { // 계정 받는 다이얼로그
	String Account = "";
	try{
		Account = JOptionPane.showInputDialog("Enter Starting NickName: (Max 6)");
		if (Account.length() > 6) {
			Account = getStartingAccount();
		}
			} catch (Exception nfe) {nfe.getMessage();}
 	return Account;
}


/**
   * Code for New Round 
   */ 
public void gameInit() {
    currentBet = setBet(0);
    usersCards = new Card[10];
    dealersCards = new Card[10];
    deck = new ArrayList<Card>(52);
    
   for(int i = 0; i<52; i++)
       deck.add(i, new Card((i%4),(i%13)+1));           
   // Give User and Dealer 2 Cards Each        
   usersCards[0] = pullRandomCard();
   usersCards[1] = pullRandomCard();
   dealersCards[0] = pullRandomCard();
   dealersCards[1] = pullRandomCard();
   repaint(); 
} 
public int getStartingAmmt() {
   int amnt = 0;
    while(amnt<=0){
       try{
       amnt = Integer.parseInt(JOptionPane.showInputDialog("Enter Starting Ammount of Money:"));
       } catch (NumberFormatException nfe) {amnt=0;}
    }
    return amnt;
}

public Card pullRandomCard(){
   Random rand = new Random();
   return deck.remove(rand.nextInt(deck.size()));
}
    //*********************EVENTS*******************\
  /** Deal 
   * Recreate The deck  
   * Give dealer and user 2 cards 
   */ 
class Dealer implements ActionListener{
   public void actionPerformed(ActionEvent ae){
      gameOn = true;
      gameInit();
   }
}   
   /** Hit Me
   * Draw a random card from the deck
   * Check the new sum of cards
   * if 21 user wins 
   * if > 21 user losses 
   * if < 21 user can choose to hit/stay 
   */   
class HitMe implements ActionListener{
   public void actionPerformed(ActionEvent ae){
      if(gameOn){
         usersCards[getNumUserCards()] = pullRandomCard();
         repaint();
         if(getUserSum()>21){
               // You Lose
            JOptionPane.showMessageDialog(null, "You Lost...");
            gameOn = false;
            repaint();
            cash-=currentBet;
            labelMoney.setText(Integer.toString(cash));
         }
         if(cash<=0){
            JOptionPane.showMessageDialog(null,"You broke...");
            System.exit(0);
         }
      }
   }   
}
   /**    
   * Check for winner
    */

class Stay implements ActionListener{
   public void actionPerformed(ActionEvent ae){
      if(gameOn){
         gameOn = false;
         if(getUserSum()>21){
            // You Lose
            JOptionPane.showMessageDialog(null, "You Lost...");
            cash-=currentBet;
            labelMoney.setText(Integer.toString(cash));
         } else {
            while(getDealerSum()<17){
               dealersCards[getNumDealerCards()] = pullRandomCard();
             }
            if(getDealerSum()>21){
               // You Win
               JOptionPane.showMessageDialog(null, "You Win!!");
               cash+=currentBet;
               labelMoney.setText(Integer.toString(cash));
            } else {
               if(21-getUserSum() < 21-getDealerSum()){// Youwin
                  JOptionPane.showMessageDialog(null, "You Win!!");
                  cash+=currentBet;
                  labelMoney.setText(Integer.toString(cash));
               } else if(getUserSum() == getDealerSum()) {
                  JOptionPane.showMessageDialog(null, "Tie...");
               } else /* You Lose*/ {
                  JOptionPane.showMessageDialog(null, "You Lost...");
                  cash-=currentBet;
                  labelMoney.setText(Integer.toString(cash));
               }
            }
         }
         repaint();
         if(cash<=0)
         {
            JOptionPane.showMessageDialog(null,"You broke...");   
         System.exit(0);
         }
      }
   }   
}
   /**
    * Leav App
    */   
class Walk implements ActionListener{
	public void actionPerformed(ActionEvent ae){
		Ranking a = new Ranking(Account , cash);  // 랭킹 저장 
		Login[GameNum] = a;
		JOptionPane.showMessageDialog(null, "You played and walked away with $" + cash);
		int Regame = -1;
		try{ //다시시작 1 , 랭킹보기 2 , 나가기 0
			while(0 > Regame && Regame <1 ) {
				Regame = Integer.parseInt(JOptionPane.showInputDialog("Regame:1 or Rank:2 or Exit:0 "));
			}
		} catch (Exception nfe) {nfe.getMessage();}
		
		if (Regame == 1) { //다시시작
			GameNum++;
			gameOn = true;
			SinglePlay2();
		} else if (Regame == 2) { //랭킹보기
			int i = 0;
			Ranking[] Log = new Ranking[GameNum+1];
			for(int j=0 ; j < GameNum+1 ; j++) {
				Log[j] = Login[j];
			}
			Arrays.sort(Log);  //array,sort 를 이용한 배열 오름차순 정렬
			for(Ranking temp: Log) {
				JOptionPane.showMessageDialog(null,"Rank " + ++i + " : " + temp.getAccount() + ", Score  : " + temp.getCash());
			} 
		} else { System.exit(0); }
	}
}
class HitMeMulti implements ActionListener{
	   public void actionPerformed(ActionEvent ae){
	      if(gameOn){
	         dealersCards[getNumDealerCards()] = pullRandomCard();
	         repaint();
	         if(getDealerSum()>21){
	               // You Lose
	            JOptionPane.showMessageDialog(null, "PlayerOne Lost...");
	            gameOn = false;
	            repaint();
	            PlayerOnecash-=currentBet;
	            PlayerTwocash+=currentBet;	            
	            labelOneMoney.setText(Integer.toString(PlayerOnecash));
	            labelTwoMoney.setText(Integer.toString(PlayerTwocash));
	         }
	         if(PlayerOnecash<=0){
	            JOptionPane.showMessageDialog(null,"PlayerOne broke...");
	            JOptionPane.showMessageDialog(null, "Player Two Win $" + PlayerTwocash);
	            System.exit(0);
	         }
	      }
	   }   
	}
class HitMeMulti2 implements ActionListener{
   public void actionPerformed(ActionEvent ae){
      if(gameOn){
         usersCards[getNumUserCards()] = pullRandomCard();
         repaint();
         if(getUserSum()>21){
               // You Lose
            JOptionPane.showMessageDialog(null, "PlayerTwo Lost...");
            gameOn = false;
            repaint();
            PlayerTwocash-=currentBet;
            PlayerOnecash+=currentBet;
            labelOneMoney.setText(Integer.toString(PlayerOnecash));
            labelTwoMoney.setText(Integer.toString(PlayerTwocash));
         }

         if(PlayerTwocash<=0){
            JOptionPane.showMessageDialog(null,"PlayerTwo broke...");
            JOptionPane.showMessageDialog(null, "Player One Win $" + PlayerOnecash);
            System.exit(0);
         }
      }
   }   
}

class StayMulti implements ActionListener{
	public void actionPerformed(ActionEvent ae){
	      if(gameOn){
	         gameOn = false;
	         if(getUserSum()>21){
	            // PlayerOne Lose
	        	 JOptionPane.showMessageDialog(null, "PlayerOne Win!!");
	             PlayerOnecash+=currentBet;
	             PlayerTwocash-=currentBet;
	             labelOneMoney.setText(Integer.toString(PlayerOnecash));
	             labelTwoMoney.setText(Integer.toString(PlayerTwocash));
	         } 
	         if(getDealerSum()>21){
	              // PlayerOne Win
	        	 JOptionPane.showMessageDialog(null, "PlayerTwo Win!!");
	             PlayerTwocash+=currentBet;
	             PlayerOnecash-=currentBet;
	             labelOneMoney.setText(Integer.toString(PlayerOnecash));
	             labelTwoMoney.setText(Integer.toString(PlayerTwocash));
	            } else {
	               if(21-getUserSum() < 21-getDealerSum()){// You win
	                  JOptionPane.showMessageDialog(null, "PlayerTwo Win!!");
	                  PlayerTwocash+=currentBet;
		              PlayerOnecash-=currentBet;
		              labelOneMoney.setText(Integer.toString(PlayerOnecash));
		              labelTwoMoney.setText(Integer.toString(PlayerTwocash));
	               } else if(getUserSum() == getDealerSum()) {
	                  JOptionPane.showMessageDialog(null, "Tie...");
	               } else /* You Lose*/ {
	                   JOptionPane.showMessageDialog(null, "PlayerOne Win!!");
	                   PlayerOnecash+=currentBet;
	 	               PlayerTwocash-=currentBet;
	 	               labelOneMoney.setText(Integer.toString(PlayerOnecash));
	 	               labelTwoMoney.setText(Integer.toString(PlayerTwocash));
	               }
	            }
	         
	         repaint();
	         if(PlayerOnecash<=0)
	         {
	            JOptionPane.showMessageDialog(null,"PlayerOne broke...");
	            JOptionPane.showMessageDialog(null, "Player Two Win$" + PlayerTwocash);
	         System.exit(0);
	         }
	         if(PlayerTwocash<=0)
	         {
	            JOptionPane.showMessageDialog(null,"PlayerTwo broke...");
	            JOptionPane.showMessageDialog(null, "Player One Win $" + PlayerOnecash);
	         System.exit(0);
	         }
	   }
	}
}
class WalkMulti implements ActionListener{
   public void actionPerformed(ActionEvent ae){
      if(PlayerOnecash > PlayerTwocash) {
         JOptionPane.showMessageDialog(null, "Player One Win $" + PlayerOnecash);
         JOptionPane.showMessageDialog(null, "Player Two lose $" + PlayerTwocash);
         System.exit(0);
      } else if (PlayerOnecash < PlayerTwocash) {
         JOptionPane.showMessageDialog(null, "Player Two Win$" + PlayerTwocash);
         JOptionPane.showMessageDialog(null, "Player One lose $" + PlayerOnecash);
         System.exit(0);
      } else { 
      JOptionPane.showMessageDialog(null, "Tie");
      System.exit(0);
      }
   }
}
public int Select() {
   int amnt = 0;
    while(amnt<=0){
       try{
    	   while(true) {
    		   amnt = Integer.parseInt(JOptionPane.showInputDialog("1p or 2p ? (only Num) :"));
    		   if(amnt==1||amnt==2) break;
    	   }
       
       } catch (NumberFormatException nfe) {amnt=0;}
    }
    sel = amnt;
    return amnt;
}
public void SinglePlay() {
      buildTable();
      gameInit();    
      repaint(); 
      getContentPane().setBackground(Color.YELLOW);
}
public void SinglePlay2() { // 반복될때 buildTable 제외.(overriding 회피.)
	Account = getStartingAccount();
 	cash = getStartingAmmt();
 	labelMoney.setText(Integer.toString(cash));
	gameInit(); 	
	repaint(); 
	getContentPane().setBackground(Color.YELLOW);
}
public void MultiPlay() {
      buildMultiTable();
      gameInit();
      repaint(); 
      getContentPane().setBackground(Color.YELLOW);
}
public static void main(String[] args) {
      new BlackJack(); 
   }
}
