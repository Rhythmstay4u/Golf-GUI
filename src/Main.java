import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private int ballX, ballY; // Ball coordinates
    private int holeX, holeY; // Hole coordinates
    private int strokes;     // Number of strokes
    private int score;       // Player's score
    private boolean gameFinished;

    private List<Level> levels;
    private int currentLevel;

    public Main() {
        setTitle("Golf Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        levels = new ArrayList<>();
        levels.add(new Level(50, 300, 700, 300));
        levels.add(new Level(100, 100, 600, 500));
        currentLevel = 0;

        loadLevel(currentLevel);

        // Create a panel for drawing
        GolfGamePanel gamePanel = new GolfGamePanel();
        add(gamePanel);

        // Create a button to hit the ball
        JButton hitButton = new JButton("Hit the ball");
        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameFinished) {
                    hitBall();
                    gamePanel.repaint();
                }
            }
        });

        // Create a button to reset the game
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                gamePanel.repaint();
            }
        });

        // Create a label to display the number of strokes and score
        JLabel strokesLabel = new JLabel("Strokes: " + strokes);
        JLabel scoreLabel = new JLabel("Score: " + score);

        // Create a panel for buttons and labels
        JPanel controlPanel = new JPanel();
        controlPanel.add(hitButton);
        controlPanel.add(resetButton);
        controlPanel.add(strokesLabel);
        controlPanel.add(scoreLabel);

        // Add the control panel to the frame
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadLevel(int levelIndex) {
        Level level = levels.get(levelIndex);
        ballX = level.ballX;
        ballY = level.ballY;
        holeX = level.holeX;
        holeY = level.holeY;
        strokes = 0;
        gameFinished = false;
    }

    private void hitBall() {
        // Simulate hitting the ball
        int dx = (int) (Math.random() * 21) - 10; // Random horizontal distance
        int dy = (int) (Math.random() * 21) - 10; // Random vertical distance

        ballX += dx;
        ballY += dy;
        strokes++;

        // Check if the ball reaches the hole
        if (Math.abs(ballX - holeX) < 20 && Math.abs(ballY - holeY) < 20) {
            gameFinished = true;
            score += 10;
            JOptionPane.showMessageDialog(this, "Hole in one! Strokes: " + strokes + " Score: " + score);

            if (currentLevel < levels.size() - 1) {
                currentLevel++;
                loadLevel(currentLevel);
            } else {
                JOptionPane.showMessageDialog(this, "Congratulations! You completed all levels. Final Score: " + score);
                resetGame();
            }
        }
    }

    private void resetGame() {
        currentLevel = 0;
        loadLevel(currentLevel);
        score = 0;
    }

    class GolfGamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.BLUE);
            g.fillOval(ballX, ballY, 20, 20);

            g.setColor(Color.RED);
            g.fillOval(holeX, holeY, 20, 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

    class Level {
        int ballX, ballY;
        int holeX, holeY;

        Level(int ballX, int ballY, int holeX, int holeY) {
            this.ballX = ballX;
            this.ballY = ballY;
            this.holeX = holeX;
            this.holeY = holeY;
        }
    }
}
