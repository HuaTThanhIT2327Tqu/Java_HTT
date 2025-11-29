package qlbh.view;

import qlbh.model.*; 
import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {
    public AboutPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Tiêu đề
        JLabel lblTitle = new JLabel("PHẦN MỀM QUẢN LÝ BÁN HÀNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thông tin
        JLabel lblSchool = new JLabel("Trường Đại học Tân Trào");
        lblSchool.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSchool.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAuthor = new JLabel("Sinh viên thực hiện: Hứa Thế Thành - ĐH CNTT K4");
        lblAuthor.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblYear = new JLabel("Năm học: 2025–2026");
        lblYear.setFont(new Font("Arial", Font.PLAIN, 16));
        lblYear.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tính năng chính
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBorder(BorderFactory.createTitledBorder("Các tính năng chính"));
        featuresPanel.setBackground(Color.WHITE);
        featuresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] features = {
            "✓ Quản lý sản phẩm (Thêm, Sửa, Xóa, Tìm kiếm)",
            "✓ Quản lý khách hàng",
            "✓ Quản lý hóa đơn bán hàng",
            "✓ Tính tiền và thanh toán",
            "✓ Thống kê doanh thu",
            "✓ Biểu đồ trực quan"
        };

        for (String feature : features) {
            JLabel lblFeature = new JLabel(feature);
            lblFeature.setFont(new Font("Arial", Font.PLAIN, 14));
            lblFeature.setAlignmentX(Component.LEFT_ALIGNMENT);
            featuresPanel.add(lblFeature);
        }

        // Khoảng cách
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(lblSchool);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(lblAuthor);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(lblYear);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(featuresPanel);

        add(contentPanel, BorderLayout.CENTER);
    }
}