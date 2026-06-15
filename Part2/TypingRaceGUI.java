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
        setSize(1400,800);
    }

    // Builds all cards upfront to avoid duplicates
    //
    private void buildAllCards() {
        buildWelcomeCard();
        buildChoosePassageCard();
        buildChooseNumTypists();
        buildChooseMods();
        buildChooseTypistPresets();
    }

    /**************************************
     * Card names:
     *      welcome
     *      choose passage
     *      choose number of typists
     *      choose mods
     *      choose typist presets
     * 
    **************************************/

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

                    showScreen("choose mods");
                }
            }
            catch (NumberFormatException ex) {  
                errorLabel.setText("Please enter a valid integer from 2 to 6");
            }
        });

        getContentPane().add(chooseNumTypistsPanel, "choose number of typists");
    }

    private void buildChooseMods () {

        JPanel chooseModsPanel = new JPanel();
        chooseModsPanel.setLayout(new BoxLayout(chooseModsPanel, BoxLayout.Y_AXIS));

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel autocorrectPanel = new JPanel();
        autocorrectPanel.setLayout(new BoxLayout(autocorrectPanel, BoxLayout.Y_AXIS));      // 'empty borders' for inside border (padding)
        autocorrectPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel caffieneModePanel = new JPanel();
        caffieneModePanel.setLayout(new BoxLayout(caffieneModePanel, BoxLayout.Y_AXIS));
        caffieneModePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel nightShiftPanel = new JPanel();
        nightShiftPanel.setLayout(new BoxLayout(nightShiftPanel, BoxLayout.Y_AXIS));
        nightShiftPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JPanel[] panArr = {autocorrectPanel,caffieneModePanel,nightShiftPanel};

        JLabel aCorrLabel = new JLabel("Autocorrect");
        aCorrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel cModeLabel = new JLabel("Caffiene Mode");
        cModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel nShiftLabel = new JLabel("Night Shift");
        nShiftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        autocorrectPanel.add(aCorrLabel);
        caffieneModePanel.add(cModeLabel);
        nightShiftPanel.add(nShiftLabel);

        for (int i=0;i<panArr.length;i++) {
            panArr[i].add(Box.createVerticalStrut(20));
        }

        // desc being description
        JTextArea aCorrDesc = new JTextArea("* 25% chance of triggering\n" +
            "• -50% Mistype Slideback\n" +
            "\n" +
            "When enabled, the slideBack amount is halved, simulating modern phone keyboards.");
        aCorrDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        aCorrDesc.setEditable(false);
        aCorrDesc.setLineWrap(true);
        aCorrDesc.setWrapStyleWord(true);   // avoids words splitting whern wrapping text
        aCorrDesc.setPreferredSize(new Dimension(200,200));
        JTextArea cModeDesc = new JTextArea("* For first 10 turns\n" +
            "• +100% Speed Boost\n" +
            "• +20% Burnout Chance \n" +
            "\n" +
            "All typists gain a temporary speed boost for the first 10 turns, followed by increased burnout risk.");
        cModeDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        cModeDesc.setEditable(false);
        cModeDesc.setLineWrap(true);
        cModeDesc.setWrapStyleWord(true);
        cModeDesc.setPreferredSize(new Dimension(200,200));
        JTextArea nShiftDesc = new JTextArea("• -33% Typing Accuracy\n" +
            "\n" +
            "Accuracy ratings are slightly reduced across the board: everyone is tired.");
        nShiftDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        nShiftDesc.setEditable(false);
        nShiftDesc.setLineWrap(true);
        nShiftDesc.setWrapStyleWord(true);
        nShiftDesc.setPreferredSize(new Dimension(200,200));

        autocorrectPanel.add(aCorrDesc);
        caffieneModePanel.add(cModeDesc);
        nightShiftPanel.add(nShiftDesc);

        JButton[] buttonArr = new JButton[panArr.length];

        for (int i=0;i<panArr.length;i++) {
            buttonArr[i] = new JButton("Select");
            buttonArr[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panArr[i].add(Box.createVerticalStrut(20));
            panArr[i].add(buttonArr[i]);
        }

        // horizontal spacing of panels
        for (int i=0;i<panArr.length;i++) {
            optionsPanel.add(panArr[i]);
            if (i < panArr.length - 1) {
                optionsPanel.add(Box.createHorizontalStrut(20));
            }
        }

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");

        continuePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuePanel.add(continueButton);

        // vertical centre positioning
        chooseModsPanel.add(Box.createVerticalGlue());   // expands to fill top
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseModsPanel.add(optionsPanel);
        chooseModsPanel.add(Box.createVerticalStrut(10));
        chooseModsPanel.add(continuePanel);
        chooseModsPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        buttonArr[0].addActionListener(e -> {
            buttonArr[0].setOpaque(true);

            if (currentGameSpecs.isAutocorrect()) {
                currentGameSpecs.setAutocorrect(false);
                buttonArr[0].setBackground(UIManager.getColor("Button.background"));    // default colour
                buttonArr[0].setForeground(Color.BLACK);
                buttonArr[0].setText("Select");
            }
            else {
                currentGameSpecs.setAutocorrect(true);
                buttonArr[0].setBackground(Color.BLUE);
                buttonArr[0].setForeground(Color.BLUE);
                buttonArr[0].setText("Deselect");
            }
        });
        buttonArr[1].addActionListener(e -> {
            buttonArr[1].setOpaque(true);

            if (currentGameSpecs.isCaffieneMode()) {
                currentGameSpecs.setCaffieneMode(false);
                buttonArr[1].setBackground(UIManager.getColor("Button.background"));    // default colour
                buttonArr[1].setForeground(Color.BLACK);
                buttonArr[1].setText("Select");
            }
            else {
                currentGameSpecs.setCaffieneMode(true);
                buttonArr[1].setBackground(Color.BLUE);
                buttonArr[1].setForeground(Color.BLUE);
                buttonArr[1].setText("Deselect");
            }
        });
        buttonArr[2].addActionListener(e -> {
            buttonArr[2].setOpaque(true);

            if (currentGameSpecs.isNightShift()) {
                currentGameSpecs.setNightShift(false);
                buttonArr[2].setBackground(UIManager.getColor("Button.background"));    // default colour
                buttonArr[2].setForeground(Color.BLACK);
                buttonArr[2].setText("Select");
                
            }
            else {
                currentGameSpecs.setNightShift(true);
                buttonArr[2].setBackground(Color.BLUE);
                buttonArr[2].setForeground(Color.BLUE);
                buttonArr[2].setText("Deselect");
            }
        });

        // Takes to next page, to be added
        continueButton.addActionListener(e -> showScreen("choose typist presets"));

        getContentPane().add(chooseModsPanel, "choose mods");
    }

    private void buildChooseTypistPresets () {

        JPanel chooseTPresetPanel = new JPanel();
        chooseTPresetPanel.setLayout(new BoxLayout(chooseTPresetPanel, BoxLayout.Y_AXIS));

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel[] panArr = new JPanel[5];
        for (int i = 0; i < panArr.length; i++) {
            panArr[i] = new JPanel();
            panArr[i].setLayout(new BoxLayout(panArr[i], BoxLayout.Y_AXIS));
            panArr[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        String[] labelNames = {"Flash Hands", "Deadeye Typist", "Hammer Hands", "Flow State", "Two-finger Tryhard"};
        JLabel[] labelArr = new JLabel[labelNames.length];

        for (int i = 0; i < labelNames.length; i++) {
            labelArr[i] = new JLabel(labelNames[i]);
            labelArr[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panArr[i].add(labelArr[i]);
        }

        for (int i=0;i<panArr.length;i++) {
            panArr[i].add(Box.createVerticalStrut(20));
        }

        String[] descriptions = {
            "• 70% Typing accuracy\n• ~15% Burnout chance\n\nThis nimble typer is quick to flicker his hands like a flash! However those hands can burn bright and burn out fast..",
            "• 90% Typing accuracy\n• ~24% Burnout chance\n\nUsing laser-like levels of focus and precision, this typist locks onto an unsuspecting key like a target in their sight, almost never missing. The mind, however, can only hold that edge for so long..",
            "• 30% Typing accuracy\n• ~2.5% Burnout chance\n\nNot the sharpest typist, but an absolute workhorse - fatigue never gets to this reliable beast!",
            "• 60% Typing accuracy\n• ~11% Burnout chance\n\nWhen this typist hits their stride, their fingers effortlessly flow across the keyboard like water - fluid and fast, though sometimes too loose to stay precise..",
            "• 40% Typing accuracy\n• ~5% Burnout chance\n\nKeyboards may be unfamiliar territory, but what this typist lacks in experience, they make up for with iron will and unstoppable determination.",
        };
        JTextArea[] descArr = new JTextArea[descriptions.length];

        for (int i = 0; i < descriptions.length; i++) {
            descArr[i] = new JTextArea(descriptions[i]);
            descArr[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            descArr[i].setEditable(false);
            descArr[i].setLineWrap(true);
            descArr[i].setWrapStyleWord(true);
            descArr[i].setPreferredSize(new Dimension(200, 200));
            panArr[i].add(descArr[i]);
        }

        JButton[] buttonArr = new JButton[panArr.length];

        for (int i=0;i<panArr.length;i++) {
            buttonArr[i] = new JButton("Select");
            buttonArr[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panArr[i].add(Box.createVerticalStrut(20));
            panArr[i].add(buttonArr[i]);
        }

        // horizontal spacing of panels
        for (int i=0;i<panArr.length;i++) {
            optionsPanel.add(panArr[i]);
            if (i < panArr.length - 1) {
                optionsPanel.add(Box.createHorizontalStrut(20));
            }
        }

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");

        continuePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuePanel.add(continueButton);

        // vertical centre positioning
        chooseTPresetPanel.add(Box.createVerticalGlue());   // expands to fill top
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseTPresetPanel.add(optionsPanel);
        chooseTPresetPanel.add(Box.createVerticalStrut(10));
        chooseTPresetPanel.add(continuePanel);
        chooseTPresetPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        /*
        for (int i=0;i<buttonArr.length;i++) {
            buttonArr[i].addActionListener(e -> {
                currentGameSpecs.setChosenCharacter(/ADD SEAT INDEX HERE,i);
            });
        }*/

        // shared event listener - for colour change
        for (JButton button : buttonArr) {
            button.setOpaque(true);

            button.addActionListener(e -> {
                button.setBackground(Color.BLUE);
                button.setForeground(Color.BLUE);
                button.setText("Selected");
                for (JButton x : buttonArr) {
                    if (x.equals(button)) continue;
                    x.setBackground(UIManager.getColor("Button.background"));
                    x.setForeground(Color.BLACK);
                    x.setText("Select");
                }
            });
        }

        // Takes to next page, to be added
        // continueButton.addActionListener(e -> showScreen("choose keyboard presets"));

        getContentPane().add(chooseTPresetPanel, "choose typist presets");
    }
}