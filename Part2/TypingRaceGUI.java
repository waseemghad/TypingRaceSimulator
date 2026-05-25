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
    CardLayout cardLayout = new CardLayout();
    GameSpecs currentGameSpecs;

    public TypingRaceGUI (String title) {
        super(title);
        frameSetup();
        buildAllCards();
        setVisible(true);
    }
    
    public static void main (String[] args) {
        new TypingRaceGUI("Typing Race");
    }

    // Sets up the window frame for the game
    //
    public void frameSetup () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(cardLayout);
        setSize(1000,800);
    }

    // Builds all cards upfront to avoid duplicates
    //
    private void buildAllCards() {
        buildWelcomeCard();
        buildChoosePassageCard();
    }

    // Helper method to show a specific screen
    //
    private void showScreen(String screenName) {
        cardLayout.show(getContentPane(), screenName);
    }

    // Opens the starting screen
    //
    private void buildWelcomeCard () {
        // Panel to contain all contents (stacks them vertically)
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to the TypingRace Simulator");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(Box.createVerticalGlue()); // Push content down from top
        welcomePanel.add(title);
        
        welcomePanel.add(Box.createVerticalStrut(20)); // Space between title and button
        
        // Horizontally centre the button by wrapping it in a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(buttonPanel);
        welcomePanel.add(Box.createVerticalGlue()); // Push content up from bottom

        getContentPane().add(welcomePanel, "welcome");

        startButton.addActionListener(e -> 
            showScreen("choose passage")
        );
    }

    // Lets you choose your passage length
    //
    private void buildChoosePassageCard () {

        JPanel choosePsgPanel = new JPanel();
        choosePsgPanel.setLayout(new BoxLayout(choosePsgPanel, BoxLayout.Y_AXIS));

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

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

        JButton[] buttonArr = new JButton[panArr.length];

        for (int i=0;i<panArr.length;i++) {
            buttonArr[i] = new JButton("Select");
            panArr[i].add(Box.createVerticalStrut(20));
            panArr[i].add(buttonArr[i]);
        }

        for (int i=0;i<panArr.length;i++) {
            optionsPanel.add(panArr[i]);
        }

        choosePsgPanel.add(Box.createVerticalGlue());   // expands to fill top
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        choosePsgPanel.add(optionsPanel);
        choosePsgPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        getContentPane().add(choosePsgPanel, "choose passage");

        buttonArr[0].addActionListener(e -> {
            currentGameSpecs = new GameSpecs("short");
        });
        buttonArr[1].addActionListener(e -> {
            currentGameSpecs = new GameSpecs("medium");
        });
        buttonArr[2].addActionListener(e -> {
            currentGameSpecs = new GameSpecs("long");
        });
        buttonArr[3].addActionListener(e -> {
            currentGameSpecs = new GameSpecs("custom");
            boolean valid = false;
            String input = null;
            while (! valid) {
                input = JOptionPane.showInputDialog(this, "Enter passage length here");
                if (input == null)
                     break;  // user clicks Cancel
                
                try {
                    int customLength = Integer.parseInt(input);
                    if (customLength >= 10 && customLength <= 200) {
                        currentGameSpecs.setPassageLength(customLength);
                        valid = true;
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Enter a number from 10 to 200")
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number (integer)");
                }
            }
        });

        // Takes to next page
        for (int i=0;i<buttonArr.length;i++) {
            buttonArr[i].addActionListener(e -> showScreen("choose number of typists"););
        }
    }
}