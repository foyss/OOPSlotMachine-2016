
package slotmachine;

import javax.swing.ImageIcon;
/**
 *
 * @author Foysal
 */
public class Symbol {
   private int value;
   private ImageIcon symbol;
   
   //first constructor
   
   public Symbol () {
       value = 0;
       symbol = null;   
   }
   
   //second constructor
   public Symbol(int value, ImageIcon symbol) {
       this.value = value;
       this.symbol = symbol;
   }
   //setter and getter method
   public void setValue(int value){
       this.value = value;
   }
   
   public void setSymbol(ImageIcon symbol) {
       this.symbol = symbol;
   }
   
   public int getValue(){
       return value;
   }
   
   public ImageIcon getSymbol(){
       return symbol;
   }
    
}
