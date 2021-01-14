import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class TimerTest1 extends JFrame implements ActionListener{

  Timer timer;
  JLabel label;
  int sec;
  int min;

  public static void main(String[] args){
    TimerTest1 frame = new TimerTest1();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 300, 200);
    frame.setTitle("タイトル");
    frame.setVisible(true);
  }
  

  TimerTest1(){
    sec = 0;
    min = 0;
    label = new JLabel();

    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    timer = new Timer(1000 , this);

    getContentPane().add(labelPanel, BorderLayout.CENTER);

    timer.start();
  }

  public void actionPerformed(ActionEvent e){
	  
	  if(sec==60) {
		  min+=1;
		  sec=0;
	  }
    label.setText(min + ":" +sec  + " 秒");
    
	  
    if (sec >= 1000){
      timer.stop();
    }else{
      sec++;
    }
  }
}