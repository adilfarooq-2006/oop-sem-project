import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameWindow {
    private JFrame frame;
    private JButton[][] boardButtons;
    private Character[][] boardData;
    
    // UI Components
    private JLabel turnLabel;        // Shows "Turn: Barbarian"
    private JLabel instructionLabel; // Shows "Click MOVE to start..."
    private JLabel p1StatsLabel;
    private JLabel p2StatsLabel;
    
    // --- FIX 1: VOLATILE KEYWORD ---
    // This ensures the Main Thread sees changes made by the Button Clicks immediately
    private volatile Point lastClick = null; 
    private volatile String lastAction = null; 

    private int rows = 8;
    private int cols = 8;

    public GameWindow(Character[][] boardData) {
        this.boardData = boardData;
        this.boardButtons = new JButton[rows][cols];
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Clash Tactics");
        frame.setSize(800, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 1. TOP PANEL (Stats)
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setPreferredSize(new Dimension(800, 50));
        
        p1StatsLabel = createStatLabel("P1: 100 HP");
        p2StatsLabel = createStatLabel("P2: 100 HP");
        
        topPanel.add(p1StatsLabel);
        topPanel.add(p2StatsLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // 2. CENTER PANEL (Grid)
        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton btn = new JButton();
                btn.setFocusable(false);
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                
                if ((i + j) % 2 == 0) btn.setBackground(Color.LIGHT_GRAY);
                else btn.setBackground(Color.WHITE);

                final int r = i;
                final int c = j;
                
                btn.addActionListener(e -> {
                    lastClick = new Point(c, r); // (x, y)
                });

                boardButtons[i][j] = btn;
                boardPanel.add(btn);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        // 3. BOTTOM PANEL (Controls + Info)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        // Info Section (Turn + Instructions)
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

        // Buttons Section
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton moveBtn = new JButton("MOVE");
        JButton attackBtn = new JButton("ATTACK");
        
        styleButton(moveBtn);
        styleButton(attackBtn);

        moveBtn.addActionListener(e -> {
            System.out.println("Move Button Clicked"); // Debug check
            lastAction = "MOVE";
        });
        
        attackBtn.addActionListener(e -> {
            System.out.println("Attack Button Clicked"); // Debug check
            lastAction = "ATTACK";
        });

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
        turnLabel.setText(turnInfo); // --- FIX 2: Updates the Big Turn Label
    }

    public void refresh() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Character c = boardData[i][j];
                if (c != null) {
                    boardButtons[i][j].setText(c.getName().substring(0, 1)); 
                    if (c.getPosition().getY() < 4) boardButtons[i][j].setForeground(Color.RED);
                    else boardButtons[i][j].setForeground(Color.BLUE);
                } else {
                    boardButtons[i][j].setText(""); 
                }
            }
        }
    }

    // Helper to style text
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
}