package part2;

import part1.TypingRace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A typing race simulation. Three typists race to complete a passage of text,
 * advancing character by character — or sliding backwards when they mistype.
 *
 * @author Waseem Ghadari
 * @version  2.0
 */
public class TypingRaceGUI extends JFrame
{
    TypingRace typeRace = new TypingRace(40);

    public TypingRaceGUI (String title) {
        super(title);
    }
    public static void main (String[] args) {
        TypingRaceGUI gui = new TypingRaceGUI("Typing Race");
        gui.startScreen();
        return;
    }

    // Sets up the window frame for the game
    //
    public void frameSetup () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(1000,800);
    }

    // public void clearFrame () {
    // }

    // Opens the starting screen
    //
    public void startScreen () {
        frameSetup();

        // Panel to contain all contents (stacks them vertically)
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to the TypingRace Simulator");
        JButton startButton = new JButton("Start");

        welcomePanel.add(title);
        
        welcomePanel.add(Box.createVerticalStrut(20)); // Space between title and button
        
        // Horizontally centre the button by wrapping it in a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        welcomePanel.add(buttonPanel);

        add(welcomePanel);
        setVisible(true);

        startButton.addActionListener(e -> choosePassage());
    }

    public void choosePassage () {

        JPanel allPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel shortPanel = new JPanel();
        shortPanel.setLayout(new BoxLayout(shortPanel, BoxLayout.Y_AXIS));
        JPanel mediumPanel = new JPanel();
        mediumPanel.setLayout(new BoxLayout(mediumPanel, BoxLayout.Y_AXIS));
        JPanel longPanel = new JPanel();
        longPanel.setLayout(new BoxLayout(longPanel, BoxLayout.Y_AXIS));
        JPanel customPanel = new JPanel();
        customPanel.setLayout(new BoxLayout(customPanel, BoxLayout.Y_AXIS));

        JPanel[] panArr = {shortPanel,mediumPanel,longPanel,customPanel};

        shortPanel.add(new JLabel("Short"));
        mediumPanel.add(new JLabel("Medium"));
        longPanel.add(new JLabel("Long"));
        customPanel.add(new JLabel("Custom"));

        for (int i=0;i<panArr.length;i++) {
            panArr[i].add(Box.createVerticalStrut(20));
        }

        shortPanel.add(new JLabel("20 characters"));
        mediumPanel.add(new JLabel("40 characters"));
        longPanel.add(new JLabel("80 characters"));
        customPanel.add(new JLabel("Choose the number of characters"));

        for (int i=0;i<panArr.length;i++) {
            panArr[i].add(Box.createVerticalStrut(20));
            panArr[i].add(new JButton("Select"));
        }

        for (int i=0;i<panArr.length;i++) {
            allPanel.add(panArr[i]);
        }

        // CALL TO METHOD TO CLEAR THE FRAME

        add(allPanel);
    }
}