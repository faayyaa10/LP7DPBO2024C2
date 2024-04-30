import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    //image attributes
    private Image backgroundImage;
    private Image birdImage;
    private Image lowerPipeImage;
    private Image upperPipeImage;

    //player attributes
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    //pipe attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    private ArrayList<Pipe> pipes;

    //game logic
    Timer gameloop;
    Timer pipesCooldown;
    int gravity = 1;

    // score
    private int score = 0;
    private JLabel scoreLabel;

    private JButton exitButton;

    //constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.start();

        gameloop = new Timer(1000 / 60, this);
        gameloop.start();

        // Initialize score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBounds(10, 10, 100, 30);
        add(scoreLabel);

        // Tombol Keluar
        exitButton = new JButton("Keluar");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menampilkan dialog konfirmasi sebelum keluar
                int choice = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar?", "Keluar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0); // Keluar dari permainan
                }
            }
        });
        exitButton.setBounds(130, 300, 100, 40);
        exitButton.setVisible(false); // Tidak terlihat saat pertama kali

        add(exitButton); // Menambahkan tombol keluar ke panel utama
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    Timer delayTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mulai kembali timer game loop dan timer untuk menambahkan pipa baru setelah delay
            gameloop.start();
            pipesCooldown.start();
        }
    });

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        for (Pipe pipe : pipes) {
            if (!pipe.isPassed()) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }
    }

    public void gameOver() {
        gameloop.stop(); // Menghentikan timer game loop
        pipesCooldown.stop(); // Menghentikan timerM untuk menambahkan pipa baru

        // Tampilkan pesan "Game Over" menggunakan dialog box
        String message = "Game Over\n\nYour Score: " + score + "\n\nDo you want to play again?";
        int choice = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            // Jika pemain memilih untuk bermain lagi, restart permainan
            restartGame();
        } else {
            // Jika pemain memilih untuk keluar dari permainan, keluar dari program
            System.exit(0);
        }
    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        boolean gameOverFlag = false; // Flag untuk menandakan apakah game over

        // Deteksi tabrakan dengan pipa
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            // Deteksi tabrakan dengan pipa atas
            if (player.getPosX() + player.getWidth() > pipe.getPosX() && player.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                    player.getPosY() + player.getHeight() > pipe.getPosY() && player.getPosY() < pipe.getPosY() + pipe.getHeight()) {
                gameOverFlag = true; // Set flag menjadi true
            }
            // Deteksi tabrakan dengan pipa bawah
            if (player.getPosX() + player.getWidth() > pipe.getPosX() && player.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                    player.getPosY() + player.getHeight() > pipe.getPosY() + pipe.getHeight() + frameHeight / 4 &&
                    player.getPosY() < pipe.getPosY() + pipe.getHeight() + frameHeight / 4 + pipeHeight) {
                gameOverFlag = true; // Set flag menjadi true
            }
            // Bagian yang ditambahkan untuk menambah skor saat melewati pipa
            // Deteksi jika player berhasil melewati pipa, tambahkan skor dan hapus pipa dari daftar
            if (player.getPosX() == pipe.getPosX() + pipe.getWidth() && !pipe.isPassed()) {
                score++;
                updateScoreLabel();
                pipe.setPassed(true);
            }
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        // Deteksi jika player jatuh ke bawah JFrame
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver();
        }

        // Jika flag gameOverFlag adalah true, panggil method gameOver()
        if (gameOverFlag) {
            gameOver();
        }

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            // Deteksi jika player melewati sepasang pipa
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                if (i % 2 == 0) { // Upper pipe
                    Pipe lowerPipe = pipes.get(i + 1);
                    // Periksa apakah player berada di antara upper dan lower pipe
                    if (player.getPosY() + player.getHeight() / 2 >= pipe.getPosY() + pipe.getHeight() &&
                            player.getPosY() + player.getHeight() / 2 <= lowerPipe.getPosY()) {
                        score++;
                        updateScoreLabel();
                        pipe.setPassed(true);
                        lowerPipe.setPassed(true);
                        break; // Keluar dari loop setelah menambahkan skor
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void restartGame() {
        // Hentikan timer game loop dan timer untuk menambahkan pipa baru
        gameloop.stop();
        pipesCooldown.stop();

        // Reset posisi player
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);

        // Hapus semua pipa yang ada
        pipes.clear();

        // Tambahkan delay sebelum player mulai jatuh
        Timer delayTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mulai kembali timer game loop dan timer untuk menambahkan pipa baru setelah delay
                gameloop.start();
                pipesCooldown.start();
            }
        });
        delayTimer.setRepeats(false); // Set timer agar hanya berjalan sekali
        delayTimer.start();

        // Reset skor
        score = 0;
        updateScoreLabel();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }
        // Tombol R untuk restart permainan
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage, 0, false);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage, 0, false);
        pipes.add(lowerPipe);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
        scoreLabel.setForeground(Color.YELLOW); // Mengubah warna teks skor menjadi kuning
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Mengubah ukuran dan jenis font teks skor
        // Animasi sederhana untuk mengubah ukuran teks skor
        Timer sizeTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Kembalikan ukuran font ke nilai semula
                scoreLabel.setForeground(Color.WHITE); // Kembalikan warna font ke warna semula
            }
        });
        sizeTimer.setRepeats(false); // Set timer agar hanya berjalan sekali
        sizeTimer.start(); // Mulai timer untuk menjalankan animasi
    }

}
