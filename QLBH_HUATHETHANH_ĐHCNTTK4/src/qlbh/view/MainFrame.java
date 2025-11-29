package qlbh.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import qlbh.model.*; 
import qlbh.controller.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private ProductManagementPanel productPanel;
    private CustomerManagementPanel customerPanel;
    private InvoiceManagementPanel invoicePanel;
    private StatisticsPanel statisticsPanel;
    private AboutPanel aboutPanel;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Phần mềm Quản lý Bán hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Khởi tạo các panel
        productPanel = new ProductManagementPanel();
        customerPanel = new CustomerManagementPanel();
        invoicePanel = new InvoiceManagementPanel(customerPanel); 
        statisticsPanel = new StatisticsPanel(invoicePanel, customerPanel);
        aboutPanel = new AboutPanel();

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnSystem = new JMenu("Hệ thống");
        menuBar.add(mnSystem);
        
        JMenuItem mntmExit = new JMenuItem("Thoát");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this, 
                    "Bạn có chắc muốn thoát chương trình?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        mnSystem.add(mntmExit);
        
        JMenu mnManagement = new JMenu("Quản lý");
        menuBar.add(mnManagement);
        
        JMenuItem mntmProduct = new JMenuItem("Quản lý Sản phẩm");
        mntmProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProductPanel();
            }
        });
        mnManagement.add(mntmProduct);
        
        JMenuItem mntmCustomer = new JMenuItem("Quản lý Khách hàng");
        mntmCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCustomerPanel();
            }
        });
        mnManagement.add(mntmCustomer);
        
        JMenuItem mntmInvoice = new JMenuItem("Quản lý Hóa đơn");
        mntmInvoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInvoicePanel();
            }
        });
        mnManagement.add(mntmInvoice);
        
        JMenu mnStatistics = new JMenu("Thống kê");
        menuBar.add(mnStatistics);
        
        JMenuItem mntmStats = new JMenuItem("Thống kê Doanh thu");
        mntmStats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStatisticsPanel();
            }
        });
        mnStatistics.add(mntmStats);
        
        JMenu mnHelp = new JMenu("Trợ giúp");
        menuBar.add(mnHelp);
        
        JMenuItem mntmAbout = new JMenuItem("Giới thiệu");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutPanel();
            }
        });
        mnHelp.add(mntmAbout);
        
        // Content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        // Add panels to card layout
        contentPanel.add(productPanel, "Product");
        contentPanel.add(customerPanel, "Customer");
        contentPanel.add(invoicePanel, "Invoice");
        contentPanel.add(statisticsPanel, "Statistics");
        contentPanel.add(aboutPanel, "About");
        
        // Show default panel
        showProductPanel();
        
        // Add window listener to confirm exit
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this, 
                    "Bạn có chắc muốn thoát chương trình?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }
    
    private void showProductPanel() {
        cardLayout.show(contentPanel, "Product");
        setTitle("Phần mềm Quản lý Bán hàng - Quản lý Sản phẩm");
    }
    
    private void showCustomerPanel() {
        cardLayout.show(contentPanel, "Customer");
        setTitle("Phần mềm Quản lý Bán hàng - Quản lý Khách hàng");
    }
    
    private void showInvoicePanel() {
        cardLayout.show(contentPanel, "Invoice");
        setTitle("Phần mềm Quản lý Bán hàng - Quản lý Hóa đơn");
    }
    
    private void showStatisticsPanel() {
        // Cập nhật lại thống kê khi chuyển sang tab
        statisticsPanel.thucHienThongKe();
        cardLayout.show(contentPanel, "Statistics");
        setTitle("Phần mềm Quản lý Bán hàng - Thống kê Doanh thu");
    }
    
    private void showAboutPanel() {
        cardLayout.show(contentPanel, "About");
        setTitle("Phần mềm Quản lý Bán hàng - Giới thiệu");
    }
}