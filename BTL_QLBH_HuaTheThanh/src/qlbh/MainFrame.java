package qlbh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private ProductManagementPanel productPanel;
    private CustomerManagementPanel customerPanel;
    private InvoiceManagementPanel invoicePanel;
    private StatisticsPanel statisticsPanel;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Phần mềm Quản lý Bán hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        // Khởi tạo các panel
        productPanel = new ProductManagementPanel();
        customerPanel = new CustomerManagementPanel();
        invoicePanel = new InvoiceManagementPanel();
        statisticsPanel = new StatisticsPanel(invoicePanel, customerPanel); // Truyền tham chiếu

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnSystem = new JMenu("Hệ thống");
        menuBar.add(mnSystem);
        
        JMenuItem mntmExit = new JMenuItem("Thoát");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnSystem.add(mntmExit);
        
        JMenu mnManagement = new JMenu("Quản lý");
        menuBar.add(mnManagement);
        
        JMenuItem mntmProduct = new JMenuItem("Sản phẩm");
        mntmProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProductPanel();
            }
        });
        mnManagement.add(mntmProduct);
        
        JMenuItem mntmCustomer = new JMenuItem("Khách hàng");
        mntmCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCustomerPanel();
            }
        });
        mnManagement.add(mntmCustomer);
        
        JMenuItem mntmInvoice = new JMenuItem("Hóa đơn");
        mntmInvoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInvoicePanel();
            }
        });
        mnManagement.add(mntmInvoice);
        
        JMenu mnStatistics = new JMenu("Thống kê");
        menuBar.add(mnStatistics);
        
        JMenuItem mntmStats = new JMenuItem("Doanh thu");
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
        contentPanel.add(new AboutPanel(), "About");
        
        // Show default panel
        showProductPanel();
    }
    
    private void showProductPanel() {
        cardLayout.show(contentPanel, "Product");
    }
    
    private void showCustomerPanel() {
        cardLayout.show(contentPanel, "Customer");
    }
    
    private void showInvoicePanel() {
        cardLayout.show(contentPanel, "Invoice");
    }
    
    private void showStatisticsPanel() {
        // Cập nhật lại thống kê khi chuyển sang tab
        statisticsPanel.thucHienThongKe();
        cardLayout.show(contentPanel, "Statistics");
    }
    
    private void showAboutPanel() {
        cardLayout.show(contentPanel, "About");
    }
}