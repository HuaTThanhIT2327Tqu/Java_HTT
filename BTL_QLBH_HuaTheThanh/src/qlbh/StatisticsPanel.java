package qlbh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class StatisticsPanel extends JPanel {
    private JComboBox<String> cbLoaiThongKe, cbThang, cbQuy, cbNam;
    private JLabel lblTongDoanhThu, lblTongHoaDon, lblKhachHangThanThiet;
    private JPanel chartPanel;
    
    // Tham chiếu đến danh sách hóa đơn thực tế
    private InvoiceManagementPanel invoicePanel;
    private CustomerManagementPanel customerPanel;

    public StatisticsPanel(InvoiceManagementPanel invoicePanel, CustomerManagementPanel customerPanel) {
        this.invoicePanel = invoicePanel;
        this.customerPanel = customerPanel;
        initComponents();
    }

    private void initComponents() {
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

        // === SẮP XẾP LAYOUT ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

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
        String thang = (String) cbThang.getSelectedItem();
        String quy = (String) cbQuy.getSelectedItem();
        String nam = (String) cbNam.getSelectedItem();

        // Lấy danh sách hóa đơn thực tế từ InvoiceManagementPanel
        java.util.List<Invoice> danhSachHoaDon = invoicePanel.getDanhSachHoaDon();
        java.util.List<Customer> danhSachKhachHang = customerPanel.getCustomerList();

        // Lọc hóa đơn theo điều kiện
        java.util.List<Invoice> hoaDonLoc = locHoaDon(danhSachHoaDon, loaiThongKe, thang, quy, nam);

        // Tính toán thống kê
        double tongDoanhThu = tinhTongDoanhThu(hoaDonLoc);
        int tongHoaDon = hoaDonLoc.size();
        String khachHangThanThiet = timKhachHangThanThiet(hoaDonLoc, danhSachKhachHang);

        // Cập nhật giao diện
        lblTongDoanhThu.setText(String.format("Tổng doanh thu: %,d VND", (long) tongDoanhThu));
        lblTongHoaDon.setText(String.format("Tổng số hóa đơn: %d", tongHoaDon));
        lblKhachHangThanThiet.setText(String.format("Khách hàng thân thiết: %s", khachHangThanThiet));

        // Vẽ biểu đồ
        chartPanel.repaint();
    }

    private java.util.List<Invoice> locHoaDon(java.util.List<Invoice> danhSachHoaDon, String loaiThongKe, String thang, String quy, String nam) {
        java.util.List<Invoice> ketQua = new ArrayList<>();
        int targetYear = Integer.parseInt(nam);

        for (Invoice hd : danhSachHoaDon) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hd.getNgayBan());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;

            if (year != targetYear) continue;

            switch (loaiThongKe) {
                case "Theo tháng":
                    if (thang.equals("Tất cả") || Integer.parseInt(thang) == month) {
                        ketQua.add(hd);
                    }
                    break;
                case "Theo quý":
                    int quarter = (month - 1) / 3 + 1;
                    if (quy.equals("Tất cả") || Integer.parseInt(quy) == quarter) {
                        ketQua.add(hd);
                    }
                    break;
                case "Theo năm":
                    ketQua.add(hd);
                    break;
            }
        }
        return ketQua;
    }

    private double tinhTongDoanhThu(java.util.List<Invoice> hoaDon) {
        double tong = 0;
        for (Invoice hd : hoaDon) {
            tong += hd.getTongTien();
        }
        return tong;
    }

    private String timKhachHangThanThiet(java.util.List<Invoice> hoaDon, java.util.List<Customer> danhSachKhachHang) {
        if (hoaDon.isEmpty()) return "Chưa có dữ liệu";

        Map<String, Double> demDoanhThu = new HashMap<>();

        for (Invoice hd : hoaDon) {
            String maKH = hd.getMaKH();
            demDoanhThu.put(maKH, demDoanhThu.getOrDefault(maKH, 0.0) + hd.getTongTien());
        }

        // Tìm khách hàng có doanh thu cao nhất
        String khachHangDoanhThuCaoNhat = "";
        double maxDoanhThu = 0;
        for (Map.Entry<String, Double> entry : demDoanhThu.entrySet()) {
            if (entry.getValue() > maxDoanhThu) {
                maxDoanhThu = entry.getValue();
                khachHangDoanhThuCaoNhat = entry.getKey();
            }
        }

        // Tìm tên khách hàng
        String tenKH = timTenKhachHang(khachHangDoanhThuCaoNhat, danhSachKhachHang);
        return String.format("%s (%,.0f VND)", tenKH, maxDoanhThu);
    }

    private String timTenKhachHang(String maKH, java.util.List<Customer> danhSachKhachHang) {
        for (Customer kh : danhSachKhachHang) {
            if (kh.getMaKH().equals(maKH)) {
                return kh.getHoTen();
            }
        }
        return maKH;
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
        String nam = (String) cbNam.getSelectedItem();
        Map<String, Double> duLieuBieuDo = layDuLieuBieuDoThucTe(loaiThongKe, nam);

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
        int barWidth = (width - 2 * padding) / labels.length - 10;
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
            g2d.drawString(valueText, x + barWidth/2 - 15, y - 5);

            // Hiển thị nhãn
            g2d.drawString(labels[i], x + barWidth/2 - 10, baseY + 15);
        }

        // Tiêu đề biểu đồ
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String title = String.format("BIỂU ĐỒ DOANH THU NĂM %s", nam);
        g2d.drawString(title, width/2 - 100, 30);
    }

    private Map<String, Double> layDuLieuBieuDoThucTe(String loaiThongKe, String nam) {
        Map<String, Double> duLieu = new LinkedHashMap<>();
        int targetYear = Integer.parseInt(nam);

        // Lấy danh sách hóa đơn thực tế
        java.util.List<Invoice> danhSachHoaDon = invoicePanel.getDanhSachHoaDon();

        switch (loaiThongKe) {
            case "Theo tháng":
                for (int i = 1; i <= 12; i++) {
                    duLieu.put("Tháng " + i, 0.0);
                }
                break;
            case "Theo quý":
                duLieu.put("Quý 1", 0.0);
                duLieu.put("Quý 2", 0.0);
                duLieu.put("Quý 3", 0.0);
                duLieu.put("Quý 4", 0.0);
                break;
            case "Theo năm":
                duLieu.put("Năm " + nam, 0.0);
                break;
        }

        // Tính toán doanh thu thực tế từ hóa đơn
        for (Invoice hd : danhSachHoaDon) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hd.getNgayBan());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;

            if (year != targetYear) continue;

            switch (loaiThongKe) {
                case "Theo tháng":
                    String keyThang = "Tháng " + month;
                    duLieu.put(keyThang, duLieu.get(keyThang) + hd.getTongTien());
                    break;
                case "Theo quý":
                    int quarter = (month - 1) / 3 + 1;
                    String keyQuy = "Quý " + quarter;
                    duLieu.put(keyQuy, duLieu.get(keyQuy) + hd.getTongTien());
                    break;
                case "Theo năm":
                    String keyNam = "Năm " + nam;
                    duLieu.put(keyNam, duLieu.get(keyNam) + hd.getTongTien());
                    break;
            }
        }

        return duLieu;
    }
}