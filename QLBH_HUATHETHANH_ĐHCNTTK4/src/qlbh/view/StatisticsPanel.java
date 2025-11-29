package qlbh.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import qlbh.model.*;
import qlbh.controller.StatisticsController;
import qlbh.controller.InvoiceController;
import qlbh.controller.CustomerController;
import java.net.URL;

public class StatisticsPanel extends JPanel {
    private JComboBox<String> cbLoaiThongKe, cbThang, cbQuy, cbNam;
    private JLabel lblTongDoanhThu, lblTongHoaDon, lblKhachHangThanThiet;
    private JPanel chartPanel;
    
    private StatisticsController statisticsController;
    private InvoiceController invoiceController;
    private CustomerController customerController;

    public StatisticsPanel(InvoiceManagementPanel invoicePanel, CustomerManagementPanel customerPanel) {
        this.invoiceController = invoicePanel.getInvoiceController();
        this.customerController = customerPanel.getCustomerController();
        this.statisticsController = new StatisticsController(invoiceController, customerController);
        initComponents();
    }

    private void initComponents() {
        // === KHỐI CODE HEADER (LOGO & CHỦ ĐỀ) ===
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(new Color(230, 240, 250)); 

        // Logo (WEST)
        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(80, 80)); 
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setVerticalAlignment(SwingConstants.CENTER);

        try {
            URL imageUrl = getClass().getResource("/qlbh/logo.png"); 
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image image = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(image));
            } else {
                lblLogo.setText("[LOGO - Lỗi]");
                lblLogo.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
                lblLogo.setFont(new Font("Arial", Font.PLAIN, 12));
            }
        } catch (Exception e) {
            lblLogo.setText("[LOGO - Lỗi]");
            lblLogo.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
            lblLogo.setFont(new Font("Arial", Font.PLAIN, 12));
            e.printStackTrace();
        }

        // Tiêu đề (CENTER)
        JLabel lblChuDe = new JLabel("HỆ THỐNG QUẢN LÝ BÁN HÀNG");
        lblChuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblChuDe.setForeground(new Color(0, 102, 204));
        lblChuDe.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(lblLogo, BorderLayout.WEST);
        headerPanel.add(lblChuDe, BorderLayout.CENTER);
        // === KẾT THÚC KHỐI CODE HEADER ===
        
        // Bắt đầu code UI gốc
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === PANEL LỰA CHỌN THỐNG KÊ ===
        JPanel controlPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Lựa chọn thống kê"));

        controlPanel.add(new JLabel("Loại thống kê:"));
        String[] loaiThongKe = {"Theo tháng", "Theo quý", "Theo năm"};
        cbLoaiThongKe = new JComboBox<>(loaiThongKe);
        controlPanel.add(cbLoaiThongKe);

        controlPanel.add(new JLabel("Tháng:"));
        String[] thang = {"Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        cbThang = new JComboBox<>(thang);
        controlPanel.add(cbThang);

        controlPanel.add(new JLabel("Quý:"));
        String[] quy = {"Tất cả", "1", "2", "3", "4"};
        cbQuy = new JComboBox<>(quy);
        controlPanel.add(cbQuy);

        controlPanel.add(new JLabel("Năm:"));
        String[] nam = {"2024", "2025", "2026"};
        cbNam = new JComboBox<>(nam);
        cbNam.setSelectedItem("2025");
        controlPanel.add(cbNam);

        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setBackground(new Color(0, 102, 204));
        btnThongKe.setForeground(Color.WHITE);
        controlPanel.add(btnThongKe);

        // === PANEL CHỈ SỐ THỐNG KÊ ===
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Chỉ số thống kê"));

        lblTongDoanhThu = createStatLabel("Tổng doanh thu: 0 VND");
        lblTongHoaDon = createStatLabel("Tổng số hóa đơn: 0");
        lblKhachHangThanThiet = createStatLabel("Khách hàng thân thiết: Chưa có dữ liệu");

        statsPanel.add(lblTongDoanhThu);
        statsPanel.add(lblTongHoaDon);
        statsPanel.add(lblKhachHangThanThiet);

        // === GOM CONTROL VÀ STATS ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.SOUTH);

        // === PANEL BIỂU ĐỒ ===
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChart(g);
            }
        };
        chartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ doanh thu"));
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setBackground(Color.WHITE);

        // BẮT ĐẦU SẮP XẾP VỚI HEADER MỚI
        JPanel contentWrapper = new JPanel(new BorderLayout(10, 10));
        contentWrapper.add(topPanel, BorderLayout.NORTH);
        contentWrapper.add(chartPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH); 
        add(contentWrapper, BorderLayout.CENTER); 

        // === XỬ LÝ SỰ KIỆN ===
        btnThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thucHienThongKe();
            }
        });

        // Mặc định thực hiện thống kê khi khởi động
        SwingUtilities.invokeLater(() -> thucHienThongKe());
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setOpaque(true);
        label.setBackground(new Color(240, 240, 240));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public void thucHienThongKe() {
        String loaiThongKe = (String) cbLoaiThongKe.getSelectedItem();
        String namStr = (String) cbNam.getSelectedItem();
        int nam = Integer.parseInt(namStr);

        // Tính toán thống kê
        Date startDate = getStartOfYear(nam);
        Date endDate = getEndOfYear(nam);
        
        double tongDoanhThu = statisticsController.getTotalRevenue(startDate, endDate);
        int tongHoaDon = statisticsController.getTotalInvoices(startDate, endDate);
        Customer khachHangThanThiet = statisticsController.getMostValuableCustomer(nam);

        // Cập nhật giao diện
        lblTongDoanhThu.setText(String.format("Tổng doanh thu: %,d VND", (long) tongDoanhThu));
        lblTongHoaDon.setText(String.format("Tổng số hóa đơn: %d", tongHoaDon));
        
        if (khachHangThanThiet != null) {
            double doanhThuKhachHang = tinhDoanhThuKhachHang(khachHangThanThiet.getMaKH(), nam);
            lblKhachHangThanThiet.setText(String.format("Khách hàng thân thiết: %s (%,.0f VND)", 
                khachHangThanThiet.getHoTen(), doanhThuKhachHang));
        } else {
            lblKhachHangThanThiet.setText("Khách hàng thân thiết: Chưa có dữ liệu");
        }

        // Vẽ biểu đồ
        chartPanel.repaint();
    }

    private double tinhDoanhThuKhachHang(String maKH, int nam) {
        Date startDate = getStartOfYear(nam);
        Date endDate = getEndOfYear(nam);
        
        return invoiceController.getInvoicesByCustomer(maKH).stream()
                .filter(invoice -> !invoice.getNgayBan().before(startDate) && !invoice.getNgayBan().after(endDate))
                .mapToDouble(Invoice::getTongTien)
                .sum();
    }

    private void drawChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = chartPanel.getWidth();
        int height = chartPanel.getHeight();
        int padding = 50;

        if (width <= 2 * padding || height <= 2 * padding) return;

        // Vẽ trục
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, height - padding, width - padding, height - padding);
        g2d.drawLine(padding, padding, padding, height - padding);

        // Lấy dữ liệu thực tế cho biểu đồ
        String loaiThongKe = (String) cbLoaiThongKe.getSelectedItem();
        String namStr = (String) cbNam.getSelectedItem();
        int nam = Integer.parseInt(namStr);
        
        Map<String, Double> duLieuBieuDo = statisticsController.getRevenueStatistics(loaiThongKe, nam, "");

        if (duLieuBieuDo.isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.drawString("Không có dữ liệu để hiển thị", width/2 - 60, height/2);
            return;
        }

        String[] labels = duLieuBieuDo.keySet().toArray(new String[0]);
        double[] values = new double[labels.length];
        
        int index = 0;
        double maxValue = 0;
        for (Double value : duLieuBieuDo.values()) {
            values[index] = value;
            if (value > maxValue) maxValue = value;
            index++;
        }

        // Tính toán tỷ lệ
        double scaleY = (height - 2 * padding - 30) / (maxValue > 0 ? maxValue : 1);
        int barWidth = Math.max(20, (width - 2 * padding) / labels.length - 10);
        int baseY = height - padding;

        // Vẽ các cột
        for (int i = 0; i < labels.length; i++) {
            int barHeight = (int) (values[i] * scaleY);
            int x = padding + i * (barWidth + 10) + 5;
            int y = baseY - barHeight;

            // Vẽ cột
            Color barColor = new Color(70, 130, 180, 200);
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);

            // Vẽ viền
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);

            // Hiển thị giá trị
            g2d.setColor(Color.BLACK);
            String valueText = String.format("%,.0f", values[i]);
            int textWidth = g2d.getFontMetrics().stringWidth(valueText);
            g2d.drawString(valueText, x + barWidth/2 - textWidth/2, y - 5);

            // Hiển thị nhãn
            int labelWidth = g2d.getFontMetrics().stringWidth(labels[i]);
            g2d.drawString(labels[i], x + barWidth/2 - labelWidth/2, baseY + 15);
        }

        // Tiêu đề biểu đồ
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String title = String.format("BIỂU ĐỒ DOANH THU NĂM %s", namStr);
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, width/2 - titleWidth/2, 30);
    }

    private Date getStartOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        return cal.getTime();
    }
    
    private Date getEndOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return cal.getTime();
    }
}