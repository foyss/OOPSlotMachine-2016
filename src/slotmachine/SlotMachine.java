/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slotmachine;

import java.awt.*;       // Using AWT's layouts
import java.awt.event.*; // Using AWT's event classes and listener interfaces
import javax.swing.*;    // Using Swing components and containers
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Foysal
 */


public class SlotMachine extends javax.swing.JFrame implements Runnable{

    //variables / jframes being initialised
    private boolean spinning = false;
    JButton addOne, statistics, betOne, betMax, reset, spin;
    
    JLabel lblreel1, lblreel2, lblreel3;
    JTextField creditArea, betArea;
    Reel reel;
    int credit = 10; 
    int newCredit;
    int bet;
    static int won, lost, games;  
    static double avgCredits;
    
    
    public SlotMachine(){
      
        
      Container cp = getContentPane();
      // set layout in the main panel
      cp.setLayout(new BorderLayout());
      
      //create a panel which displays the reels
    
     
     JPanel p1 = new JPanel();
     p1.setLayout(new GridLayout(1,3));
     
     lblreel1 = new JLabel("");
     p1.add(lblreel1);
     lblreel1.addMouseListener(new stopReeling());
     
     lblreel2 = new JLabel("");
     p1.add(lblreel2);
     lblreel2.addMouseListener(new stopReeling());
     
     lblreel3 = new JLabel("");
     p1.add(lblreel3);
     lblreel3.addMouseListener(new stopReeling());
     //layed out in the center
     cp.add(p1, BorderLayout.CENTER);
   
      
      //creats a panel which displays the text areas that display credit and bet
      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(1,2));
      
      creditArea = new JTextField();
      creditArea.setEditable(false);
      p2.add(creditArea);
      
      
      betArea = new JTextField();
      betArea.setEditable(false);
      p2.add(betArea);
      
      cp.add(p2, BorderLayout.NORTH);
      
      //creates a panel which displays all the buttons
      JPanel p3 = new JPanel();
      p3.setLayout(new GridLayout(2,3));
      
      
      spin = new JButton("Spin");
      p3.add(spin);
      
        addOne = new JButton("Add coin");
      p3.add(addOne);
      addOne.addActionListener(new addCoins()); 
      
      statistics = new JButton("Stats");
      p3.add(statistics);
      statistics.addActionListener(new stats());
      
      cp.add(p3, BorderLayout.SOUTH);
      
      
      
      betOne = new JButton("Bet one credit");
      p3.add(betOne);
      
      betOne.addActionListener(new betCoins());
      
      betMax = new JButton("Bet maximum ");
      p3.add(betMax);
      
      betMax.addActionListener(new betMax());
      
      reset = new JButton("Reset");
      p3.add(reset);
      reset.addActionListener(new reset());
      
      
     
      
      
      // create a reel 
      reel = new Reel(); 
      Symbol firstFace = reel.spin();
      
      lblreel1.setIcon(firstFace.getSymbol());
       lblreel2.setIcon(firstFace.getSymbol());
       lblreel3.setIcon(firstFace.getSymbol());
       
      
      // add event handler to the roll button
      MyListener myListener = new MyListener();
      spin.addActionListener(myListener);
     
      //set value for credit and bet  and display for user
    creditArea.setText("Credit: " + credit);
    
    betArea.setText("Current bet: " + bet);
    
    
      
    }
    //new class which is in action when add coin is presed
    class addCoins implements ActionListener{
    
    public void actionPerformed (ActionEvent event){
    
    credit = credit + 1;
    creditArea.setText("Credit: " + credit); 
    
    if (credit >= bet) {
        spin.setEnabled(true);
        betOne.setEnabled(true);
        
    }
    
    
    }
    
    
    }
    //new class which is in action when stats is presed
    class stats implements ActionListener{
        public void actionPerformed(ActionEvent event){
            statistics();
                    }
    }
    
    //new class which is in action when bet one coin is presed
    class betCoins implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            
            if (bet >= credit) {
                betArea.setText("Reset bet or add more coins!");
                betOne.setEnabled(false);
                betMax.setEnabled(false);
                spin.setEnabled(false);
            }
            
           

            bet = bet + 1;

            betArea.setText("Current bet: " + bet);
            
            creditArea.setText("Credit: " + credit);
            
            

        }
    }
//new class which is in action when bet max is presed
    class betMax implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            bet = bet + 3;
            betArea.setText("Current bet: " + bet);

            betMax.setEnabled(false);
            
            if (bet >= credit) {
        creditArea.setText("Credit: " + credit+ " - Add more to continue playing...");
        spin.setEnabled(false);
    }

        }
    }
    //new class which is in action when reset is presed
    class reset implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            bet = 0;
            betArea.setText("Current bet: " + 0);
            creditArea.setText("Credit: " + credit);
            
            
            spin.setEnabled(true);
            betOne.setEnabled(true);
            betMax.setEnabled(true);
            
        }
    }
    
    
    
    //new class which is in action when spin is presed is presed
    private class MyListener implements ActionListener{
     
      public void actionPerformed(ActionEvent evt) {
           
           makeSpin();
           
       
      }
      }
    

    //method makeSpin is run when called

    public void makeSpin(){

               //threads are crated for spin
                
     Thread t01 = new Thread(this);
     Thread t02 = new Thread(this);
     Thread t03 = new Thread(this);

     if (!(t01.isAlive() && t02.isAlive() && t03.isAlive())){

     t01.start();
     t02.start();
     t03.start();

     }
     spinning = true;

     }
       
       @Override 
       public void run (){
       
       try{
           while (spinning == true && spinning == true && spinning == true){
               Symbol firstReel = reel.spin();
          lblreel1.setIcon(firstReel.getSymbol());
        
          Symbol secondReel = reel.spin();
        lblreel2.setIcon(secondReel.getSymbol());
        
         Symbol thirdReel = reel.spin();
        lblreel3.setIcon(thirdReel.getSymbol());
           }
           
       } catch (UnsupportedOperationException e) {
           System.out.println("Error");
       }
       
       
       
       }
       
       //new class which is in action when user clics reel to stop
    public class stopReeling extends MouseAdapter{ 
       public void mouseClicked(MouseEvent e){
           
       if (e.getComponent().equals(lblreel1)){
           spinning = false;
       }
       
       if (e.getComponent().equals(lblreel2)){
           spinning = false;
       }
       
       if (e.getComponent().equals(lblreel3)){
           spinning = false;
       }
                
                newCredit = credit - bet;
                credit = credit - bet;
                creditArea.setText("Credit: " + credit);
           
           
        Symbol firstReel = reel.spin();
           lblreel1.setIcon(firstReel.getSymbol());
        
        Symbol secondReel = reel.spin();
        lblreel2.setIcon(secondReel.getSymbol());
        
        Symbol thirdReel = reel.spin();
        lblreel3.setIcon(thirdReel.getSymbol());
           
       
      //displays lost msg when reels do not match
        if (firstReel != secondReel || firstReel != thirdReel || secondReel != thirdReel) {
                
                betArea.setText("Sorry you lost! Bet: " + bet);
                
                games++;
            }
            //adds 1 to lost when reels do not match
             if (firstReel != secondReel && firstReel != thirdReel && secondReel != thirdReel) {
                 lost++;
             }
            if (credit < bet) {
                    creditArea.setText("Credit: " + credit + " - Add more to continue playing...");
                    spin.setEnabled(false);
                }
            
            if (firstReel == secondReel) {
            betArea.setText("Congratulations! You matched 2 in a row! Bet was " + bet);
            credit = newCredit + (bet * (firstReel.getValue()));
            
            creditArea.setText("Credit: " + credit);
           
            won++;
           games++;
        
        }
             if (secondReel == thirdReel) {
            betArea.setText("Congratulations! You matched 2 in a row! Bet was " + bet);
            credit = newCredit + (bet * (secondReel.getValue()));
            
            creditArea.setText("Credit: " + credit);
            
           won++;
        games++;
        }
             if (firstReel == thirdReel) {
            betArea.setText("Congratulations! You matched 2 in a row! Bet was " + bet);
            credit = newCredit + (bet * (thirdReel.getValue()));
            
            creditArea.setText("Credit: " + credit);
           
           
        won++;
        games++;
        }
            
            if (firstReel == secondReel && firstReel == thirdReel){
            betArea.setText("Congratulations! You matched 3 in a row! Bet was " + bet);
            credit = newCredit + (bet * (firstReel.getValue()));
            
            creditArea.setText("Credit: " + credit);
            won++;
            games++;
        }
           
            
       }  
            
        }
        //new window opens when stats is pressed
    public static void statistics(){
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Stats");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try
                {
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());   
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JTextArea statistics = new JTextArea(15, 50);
                statistics.setWrapStyleWord(true);
                statistics.setEditable(false);
                statistics.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(statistics);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField input = new JTextField(20);
                JButton save = new JButton("Save");
                
                
                DefaultCaret caret = (DefaultCaret) statistics.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                inputpanel.add(save);
                panel.add(inputpanel);
              
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                input.requestFocus();
                
                
                
                StringBuilder asteriskWon = new StringBuilder();
                
                for (int w = 0; w < won; w++)
                    asteriskWon.append("*");
                
                StringBuilder asteriskLost = new StringBuilder();
                
                for (int l = 0; l < lost; l++)
                    asteriskLost.append("*");
    
                String line1 = statistics.getText();
                
                line1 = "Won: " + won + " ";
                
                String line2 = statistics.getText();
                
                line2 = "Lost: " + lost + " ";
                
                String histogramLine = statistics.getText();
                
                histogramLine = "Histograms" + " ";
                
                String line3 = statistics.getText();
                
                line3 = "Won: " + asteriskWon + " = " + won + " ";
                
                String line4 = statistics.getText();
                
                line4 = "Lost: " + asteriskLost + " = " + lost + " ";
                
                statistics.setText(line1 + "\n" + line2 + "\n" +  "\n" + histogramLine + "\n" + line3 + "\n" + line4); //line which shows all text in textArea
                
               try {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                   Date date = new Date();
                   
                   PrintWriter out = new PrintWriter(new FileWriter(dateFormat.format(date)+ " Statistics.log", true));
                   out.println(statistics.getText());
                   out.close();
               }catch(Exception e){
                   
               }
                
            }
                    
                
                });
                }
    
    
    public static void main(String[] args) {
        // Invoke the constructor by allocating an anonymous instance
      SlotMachine myFrame = new SlotMachine();
      
      myFrame.setSize(760, 350);       // "super" Frame sets initial size
      myFrame.setTitle("Slot Machine");           // "super" Frame sets title
      myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myFrame.setVisible(true);                  // show "super" Frame
    }
    
    
 
    
}