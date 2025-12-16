import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.File; // To check if files exist

public class GameWindow {
    private JFrame frame;
    private JButton[][] boardButtons;
    private Character[][] boardData;
    
    // UI Components
    private JLabel turnLabel;        
    private JLabel instructionLabel; 
    private JLabel p1StatsLabel;
    private JLabel p2StatsLabel;
    
    // --- NEW: ICON STORAGE ---
    private ImageIcon barbarianIcon;
    private ImageIcon archerIcon;
    private ImageIcon wizardIcon;
    private ImageIcon pekkaIcon;

    // Logic Variables
    private volatile Point lastClick = null; 
    private volatile String lastAction = null; 

    private int rows = 8;
    private int cols = 8;

    public GameWindow(Character[][] boardData) {
        this.boardData = boardData;
        this.boardButtons = new JButton[rows][cols];
        
        // 1. LOAD IMAGES BEFORE UI STARTS
        loadIcons();
        
        initializeUI();
    }

    // --- NEW METHOD: Load and Resize Images ---
    private void loadIcons() {
        // We resize images to 50x50 to fit nicely in the grid buttons
        barbarianIcon = loadAndResize("assets/barbarian_fixed.png");
        archerIcon    = loadAndResize("assets/archer.png");
        wizardIcon    = loadAndResize("assets/wizard.png");
        pekkaIcon     = loadAndResize("assets/pekka_fixed.png");
    }

    private ImageIcon loadAndResize(String path) {
        try {
            // Check if file exists first to avoid crashes
            if (!new File(path).exists()) {
                System.out.println("ERROR: Image not found at " + path);
                return null;
            }
            
            ImageIcon original = new ImageIcon(path);
            Image img = original.getImage();
            // Resize to 60x60 pixels (Adjust this if your buttons are bigger/smaller)
            Image newImg = img.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (Exception e) {
            System.out.println("Could not load image: " + path);
            return null;
        }
    }

    private void initializeUI() {
        frame = new JFrame("Clash Tactics");
        frame.setSize(800, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // TOP PANEL (Stats)
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setPreferredSize(new Dimension(800, 50));
        
        p1StatsLabel = createStatLabel("P1: 100 HP");
        p2StatsLabel = createStatLabel("P2: 100 HP");
        
        topPanel.add(p1StatsLabel);
        topPanel.add(p2StatsLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL (Grid)
        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton btn = new JButton();
                btn.setFocusable(false);
                
                // IMPORTANT: Reset background to white/gray
                if ((i + j) % 2 == 0) btn.setBackground(new Color(230, 230, 230));
                else btn.setBackground(Color.WHITE);

                final int r = i;
                final int c = j;
                
                btn.addActionListener(e -> {
                    lastClick = new Point(c, r); 
                });

                boardButtons[i][j] = btn;
                boardPanel.add(btn);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        // BOTTOM PANEL (Controls)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        turnLabel = new JLabel("Initializing...");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        instructionLabel = new JLabel("Wait...");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        
        infoPanel.add(turnLabel);
        infoPanel.add(instructionLabel);
        bottomPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton moveBtn = new JButton("MOVE");
        JButton attackBtn = new JButton("ATTACK");
        styleButton(moveBtn);
        styleButton(attackBtn);

        moveBtn.addActionListener(e -> lastAction = "MOVE");
        attackBtn.addActionListener(e -> lastAction = "ATTACK");

        actionPanel.add(moveBtn);
        actionPanel.add(attackBtn);
        bottomPanel.add(actionPanel, BorderLayout.CENTER);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        refresh(); 
    }

    // --- LOGIC METHODS ---

    public String waitForAction() {
        lastAction = null; 
        instructionLabel.setText("Choose Action: Click MOVE or ATTACK");
        while (lastAction == null) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        return lastAction;
    }

    public Point waitForClick() {
        lastClick = null; 
        instructionLabel.setText(">>> Select a tile on the board <<<");
        while (lastClick == null) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        return lastClick;
    }

    public void updateStats(String p1Info, String p2Info, String turnInfo) {
        p1StatsLabel.setText(p1Info);
        p2StatsLabel.setText(p2Info);
        turnLabel.setText(turnInfo);
    }

    // --- UPDATED REFRESH METHOD FOR ICONS ---
    public void refresh() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Character c = boardData[i][j];
                
                // Clear previous icon and text
                boardButtons[i][j].setIcon(null);
                boardButtons[i][j].setText("");
                
                if (c != null) {
                    String name = c.getName();
                    
                    // 1. Try to set the Icon based on name
                    if (name.contains("Barbarian")) boardButtons[i][j].setIcon(barbarianIcon);
                    else if (name.contains("Archer")) boardButtons[i][j].setIcon(archerIcon);
                    else if (name.contains("Wizard")) boardButtons[i][j].setIcon(wizardIcon);
                    else if (name.contains("PEKKA")) boardButtons[i][j].setIcon(pekkaIcon);
                    
                    // 2. Fallback: If icon failed to load, set Text
                    if (boardButtons[i][j].getIcon() == null) {
                        boardButtons[i][j].setText(name.substring(0, 1));
                    }

                    // 3. TEAM COLORS (Borders)
                    // Since we can't color the text anymore, we change the Border or Background
                    // Player 1 (Top) = Red Border, Player 2 (Bottom) = Blue Border
                    if (c.getPosition().getY() < 4) {
                        boardButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    } else {
                        boardButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    }
                } else {
                    // Reset border for empty cells
                    boardButtons[i][j].setBorder(UIManager.getBorder("Button.border"));
                }
            }
        }
    }

    private JLabel createStatLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        return lbl;
    }
    
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    public void showMessage(String message, boolean isError) {
        instructionLabel.setText(message);
        if (isError) {
            instructionLabel.setForeground(Color.RED);
        } else {
            instructionLabel.setForeground(Color.BLACK); // or new Color(0, 100, 0) for dark green
        }
    }

    // shake animaion
    public void shakeWindow() {
        Point original = frame.getLocation();
        int intensity = 5; // Pixels to move
        
        try {
            for (int i = 0; i < 6; i++) {
                // Move Right
                frame.setLocation(new Point(original.x + intensity, original.y));
                Thread.sleep(40);
                
                // Move Left
                frame.setLocation(new Point(original.x - intensity, original.y));
                Thread.sleep(40);
            }
            // Restore original position
            frame.setLocation(original);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}