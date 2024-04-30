import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame {
    public MenuForm() {
        setTitle("Flappy Bird Menu");
        setSize(360, 640); // Menetapkan ukuran yang sama dengan tampilan permainan FlappyBird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel utama dengan layout null agar bisa menempatkan komponen secara bebas
        JPanel mainPanel = new JPanel(null);

        // Menambahkan latar belakang menggunakan JLabel
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("assets/background.png"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        mainPanel.add(backgroundLabel);

        // Menambahkan panel untuk tombol "Start Game" di atas latar belakang
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Membuat panel transparan agar latar belakang terlihat

        // Mengatur posisi tombol "Start Game" agar berada di tengah panel
        int buttonWidth = 150;
        int buttonHeight = 50;
        int buttonX = (360 - buttonWidth) / 2;
        int buttonY = (640 - buttonHeight) / 2;

        buttonPanel.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Membuat tombol "Start Game" dengan penyesuaian tampilan
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20)); // Mengatur ukuran dan jenis font teks
        startButton.setForeground(Color.WHITE); // Mengatur warna teks
        startButton.setBackground(Color.CYAN); // Mengatur warna latar belakang
        startButton.setFocusPainted(false); // Menghilangkan efek focus border saat tombol aktif
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Mengatur padding
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup JFrame menu
                dispose();
                // Memulai permainan FlappyBird
                startGame();
            }
        });

        // Memberikan efek hover saat kursor berada di atas tombol
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.CYAN); // Mengubah warna latar belakang saat hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.BLUE); // Mengembalikan warna latar belakang saat kursor keluar
            }
        });

        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel); // Menambahkan panel tombol ke panel utama

        // Menetapkan panel utama ke JFrame
        setContentPane(mainPanel);

        setVisible(true);
    }

    private void startGame() {
        // Membuat objek frame game FlappyBird
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Membuat objek JPanel FlappyBird
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Memulai dengan menampilkan menu form
        new MenuForm();
    }
}
