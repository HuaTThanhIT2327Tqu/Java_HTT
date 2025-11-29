package qlbh.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import qlbh.model.*;
import qlbh.controller.CustomerController;
import java.net.URL;

public class CustomerManagementPanel extends JPanel {
    private JTextField txtMaKH, txtHoTen, txtSDT, txtDiaChi;
    private JTable table;
    private DefaultTableModel tableModel;
    private CustomerController customerController;
    private JButton btnThem, btnXoa, btnCapNhat;

    public CustomerManagementPanel() {
        customerController = new CustomerController();
        initializeUI();
        refreshTable();
    }

    private void initializeUI() {
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

        // Panel nhập thông tin
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        inputPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        inputPanel.add(txtMaKH);

        inputPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        inputPanel.add(txtHoTen);

        inputPanel.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        inputPanel.add(txtSDT);

        inputPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập nhật");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnCapNhat);

        // Panel trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Bảng hiển thị
        String[] columns = {"Mã KH", "Họ tên", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // BẮT ĐẦU SẮP XẾP VỚI HEADER MỚI
        JPanel contentWrapper = new JPanel(new BorderLayout(10, 10));
        contentWrapper.add(topPanel, BorderLayout.NORTH);
        contentWrapper.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout()); 
        add(headerPanel, BorderLayout.NORTH); 
        add(contentWrapper, BorderLayout.CENTER); 

        // Xử lý sự kiện
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnThem.addActionListener(e -> themKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnCapNhat.addActionListener(e -> capNhatKhachHang());

        // Xử lý chọn hàng trong bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtMaKH.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
    }

    private void themKhachHang() {
        String maKH = txtMaKH.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (maKH.isEmpty() || hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        Customer customer = new Customer(maKH, hoTen, sdt, diaChi);
        if (customerController.addCustomer(customer)) {
            refreshTable();
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
        }
    }

    private void xoaKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }

        String maKH = txtMaKH.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (customerController.deleteCustomer(maKH)) {
                refreshTable();
                xoaTrang();
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }
        }
    }

    private void capNhatKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!");
            return;
        }

        String maKH = txtMaKH.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (maKH.isEmpty() || hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        Customer updatedCustomer = new Customer(maKH, hoTen, sdt, diaChi);
        if (customerController.updateCustomer(maKH, updatedCustomer)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!");
        }
    }

    private void xoaTrang() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        table.clearSelection();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerController.getAllCustomers();
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{
                c.getMaKH(), c.getHoTen(), c.getSdt(), c.getDiaChi()
            });
        }
    }

    public List<Customer> getCustomerList() {
        return customerController.getAllCustomers();
    }
    
    public CustomerController getCustomerController() {
        return customerController;
    }

    // THÊM PHƯƠNG THỨC ĐỂ NHẬN KHÁCH HÀNG TỪ HÓA ĐƠN
    public void themKhachHangTuHoaDon(Customer customer) {
        if (customerController.addCustomer(customer)) {
            refreshTable();
            System.out.println("Đã thêm khách hàng mới từ hóa đơn: " + customer.getHoTen());
        }
    }
}