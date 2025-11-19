package qlbh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementPanel extends JPanel {
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtLoaiSP;
    private JTextField txtGiaBan;
    private JTextField txtSoLuong;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Product> productList;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnXoaTrang;

    public ProductManagementPanel() {
        productList = new ArrayList<>();
        initialize();
        loadSampleData();
    }

    private void initialize() {
        setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
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
        
        JLabel lblGiaBan = new JLabel("Giá bán:");
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
        add(scrollPane, BorderLayout.SOUTH);
        
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
                txtGiaBan.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtSoLuong.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });
    }

    private void themSanPham() {
        try {
            String maSP = txtMaSP.getText();
            String tenSP = txtTenSP.getText();
            String loaiSP = txtLoaiSP.getText();
            double giaBan = Double.parseDouble(txtGiaBan.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            // Check duplicate
            for (Product p : productList) {
                if (p.getMaSP().equals(maSP)) {
                    JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!");
                    return;
                }
            }

            Product product = new Product(maSP, tenSP, loaiSP, giaBan, soLuong);
            productList.add(product);
            tableModel.addRow(new Object[]{maSP, tenSP, loaiSP, giaBan, soLuong});
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");

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
            String maSP = txtMaSP.getText();
            String tenSP = txtTenSP.getText();
            String loaiSP = txtLoaiSP.getText();
            double giaBan = Double.parseDouble(txtGiaBan.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            Product product = productList.get(selectedRow);
            product.setTenSP(tenSP);
            product.setLoaiSP(loaiSP);
            product.setGiaBan(giaBan);
            product.setSoLuongTon(soLuong);

            tableModel.setValueAt(tenSP, selectedRow, 1);
            tableModel.setValueAt(loaiSP, selectedRow, 2);
            tableModel.setValueAt(giaBan, selectedRow, 3);
            tableModel.setValueAt(soLuong, selectedRow, 4);

            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");

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

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            productList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
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

    private void loadSampleData() {
        productList.add(new Product("SP001", "Laptop Dell", "Điện tử", 15000, 10));
        productList.add(new Product("SP002", "iPhone 14", "Điện thoại", 20000, 5));
        productList.add(new Product("SP003", "Samsung TV", "Điện tử", 12000, 8));
        productList.add(new Product("SP006", "Loa Bluetooth JBL", "Âm thanh", 3000, 15));
        productList.add(new Product("SP007", "Chuột Logitech", "Phụ kiện", 500, 30));
        productList.add(new Product("SP008", "Bàn phím cơ Keychron", "Phụ kiện", 2500, 20));
        productList.add(new Product("SP009", "Máy lạnh Daikin", "Điện lạnh", 95000, 3));
        productList.add(new Product("SP010", "Nồi cơm điện Sharp", "Điện gia dụng", 2000, 25));
        productList.add(new Product("SP011", "Máy hút bụi Philips", "Điện gia dụng", 4500, 12));
        productList.add(new Product("SP012", "Điện thoại Oppo Reno10", "Điện thoại", 11000, 7));
        productList.add(new Product("SP013", "Tai nghe AirPods Pro", "Phụ kiện", 5500, 10));
        productList.add(new Product("SP014", "Apple Watch S9", "Thiết bị đeo tay", 9000, 6));
        productList.add(new Product("SP015", "Máy ảnh Canon EOS", "Máy ảnh", 18000, 4));
        productList.add(new Product("SP016", "Tivi Sony Bravia 55\"", "Điện tử", 22000, 5));
        productList.add(new Product("SP017", "Bếp từ Sunhouse", "Điện gia dụng", 3500, 9));
        productList.add(new Product("SP018", "Máy lọc không khí Xiaomi", "Điện gia dụng", 5000, 8));
        productList.add(new Product("SP019", "Laptop HP Pavilion", "Điện tử", 14000, 7));
        productList.add(new Product("SP020", "Điện thoại Samsung S24", "Điện thoại", 21000, 6));
        productList.add(new Product("SP021", "Máy sấy tóc Panasonic", "Điện gia dụng", 1200, 15));
        productList.add(new Product("SP022", "Bình siêu tốc Kangaroo", "Điện gia dụng", 800, 20));
        productList.add(new Product("SP023", "Loa Soundbar Sony", "Âm thanh", 7500, 5));
        productList.add(new Product("SP024", "Tủ đông Sanaky", "Điện lạnh", 65000, 4));
        productList.add(new Product("SP025", "Máy tính bảng iPad Air", "Điện tử", 18000, 6));
        for (Product p : productList) {
            tableModel.addRow(new Object[]{
                p.getMaSP(), p.getTenSP(), p.getLoaiSP(), p.getGiaBan(), p.getSoLuongTon()
            });
        }
    }
}