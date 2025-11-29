package qlbh.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import qlbh.controller.AuthController;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private AuthController authController;
    
    public LoginForm() {
        authController = new AuthController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Đăng nhập - Hệ thống Quản lý Bán hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JLabel lblHeader = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeader.setForeground(new Color(0, 102, 204));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(lblUser, gbc);
        
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(txtUsername, gbc);
        
        // Password
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(lblPass, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(txtPassword, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setFocusPainted(false);
        
        JButton btnExit = new JButton("Thoát");
        btnExit.setBackground(new Color(204, 0, 0));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 12));
        btnExit.setPreferredSize(new Dimension(120, 35));
        btnExit.setFocusPainted(false);
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);
        
        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(new Color(240, 240, 240));
        JLabel lblInfo = new JLabel("Tài khoản: admin | Mật khẩu: 123456");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        infoPanel.add(lblInfo);
        
        // Add components to main panel
        mainPanel.add(lblHeader, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(infoPanel, BorderLayout.AFTER_LAST_LINE);
        
        add(mainPanel);
        
        // Event handlers
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    LoginForm.this, 
                    "Bạn có chắc muốn thoát chương trình?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Enter key to login
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        // Focus on username field when window opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                txtUsername.requestFocus();
            }
        });
    }
    
    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", 
                "Lỗi đăng nhập", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (authController.authenticate(username, password)) {
            showFullScreenWelcome(username);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Sai tên đăng nhập hoặc mật khẩu!\n\nVui lòng thử lại.", 
                "Đăng nhập thất bại", 
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
    
    private void showFullScreenWelcome(String username) {
        JFrame welcomeFrame = new JFrame("Chào mừng - Hệ thống Quản lý Bán hàng");
        welcomeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        welcomeFrame.setUndecorated(true);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(70, 130, 180),
                    getWidth(), getHeight(), new Color(30, 80, 150)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
        JLabel iconLabel = new JLabel("🎉");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP THÀNH CÔNG!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("Xin chào " + username + "!");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subMessageLabel = new JLabel("Chào mừng bạn đến với Hệ thống Quản lý Bán hàng");
        subMessageLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        subMessageLabel.setForeground(new Color(200, 230, 255));
        subMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Phần mềm được phát triển bởi Hứa Thế Thành - ĐH CNTT K4");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        infoLabel.setForeground(new Color(180, 220, 255));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(255, 255, 255, 150));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        separator.setMaximumSize(new Dimension(600, 5));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnContinue = new JButton("BẮT ĐẦU SỬ DỤNG HỆ THỐNG");
        btnContinue.setFont(new Font("Arial", Font.BOLD, 24));
        btnContinue.setBackground(new Color(255, 215, 0));
        btnContinue.setForeground(Color.BLACK);
        btnContinue.setFocusPainted(false);
        btnContinue.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnContinue.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnContinue.setBackground(new Color(255, 230, 0));
                btnContinue.setForeground(new Color(50, 50, 50));
            }
            
            public void mouseExited(MouseEvent e) {
                btnContinue.setBackground(new Color(255, 215, 0));
                btnContinue.setForeground(Color.BLACK);
            }
        });
        
        btnContinue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                openMainApplication();
            }
        });
        
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(subMessageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(infoLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        buttonPanel.add(btnContinue);
        contentPanel.add(buttonPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        welcomeFrame.add(mainPanel);
        welcomeFrame.setVisible(true);
        
        // Auto close after 5 seconds
        Timer autoCloseTimer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (welcomeFrame.isVisible()) {
                    welcomeFrame.dispose();
                    openMainApplication();
                }
            }
        });
        autoCloseTimer.setRepeats(false);
        autoCloseTimer.start();
        
        // Close on any key press or mouse click
        welcomeFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                welcomeFrame.dispose();
                openMainApplication();
            }
        });
        
        welcomeFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                welcomeFrame.dispose();
                openMainApplication();
            }
        });
        
        welcomeFrame.setFocusable(true);
        welcomeFrame.requestFocus();
    }
    
    private void openMainApplication() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}