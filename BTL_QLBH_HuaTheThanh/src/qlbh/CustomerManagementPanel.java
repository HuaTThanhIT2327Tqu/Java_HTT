package qlbh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerManagementPanel extends JPanel {
    private JTextField txtMaKH, txtHoTen, txtSDT, txtDiaChi;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Customer> customerList;
    private JButton btnThem, btnXoa, btnCapNhat;

    public CustomerManagementPanel() {
        customerList = new ArrayList<>();
        initializeUI();
        loadSampleData();
    }

    private void initializeUI() {
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

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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
        String maKH = txtMaKH.getText();
        String hoTen = txtHoTen.getText();
        String sdt = txtSDT.getText();
        String diaChi = txtDiaChi.getText();

        if (maKH.isEmpty() || hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra trùng mã
        for (Customer c : customerList) {
            if (c.getMaKH().equals(maKH)) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
                return;
            }
        }

        Customer customer = new Customer(maKH, hoTen, sdt, diaChi);
        customerList.add(customer);
        tableModel.addRow(new Object[]{maKH, hoTen, sdt, diaChi});
        xoaTrang();
        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
    }

    private void xoaKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            customerList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            xoaTrang();
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
        }
    }

    private void capNhatKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!");
            return;
        }

        String maKH = txtMaKH.getText();
        String hoTen = txtHoTen.getText();
        String sdt = txtSDT.getText();
        String diaChi = txtDiaChi.getText();

        if (maKH.isEmpty() || hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Cập nhật trong list
        Customer customer = customerList.get(selectedRow);
        customer.setHoTen(hoTen);
        customer.setSdt(sdt);
        customer.setDiaChi(diaChi);

        // Cập nhật trong table
        tableModel.setValueAt(hoTen, selectedRow, 1);
        tableModel.setValueAt(sdt, selectedRow, 2);
        tableModel.setValueAt(diaChi, selectedRow, 3);

        JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
    }

    private void xoaTrang() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        table.clearSelection();
    }

    private void loadSampleData() {
        // Thêm dữ liệu mẫu
        customerList.add(new Customer("KH001", "Nguyễn Văn An", "0912345678", "Hà Nội"));
        customerList.add(new Customer("KH002", "Trần Thị Bình", "0987654321", "TP.HCM"));
        customerList.add(new Customer("KH003", "Lê Văn Cường", "0909123456", "Đà Nẵng"));
        customerList.add(new Customer("KH004", "Phạm Minh Đức", "0938123456", "Hải Phòng"));
        customerList.add(new Customer("KH005", "Hoàng Thị Lan", "0976123987", "Nghệ An"));
        customerList.add(new Customer("KH006", "Vũ Quốc Huy", "0918765432", "Cần Thơ"));
        customerList.add(new Customer("KH007", "Đặng Ngọc Hòa", "0905123987", "Huế"));
        customerList.add(new Customer("KH008", "Ngô Thị Hằng", "0945123987", "Bắc Ninh"));
        customerList.add(new Customer("KH009", "Bùi Anh Tuấn", "0982334455", "Hà Nội"));
        customerList.add(new Customer("KH010", "Trịnh Thị Thu", "0938776655", "Thanh Hóa"));
        customerList.add(new Customer("KH011", "Đỗ Minh Khôi", "0975667788", "TP.HCM"));
        customerList.add(new Customer("KH012", "Nguyễn Thị Mai", "0965887788", "Bình Dương"));
        customerList.add(new Customer("KH013", "Phan Văn Long", "0909123777", "Đà Nẵng"));
        customerList.add(new Customer("KH014", "Trương Thị Hồng", "0916778899", "Hải Dương"));
        customerList.add(new Customer("KH015", "Lý Tuấn Kiệt", "0938999777", "TP.HCM"));
        customerList.add(new Customer("KH016", "Nguyễn Thị Thu Hà", "0978123123", "Quảng Ninh"));
        customerList.add(new Customer("KH017", "Phạm Văn Dũng", "0904333555", "Nam Định"));
        customerList.add(new Customer("KH018", "Lê Hoàng Yến", "0989123999", "Vĩnh Long"));
        customerList.add(new Customer("KH019", "Đoàn Quốc Bảo", "0919555777", "Nha Trang"));
        customerList.add(new Customer("KH020", "Trần Thị Kim Oanh", "0908444666", "Bình Thuận"));
        customerList.add(new Customer("KH021", "Nguyễn Hữu Tài", "0933222111", "Hà Tĩnh"));
        customerList.add(new Customer("KH022", "Phan Thị Ngọc Diệp", "0977333222", "Cà Mau"));
        customerList.add(new Customer("KH023", "Võ Minh Khang", "0988111222", "TP.HCM"));
        customerList.add(new Customer("KH024", "Đặng Thị Thu Trang", "0911222333", "Hà Nội"));
        customerList.add(new Customer("KH025", "Bùi Thanh Phong", "0966778899", "Đà Lạt"));

        for (Customer c : customerList) {
            tableModel.addRow(new Object[]{
                c.getMaKH(), c.getHoTen(), c.getSdt(), c.getDiaChi()
            });
        }
    }

    // THÊM PHƯƠNG THỨC GETTER ĐỂ STATISTICS PANEL LẤY DỮ LIỆU
    public List<Customer> getCustomerList() {
        return customerList;
    }
}