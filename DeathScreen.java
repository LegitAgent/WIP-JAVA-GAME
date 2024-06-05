/**
	@author Jienzel Christenzen H. Chua (231567), Martin Darius Alba (230179)
	@version 02 September 2023
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
* Creates the class for the death screen. It implements the action listener class.
**/
public class DeathScreen implements ActionListener {
    JLabel deathMessage, emptyMessage, scoreMessage; 
    JFrame frame;
    JButton quit;
    Player player;
/**
* @constructor
* @param (f): is the frame where the death screen is shown
* @param (player): gets the player who has died
**/
    public DeathScreen(JFrame f, Player player) {
        frame = f;
        this.player = player;

        deathMessage = new JLabel("YOU DIED");
        quit = new JButton("QUIT");
        scoreMessage = new JLabel("SCORE: " + player.getScore());
        emptyMessage = new JLabel();

        deathMessage.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
        deathMessage.setForeground(Color.RED);

        scoreMessage.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        scoreMessage.setForeground(Color.YELLOW);

        deathMessage.setBounds(290,100,300,50);
        scoreMessage.setBounds(325,200,200,50);

        quit.setBounds(350,300,100,50);

        quit.setFocusable(false);

        quit.addActionListener(this);
    }
/**
* @public displays the death screen when the player dies, adds a quit button to close the java application
**/
    public void showDeathScreen() {
        frame.add(deathMessage);
        frame.add(quit);

        scoreMessage.setText("SCORE: " + player.getScore());

        frame.add(scoreMessage);
        frame.add(emptyMessage);
    }
/**
* @public makes it so that the quit button can close the java application
* @param (e): the button input
**/
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == quit) {
            //https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        frame.requestFocusInWindow();
    }
}
