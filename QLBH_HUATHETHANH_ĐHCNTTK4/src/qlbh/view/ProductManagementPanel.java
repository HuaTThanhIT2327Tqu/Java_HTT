package qlbh.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import qlbh.model.*;
import qlbh.controller.ProductController;
import java.net.URL;

public class ProductManagementPanel extends JPanel {
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtLoaiSP;
    private JTextField txtGiaBan;
    private JTextField txtSoLuong;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProductController productController;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnXoaTrang;
    private DecimalFormat decimalFormat;

    public ProductManagementPanel() {
        productController = new ProductController();
        decimalFormat = new DecimalFormat("#,###");
        initialize();
        refreshTable();
    }

    private void initialize() {
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
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        panel.add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
        
        JLabel lblMaSP = new JLabel("Mã SP:");
        inputPanel.add(lblMaSP);
        
        txtMaSP = new JTextField();
        inputPanel.add(txtMaSP);
        txtMaSP.setColumns(10);
        
        JLabel lblTenSP = new JLabel("Tên SP:");
        inputPanel.add(lblTenSP);
        
        txtTenSP = new JTextField();
        inputPanel.add(txtTenSP);
        txtTenSP.setColumns(10);
        
        JLabel lblLoaiSP = new JLabel("Loại SP:");
        inputPanel.add(lblLoaiSP);
        
        txtLoaiSP = new JTextField();
        inputPanel.add(txtLoaiSP);
        txtLoaiSP.setColumns(10);
        
        JLabel lblGiaBan = new JLabel("Giá bán (VNĐ):");
        inputPanel.add(lblGiaBan);
        
        txtGiaBan = new JTextField();
        inputPanel.add(txtGiaBan);
        txtGiaBan.setColumns(10);
        
        JLabel lblSoLuong = new JLabel("Số lượng tồn:");
        inputPanel.add(lblSoLuong);
        
        txtSoLuong = new JTextField();
        inputPanel.add(txtSoLuong);
        txtSoLuong.setColumns(10);
        
        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themSanPham();
            }
        });
        buttonPanel.add(btnThem);
        
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaSanPham();
            }
        });
        buttonPanel.add(btnSua);
        
        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaSanPham();
            }
        });
        buttonPanel.add(btnXoa);
        
        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                luuSanPham();
            }
        });
        buttonPanel.add(btnLuu);
        
        btnXoaTrang = new JButton("Xóa trắng");
        btnXoaTrang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaTrang();
            }
        });
        buttonPanel.add(btnXoaTrang);
        
        JScrollPane scrollPane = new JScrollPane();
        
        String[] columns = {"Mã SP", "Tên SP", "Loại SP", "Giá bán", "Số lượng tồn"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);
        
        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtMaSP.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTenSP.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtLoaiSP.setText(tableModel.getValueAt(selectedRow, 2).toString());
                
                String giaBanStr = tableModel.getValueAt(selectedRow, 3).toString().replace(" VND", "").replace(",", "");
                txtGiaBan.setText(giaBanStr);
                
                txtSoLuong.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });
        
        // BẮT ĐẦU SẮP XẾP VỚI HEADER MỚI
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.add(panel, BorderLayout.NORTH);
        contentWrapper.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH); 
        add(contentWrapper, BorderLayout.CENTER);
    }

    private void themSanPham() {
        try {
            String maSP = txtMaSP.getText().trim();
            String tenSP = txtTenSP.getText().trim();
            String loaiSP = txtLoaiSP.getText().trim();
            double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());

            if (maSP.isEmpty() || tenSP.isEmpty() || loaiSP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Product product = new Product(maSP, tenSP, loaiSP, giaBan, soLuong);
            if (productController.addProduct(product)) {
                refreshTable();
                xoaTrang();
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void suaSanPham() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }

        try {
            String maSP = txtMaSP.getText().trim();
            String tenSP = txtTenSP.getText().trim();
            String loaiSP = txtLoaiSP.getText().trim();
            double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());

            if (tenSP.isEmpty() || loaiSP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            Product updatedProduct = new Product(maSP, tenSP, loaiSP, giaBan, soLuong);
            if (productController.updateProduct(maSP, updatedProduct)) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void xoaSanPham() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        String maSP = txtMaSP.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (productController.deleteProduct(maSP)) {
                refreshTable();
                xoaTrang();
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!");
            }
        }
    }

    private void luuSanPham() {
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu!");
    }

    private void xoaTrang() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtLoaiSP.setText("");
        txtGiaBan.setText("");
        txtSoLuong.setText("");
        table.clearSelection();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Product> products = productController.getAllProducts();
        for (Product p : products) {
            tableModel.addRow(new Object[]{
                p.getMaSP(), 
                p.getTenSP(), 
                p.getLoaiSP(), 
                String.format("%,.0f VND", p.getGiaBan()), 
                p.getSoLuongTon()
            });
        }
    }

    public List<Product> getProductList() {
        return productController.getAllProducts();
    }
    
    public ProductController getProductController() {
        return productController;
    }
}