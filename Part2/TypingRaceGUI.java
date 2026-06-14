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
        buildChooseNumTypists();
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
        shortPanel.setLayout(new BoxLayout(shortPanel, BoxLayout.Y_AXIS));      // 'empty borders' for inside border (padding)
        shortPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel mediumPanel = new JPanel();
        mediumPanel.setLayout(new BoxLayout(mediumPanel, BoxLayout.Y_AXIS));
        mediumPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel longPanel = new JPanel();
        longPanel.setLayout(new BoxLayout(longPanel, BoxLayout.Y_AXIS));
        longPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel customPanel = new JPanel();
        customPanel.setLayout(new BoxLayout(customPanel, BoxLayout.Y_AXIS));
        customPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JPanel[] panArr = {shortPanel,mediumPanel,longPanel,customPanel};

        JLabel shortLabel = new JLabel("Short");
        shortLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel mediumLabel = new JLabel("Medium");
        mediumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel longLabel = new JLabel("Long");
        longLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel customLabel = new JLabel("Custom");
        customLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        shortPanel.add(shortLabel);
        mediumPanel.add(mediumLabel);
        longPanel.add(longLabel);
        customPanel.add(customLabel);

        for (int i=0;i<panArr.length;i++) {
            panArr[i].add(Box.createVerticalStrut(20));
        }

        // desc being description
        JLabel shortDescLabel = new JLabel("20 characters");
        shortDescLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel mediumDescLabel = new JLabel("40 characters");
        mediumDescLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel longDescLabel = new JLabel("80 characters");
        longDescLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel customDescLabel = new JLabel("Choose the number of characters");
        customDescLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        shortPanel.add(shortDescLabel);
        mediumPanel.add(mediumDescLabel);
        longPanel.add(longDescLabel);
        customPanel.add(customDescLabel);

        JButton[] buttonArr = new JButton[panArr.length];

        for (int i=0;i<panArr.length;i++) {
            buttonArr[i] = new JButton("Select");
            buttonArr[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panArr[i].add(Box.createVerticalStrut(20));
            panArr[i].add(buttonArr[i]);
        }

        for (int i=0;i<panArr.length;i++) {
            optionsPanel.add(panArr[i]);
            if (i < panArr.length - 1) {
                optionsPanel.add(Box.createHorizontalStrut(20));
            }
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
            int customLength = 0;
            while (! valid) {
                input = JOptionPane.showInputDialog(this, "Enter passage length here (10 - 200)");
                if (input == null)
                     break;  // user clicks Cancel
                
                try {
                    customLength = Integer.parseInt(input);
                    if (customLength >= 10 && customLength <= 200) {
                        currentGameSpecs.setPassageLength(customLength);
                        valid = true;
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Enter a number from 10 to 200");
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number (integer)");
                }
            }
            if (customLength != 0)  // 0 being null operator
                showScreen("choose number of typists");
        });

        // Takes to next page
        for (int i=0;i<buttonArr.length - 1;i++) { 
            buttonArr[i].addActionListener(e -> {
                showScreen("choose number of typists");
            });
        }
    }

    // User chooses number of typists (players)
    //
    private void buildChooseNumTypists () {

        JPanel chooseNumTypistsPanel = new JPanel();
        chooseNumTypistsPanel.setLayout(new BoxLayout(chooseNumTypistsPanel, BoxLayout.Y_AXIS));

        JLabel askHowMany = new JLabel("How many typists are playing? (maximum 6)");
        askHowMany.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField inputField = new JTextField(2);
        inputField.setMaximumSize(new Dimension(200, inputField.getPreferredSize().height));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
            // constrains the inputField height to its preferred size,
            // so it doesn't fill up the screen because of BoxLayout.Y_AXIS

        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        chooseNumTypistsPanel.add(Box.createVerticalGlue());
        chooseNumTypistsPanel.add(askHowMany);
        chooseNumTypistsPanel.add(Box.createVerticalStrut(20));
        chooseNumTypistsPanel.add(inputField);
        chooseNumTypistsPanel.add(Box.createVerticalStrut(20));
        chooseNumTypistsPanel.add(submitBtn);
        chooseNumTypistsPanel.add(Box.createVerticalStrut(20));
        chooseNumTypistsPanel.add(errorLabel);
        chooseNumTypistsPanel.add(Box.createVerticalGlue());

        submitBtn.addActionListener(e -> {
            try {
                int numTypists = Integer.parseInt(inputField.getText());
                if (numTypists < 2 || numTypists > 6) {
                    errorLabel.setText("Please enter a valid number from 2 to 6");
                }
                else {  // successful input
                    errorLabel.setText("");
                    currentGameSpecs.setSeatCount(numTypists);

                    // showScreen("choose difficulty mods"); ~~[to be added]
                }
            }
            catch (NumberFormatException ex) {  
                errorLabel.setText("Please enter a valid integer from 2 to 6");
            }
        });

        getContentPane().add(chooseNumTypistsPanel, "choose number of typists");
    }
}