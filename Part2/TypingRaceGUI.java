package part2;

import part1.TypingRace;
import part1.Typist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    final int MAX_PLAYERS = 6;

    private final List<Typist> raceTypists = new ArrayList<>();
    private final List<RaceLaneView> raceLaneViews = new ArrayList<>();
    private JLabel raceStatusLabel;
    private JTextArea raceSummaryArea;
    private JButton startRaceButton;
    private JButton resetRaceButton;
    private Timer raceTimer;
    private int raceTurnCount;

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
        currentGameSpecs = new GameSpecs();
        buildWelcomeCard();
        buildRulesCard();
        buildChoosePassageCard();
        buildChooseNumTypists();
        buildChooseMods();
        callBuildChooseTypistPresets();
        callBuildChooseNameAndSymbol();
        buildStartGameCard();
    }

    /**************************************
     * Card names:
     *      welcome
     *      rules
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
            showScreen("rules")
        );
    }
    
    // Displays the rules and instructions for the game
    //
    private void buildRulesCard () {

        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));

        // page title
        JLabel titleLabel = new JLabel("Rules and Instructions");
        JTextArea rulesTextArea = new JTextArea();
        JButton readyButton = new JButton("Ready!");

        String rulesText = "In this typing simulation, you will race with your friends to see whose typist reaches the end first and wins! \n" + //
                        "\n" + //
                        "The game is round based. Each round, your typist could:\n" + //
                        " - type correctly to advance one step\n" + //
                        " - mistype to fall backwards two steps\n" + //
                        " - burnout and stay stuck for 5 rounds!\n" + //
                        "\n" + //
                        "Choose from various typists with different levels of accuracy. Higher accuracy means less mistypes, though it also means higher chances of burning out.. The winner gets their accuracy bumped up - go for another round to see if the others can catch up!\n" + //
                        "\n" + //
                        "You can also select game modifiers to shake up the race and keep things competitive! \n" + //
                        "\n" + //
                        "\n" + //
                        "Are you ready?";

        rulesTextArea.setText(rulesText);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setPreferredSize(new Dimension(500,500));
        rulesTextArea.setMaximumSize(new Dimension(500,500));
        rulesTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding

        // vertical centre positioning
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        readyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Takes to next page
        readyButton.addActionListener(e -> {
            showScreen("choose passage");
        });

        // add to main panel
        rulesPanel.add(Box.createVerticalGlue());   // expands to fill top
        rulesPanel.add(titleLabel);
        rulesPanel.add(Box.createVerticalStrut(30));
        rulesPanel.add(rulesTextArea);
        rulesPanel.add(Box.createVerticalStrut(20));
        rulesPanel.add(readyButton);
        rulesPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        getContentPane().add(rulesPanel, "rules");
    }

    // User chooses passage length (number of characters)
    //
    private void buildChoosePassageCard () {

        JPanel choosePsgPanel = new JPanel();
        choosePsgPanel.setLayout(new BoxLayout(choosePsgPanel, BoxLayout.Y_AXIS));

        // page title
        JLabel titleLabel = new JLabel("Choose your passage length");

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 'padding' for inside border (empty borders)
        JPanel shortPanel = new JPanel();
        shortPanel.setLayout(new BoxLayout(shortPanel, BoxLayout.Y_AXIS));
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

        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        choosePsgPanel.add(Box.createVerticalGlue());   // expands to fill top
        choosePsgPanel.add(titleLabel);
        choosePsgPanel.add(Box.createVerticalStrut(30));
        choosePsgPanel.add(optionsPanel);
        choosePsgPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        getContentPane().add(choosePsgPanel, "choose passage");

        buttonArr[0].addActionListener(e -> {
            currentGameSpecs.setPassageLength(currentGameSpecs.controlledSetPassageLength("short"));
        });
        buttonArr[1].addActionListener(e -> {
            currentGameSpecs.setPassageLength(currentGameSpecs.controlledSetPassageLength("medium"));
        });
        buttonArr[2].addActionListener(e -> {
            currentGameSpecs.setPassageLength(currentGameSpecs.controlledSetPassageLength("long"));
        });
        buttonArr[3].addActionListener(e -> {
            currentGameSpecs.controlledSetPassageLength("custom");
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

        // page title
        JLabel titleLabel = new JLabel("Choose the number of typists (players)");

        JLabel askHowMany = new JLabel("How many typists are playing? (maximum 6)");
        askHowMany.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField inputField = new JTextField(2);
        inputField.setMaximumSize(new Dimension(200, inputField.getPreferredSize().height));
            // constrains the inputField height to its preferred size,
            // so it doesn't fill up the screen because of BoxLayout.Y_AXIS
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        chooseNumTypistsPanel.add(Box.createVerticalGlue());
        chooseNumTypistsPanel.add(titleLabel);
        chooseNumTypistsPanel.add(Box.createVerticalStrut(30));
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
                    currentGameSpecs.setSeatCount(numTypists);  // also decares chosenTypists array
                    currentGameSpecs.declareCharacterNamesArray(numTypists);
                    currentGameSpecs.declareCharacterSymbolsArray(numTypists);
                    
                    for (int i=0;i<numTypists;i++) {
                        currentGameSpecs.nullifyCharacterPreset(i);    // reset chosen characters
                        currentGameSpecs.nullifyCharacterName(i);      // reset chosen names
                        currentGameSpecs.nullifyCharacterSymbol(i);    // reset chosen symbols
                    }

                    showScreen("choose mods");
                }
            }
            catch (NumberFormatException ex) {  // also handles null input
                errorLabel.setText("Please enter a valid integer from 2 to 6");
            }
        });

        getContentPane().add(chooseNumTypistsPanel, "choose number of typists");
    }

    private void buildChooseMods () {

        JPanel chooseModsPanel = new JPanel();
        chooseModsPanel.setLayout(new BoxLayout(chooseModsPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Choose your game modifiers");

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
            "\n" +
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
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add to main panel
        chooseModsPanel.add(Box.createVerticalGlue());   // expands to fill top
        chooseModsPanel.add(titleLabel);
        chooseModsPanel.add(Box.createVerticalStrut(30));
        chooseModsPanel.add(optionsPanel);
        chooseModsPanel.add(Box.createVerticalStrut(10));
        chooseModsPanel.add(continuePanel);
        chooseModsPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        for (JButton button:buttonArr) button.setOpaque(true);

        buttonArr[0].addActionListener(e -> {
            if (currentGameSpecs.isAutocorrect()) {
                currentGameSpecs.setAutocorrect(false);
                buttonDeselectColours(buttonArr[0]);
            }
            else {
                currentGameSpecs.setAutocorrect(true);
                buttonSelectColours(buttonArr[0]);
            }
        });
        buttonArr[1].addActionListener(e -> {
            if (currentGameSpecs.isCaffieneMode()) {
                currentGameSpecs.setCaffieneMode(false);
                buttonDeselectColours(buttonArr[1]);
            }
                
            else {
                currentGameSpecs.setCaffieneMode(true);
                buttonSelectColours(buttonArr[1]);
            }
        });
        buttonArr[2].addActionListener(e -> {
            if (currentGameSpecs.isNightShift()) {
                currentGameSpecs.setNightShift(false);
                buttonDeselectColours(buttonArr[2]);
            }
            else {
                currentGameSpecs.setNightShift(true);
                buttonSelectColours(buttonArr[2]);
            }
        });

        // Takes to next page
        continueButton.addActionListener(e -> showScreen("choose typist presets 0"));

        getContentPane().add(chooseModsPanel, "choose mods");
    }

    // changes made when button is deselected
    private void buttonDeselectColours (JButton button) {
        button.setBackground(UIManager.getColor("Button.background"));    // default colour
        button.setForeground(Color.BLACK);
        button.setText("Select");
    }

    // changes made when button is selected
    private void buttonSelectColours (JButton button) {
        button.setBackground(Color.BLUE);
        button.setForeground(Color.BLUE);
        button.setText("Deselect");
    }

    // Builds a card for a player to choose their typist
    // seatNum starting from 0
    private void buildChooseTypistPresets (int seatNum) {

        JPanel chooseTPresetPanel = new JPanel();
        chooseTPresetPanel.setLayout(new BoxLayout(chooseTPresetPanel, BoxLayout.Y_AXIS));

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // page title
        String title = "Player " + (seatNum + 1) + ", choose your Typist.";
        JLabel titleLabel = new JLabel(title);

        JPanel[] panArr = new JPanel[5];
        for (int i=0;i<panArr.length;i++) {
            panArr[i] = new JPanel();
            panArr[i].setLayout(new BoxLayout(panArr[i], BoxLayout.Y_AXIS));
            panArr[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        String[] labelNames = {"Flash Hands", "Deadeye Typist", "Hammer Hands", "Flow State", "Two-finger Tryhard"};
        JLabel[] labelArr = new JLabel[labelNames.length];

        for (int i=0;i<labelNames.length;i++) {
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

        for (int i=0;i<descriptions.length;i++) {
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

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        // vertical centre positioning
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add to main panel
        chooseTPresetPanel.add(Box.createVerticalGlue());   // expands to fill top
        chooseTPresetPanel.add(titleLabel);
        chooseTPresetPanel.add(Box.createVerticalStrut(30));
        chooseTPresetPanel.add(optionsPanel);
        chooseTPresetPanel.add(Box.createVerticalStrut(10));
        chooseTPresetPanel.add(continuePanel);
        chooseTPresetPanel.add(Box.createVerticalStrut(10));
        chooseTPresetPanel.add(errorLabel);
        chooseTPresetPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        // records a player's typist choice
        for (int i=0;i<buttonArr.length;i++) {
            int presetNum = i;
            buttonArr[presetNum].addActionListener(e -> {
                currentGameSpecs.setTypist(seatNum,presetNum);
            });
        }

        // changes colour of selected button only, resets others
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

        // Takes to next page
        continueButton.addActionListener(e -> {
            int next = seatNum + 1;

            if (currentGameSpecs.getTypist(seatNum) == -1) {
                errorLabel.setText("Please select a typist before continuing.");
                return;
            }
            if (next < currentGameSpecs.getSeatCount())
                showScreen("choose typist presets " + next);
            else
                showScreen("choose name and symbol 0");
        });

        getContentPane().add(chooseTPresetPanel, "choose typist presets " + seatNum);
        // e.g. "choose typist presets 2" for player 3
    }

    // repeatedy calls buildChooseTypistPresets to build a card for each player choice
    // builds maximum amount (6), even if all aren't used
    private void callBuildChooseTypistPresets () {
        for (int i=0;i<MAX_PLAYERS;i++) {
            buildChooseTypistPresets(i);
        }
    }

    private void buildChooseNameAndSymbol (int seatNum) {
        JPanel chooseNameAndSymbolPanel = new JPanel();
        chooseNameAndSymbolPanel.setLayout(new BoxLayout(chooseNameAndSymbolPanel, BoxLayout.Y_AXIS));

        String title = "Player " + (seatNum+1) + ", choose your Typist's name and symbol";
        JLabel titleLabel = new JLabel(title);

        JPanel panelsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel namePanel = new JPanel();
        JPanel symbolPanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(200,400));
        symbolPanel.setPreferredSize(new Dimension(200,400));

        JPanel[] panelArr = {namePanel,symbolPanel};

        for (JPanel p : panelArr) {
            p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
            p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }

        // HTML for text wrap
        JLabel nameLabel = new JLabel("<html>Type in your player name</html>");
        JLabel symbolLabel = new JLabel("<html>Type in your symbol (Choose any character you want!)</html>");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        symbolLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JTextField nameInput = new JTextField(2);
        nameInput.setMaximumSize(new Dimension(200, nameInput.getPreferredSize().height));
        nameInput.setPreferredSize(new Dimension(200, nameInput.getPreferredSize().height));
        JTextField symbolInput = new JTextField(2);
        symbolInput.setMaximumSize(new Dimension(200, symbolInput.getPreferredSize().height));
        symbolInput.setPreferredSize(new Dimension(200, symbolInput.getPreferredSize().height));

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        
        for (JPanel p : panelArr) p.add(Box.createVerticalGlue());

        namePanel.add(nameLabel);
        namePanel.add(Box.createVerticalStrut(100));
        namePanel.add(nameInput);

        symbolPanel.add(symbolLabel);
        symbolPanel.add(Box.createVerticalStrut(100));
        symbolPanel.add(symbolInput);

        for (JPanel p : panelArr) p.add(Box.createVerticalGlue());

        panelsPanel.add(namePanel);
        panelsPanel.add(Box.createHorizontalStrut(20));
        panelsPanel.add(symbolPanel);
    
        JButton continueButton = new JButton("Continue");

        // vertical centre positioning
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        symbolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        symbolInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        continueButton.addActionListener(e -> {
            String nameString = nameInput.getText();
            String symbolString = symbolInput.getText();
            try {
                nameString = nameString.trim();
                symbolString = symbolString.trim();
                if (symbolString.length() < 1 || nameString.equals("") || symbolString==null||nameString==null) {
                    errorLabel.setText("Please fill in both fields");
                }
                else if (symbolString.length() > 1) {
                    errorLabel.setText("Please type in only one character for your symbol \n" +
                    "(note: emojis aren't supported)");
                }
                else {  // successful input
                    errorLabel.setText("");
                    char symbolChar = symbolString.charAt(0);
                    int nextPg = seatNum + 1;

                    currentGameSpecs.setCharacterName(seatNum,nameString);
                    currentGameSpecs.setCharacterSymbol(seatNum,symbolChar);
                    // takes to next page
                    if (nextPg < currentGameSpecs.getSeatCount())
                        showScreen("choose name and symbol " + nextPg);
                    else {
                        prepareRaceScreen();
                        showScreen("start game");   // to be created
                        startGuiRace();
                    }
                }
            }
            catch (NullPointerException | IndexOutOfBoundsException ex) {  // handles null input
                errorLabel.setText("Please fill in both fields");
            }
        });

        // add to main panel
        chooseNameAndSymbolPanel.add(Box.createVerticalGlue());   // expands to fill top
        chooseNameAndSymbolPanel.add(titleLabel);
        chooseNameAndSymbolPanel.add(Box.createVerticalStrut(30));
        chooseNameAndSymbolPanel.add(panelsPanel);
        chooseNameAndSymbolPanel.add(Box.createVerticalStrut(30));
        chooseNameAndSymbolPanel.add(continueButton);
        chooseNameAndSymbolPanel.add(Box.createVerticalStrut(20));
        chooseNameAndSymbolPanel.add(errorLabel);
        chooseNameAndSymbolPanel.add(Box.createVerticalGlue());   // expands to fill bottom

        getContentPane().add(chooseNameAndSymbolPanel, "choose name and symbol " + seatNum);
        // e.g. "choose name and symbol 2" for player 3
    }

    // repeatedy calls buildChooseNameAndSymbol to build a card for each player's inputs
    // builds maximum amount (6), even if all aren't used
    private void callBuildChooseNameAndSymbol () {
        for (int i=0;i<MAX_PLAYERS;i++) {
            buildChooseNameAndSymbol(i);
        }
    }

    private void buildStartGameCard() {
        JPanel startGamePanel = new JPanel(new BorderLayout(20, 20));
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Race Day");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        raceStatusLabel = new JLabel("Press Start Race to begin.");
        raceStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        raceSummaryArea = new JTextArea(6, 60);
        raceSummaryArea.setEditable(false);
        raceSummaryArea.setLineWrap(true);
        raceSummaryArea.setWrapStyleWord(true);
        raceSummaryArea.setBackground(UIManager.getColor("Panel.background"));
        raceSummaryArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(raceStatusLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(raceSummaryArea);

        JPanel lanesPanel = new JPanel();
        lanesPanel.setLayout(new BoxLayout(lanesPanel, BoxLayout.Y_AXIS));
        lanesPanel.setBorder(BorderFactory.createTitledBorder("Race Lanes"));

        raceLaneViews.clear();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            RaceLaneView laneView = new RaceLaneView(i);
            raceLaneViews.add(laneView);
            lanesPanel.add(laneView.panel);
            lanesPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane lanesScrollPane = new JScrollPane(lanesPanel);
        lanesScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        startRaceButton = new JButton("Start Race");
        resetRaceButton = new JButton("Reset Race");
        startRaceButton.setEnabled(false);
        resetRaceButton.setEnabled(false);
        controlsPanel.add(startRaceButton);
        controlsPanel.add(resetRaceButton);

        startRaceButton.addActionListener(e -> startGuiRace());
        resetRaceButton.addActionListener(e -> prepareRaceScreen());

        startGamePanel.add(topPanel, BorderLayout.NORTH);
        startGamePanel.add(lanesScrollPane, BorderLayout.CENTER);
        startGamePanel.add(controlsPanel, BorderLayout.SOUTH);

        getContentPane().add(startGamePanel, "start game");
    }

    private void prepareRaceScreen() {
        stopRaceTimer();
        typeRace.applyGameSpecs(currentGameSpecs);
        raceTurnCount = 0;

        for (RaceLaneView laneView : raceLaneViews) {
            laneView.clear();
        }

        int seatCount = currentGameSpecs.getSeatCount();
        if (raceTypists.size() == seatCount && seatCount > 0) {
            for (int seatIndex = 0; seatIndex < seatCount; seatIndex++) {
                Typist typist = raceTypists.get(seatIndex);
                typist.resetToStart();
                typist.resetMistypeCounter();
                typist.setMistype(false);
                raceLaneViews.get(seatIndex).setVisible(true);
                raceLaneViews.get(seatIndex).setTypistLabel(typist);
                raceLaneViews.get(seatIndex).setLaneText(buildLaneText(typist));
                raceLaneViews.get(seatIndex).setStatusText("Ready");
            }
        }
        else {
            raceTypists.clear();
            for (int seatIndex = 0; seatIndex < seatCount; seatIndex++) {
                Typist typist = createTypistForSeat(seatIndex);
                raceTypists.add(typist);
                raceLaneViews.get(seatIndex).setVisible(true);
                raceLaneViews.get(seatIndex).setTypistLabel(typist);
                raceLaneViews.get(seatIndex).setLaneText(buildLaneText(typist));
                raceLaneViews.get(seatIndex).setStatusText("Ready");
            }
        }

        raceSummaryArea.setText(buildRaceSummary());
        raceStatusLabel.setText("Ready. Press Start Race to begin.");
        startRaceButton.setEnabled(true);
        resetRaceButton.setEnabled(true);
    }

    private void startGuiRace() {
        if (raceTimer != null && raceTimer.isRunning()) {
            return;
        }

        raceTurnCount = 0;
        raceStatusLabel.setText("Race in progress...");
        startRaceButton.setEnabled(false);
        resetRaceButton.setEnabled(false);

        raceTimer = new Timer(200, e -> advanceGuiRace());
        raceTimer.start();
    }

    private void advanceGuiRace() {
        raceTurnCount++;

        for (int i = 0; i < raceTypists.size(); i++) {
            advanceGuiTypist(raceTypists.get(i));
        }

        refreshRaceDisplay();

        Typist winner = getWinner();
        if (winner != null) {
            stopRaceTimer();
            winner.setAccuracy(currentGameSpecs.roundAccuracy(winner.getAccuracy() + currentGameSpecs.getWinnerAccuracyIncrease()));
            raceSummaryArea.setText(buildRaceSummary());
            refreshRaceDisplay();
            raceStatusLabel.setText("And the winner is... " + winner.getName() + "!");
            JOptionPane.showMessageDialog(this,
                "And the winner is... " + winner.getName() + "!\nFinal accuracy: " + currentGameSpecs.roundAccuracy(winner.getAccuracy()));
            startRaceButton.setEnabled(false);
            resetRaceButton.setEnabled(true);
        }
    }

    private void advanceGuiTypist(Typist typist) {
        if (typist.isBurntOut()) {
            typist.recoverFromBurnout();
            return;
        }

        if (determineBurnout(typist)) {
            typist.burnOut(currentGameSpecs.getBurnoutDuration());
            typist.setMistype(false);
            typist.resetMistypeCounter();
            typist.setAccuracy(roundTo3dp(typist.getAccuracy() - currentGameSpecs.getBurnoutAccuracyDecrease()));
        }
        else if (determineMistype(typist)) {
            int slideBackAmount = currentGameSpecs.isAutocorrect() ? 1 : currentGameSpecs.getSlideBackAmount();
            typist.slideBack(slideBackAmount);
            typist.setMistype(true);
            typist.plusMistypeCounter();
        }
        else {
            typist.typeCharacter();
            if (currentGameSpecs.isCaffieneMode() && raceTurnCount <= 10) {
                typist.typeCharacter();
            }
            typist.setMistype(false);
            typist.resetMistypeCounter();
        }
    }

    private boolean determineBurnout(Typist typist) {
        double burnoutChance = currentGameSpecs.getBurnoutBaseChance() * typist.getAccuracy() * typist.getAccuracy();
        if (currentGameSpecs.isCaffieneMode() && raceTurnCount <= 10) {
            burnoutChance += 0.20;
        }
        return Math.random() < burnoutChance;
    }

    private boolean determineMistype(Typist typist) {
        return Math.random() < (1 - typist.getAccuracy()) * currentGameSpecs.getMistypeBaseChance();
    }

    private Typist getWinner() {
        for (Typist typist : raceTypists) {
            if (typist.getProgress() >= currentGameSpecs.getPassageLength()) {
                return typist;
            }
        }
        return null;
    }

    private void refreshRaceDisplay() {
        for (int i = 0; i < raceLaneViews.size(); i++) {
            if (i < raceTypists.size()) {
                Typist typist = raceTypists.get(i);
                raceLaneViews.get(i).setLaneText(buildLaneText(typist));
                raceLaneViews.get(i).setStatusText(buildStatusText(typist));
            }
        }
    }

    private String buildStatusText(Typist typist) {
        if (typist.isBurntOut()) {
            return "Burnt out (" + typist.getBurnoutTurnsRemaining() + " turns left)";
        }
        if (typist.isMistype()) {
            return "Just mistyped";
        }
        if (typist.getProgress() >= currentGameSpecs.getPassageLength()) {
            return "Finished";
        }
        return "Racing";
    }

    private String buildLaneText(Typist typist) {
        int passageLength = currentGameSpecs.getPassageLength();
        int progress = Math.min(typist.getProgress(), passageLength);
        StringBuilder lane = new StringBuilder();
        lane.append('|');
        for (int i = 0; i < progress; i++) {
            lane.append(' ');
        }
        lane.append(typist.getSymbol());
        if (typist.isBurntOut()) {
            lane.append('~');
        }
        else if (typist.isMistype()) {
            lane.append('<');
        }
        for (int i = progress + 1; i < passageLength; i++) {
            lane.append(' ');
        }
        lane.append('|');
        return lane.toString();
    }

    private Typist createTypistForSeat(int seatIndex) {
        int presetIndex = currentGameSpecs.getTypist(seatIndex);
        double accuracy = currentGameSpecs.getPresetAccuracy(presetIndex);
        if (currentGameSpecs.isNightShift()) {
            accuracy = roundTo3dp(accuracy * 0.67);
        }

        Typist typist = new Typist(
            currentGameSpecs.getCharacterSymbol(seatIndex),
            currentGameSpecs.getCharacterName(seatIndex),
            accuracy
        );
        typist.resetToStart();
        typist.resetMistypeCounter();
        typist.setMistype(false);
        return typist;
    }

    private String buildRaceSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Passage length: ").append(currentGameSpecs.getPassageLength()).append("\n");
        summary.append("Modifiers: ");
        summary.append(currentGameSpecs.isAutocorrect() ? "Autocorrect on" : "Autocorrect off");
        summary.append(", ");
        summary.append(currentGameSpecs.isCaffieneMode() ? "Caffiene mode on" : "Caffiene mode off");
        summary.append(", ");
        summary.append(currentGameSpecs.isNightShift() ? "Night shift on" : "Night shift off");
        summary.append("\n\nPlayers:\n");

        for (int i = 0; i < raceTypists.size(); i++) {
            Typist typist = raceTypists.get(i);
            summary.append(i + 1)
                .append(". ")
                .append(typist.getName())
                .append(" (")
                .append(typist.getSymbol())
                .append(") - accuracy ")
                .append(currentGameSpecs.roundAccuracy(typist.getAccuracy()))
                .append("\n");
        }

        return summary.toString();
    }

    private void stopRaceTimer() {
        if (raceTimer != null) {
            raceTimer.stop();
        }
    }

    private double roundTo3dp(double number) {
        return (double)Math.round(number * 1000) / 1000;
    }

    private static class RaceLaneView {
        private final JPanel panel;
        private final JLabel titleLabel;
        private final JTextArea laneText;
        private final JLabel statusLabel;

        RaceLaneView(int laneNumber) {
            panel = new JPanel(new BorderLayout(10, 5));
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            titleLabel = new JLabel("Player " + (laneNumber + 1));
            laneText = new JTextArea(1, 50);
            laneText.setEditable(false);
            laneText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
            laneText.setBackground(UIManager.getColor("Panel.background"));
            laneText.setBorder(BorderFactory.createEmptyBorder());
            statusLabel = new JLabel("");

            panel.add(titleLabel, BorderLayout.WEST);
            panel.add(laneText, BorderLayout.CENTER);
            panel.add(statusLabel, BorderLayout.EAST);
            panel.setVisible(false);
        }

        void setVisible(boolean visible) {
            panel.setVisible(visible);
        }

        void setTypistLabel(Typist typist) {
            titleLabel.setText(typist.getName() + " (" + typist.getSymbol() + ")");
        }

        void setLaneText(String text) {
            laneText.setText(text);
        }

        void setStatusText(String text) {
            statusLabel.setText(text);
        }

        void clear() {
            titleLabel.setText("");
            laneText.setText("");
            statusLabel.setText("");
            panel.setVisible(false);
        }
    }

    private void bridge () {
        typeRace.setGuiModeTrue();
        
    }
}