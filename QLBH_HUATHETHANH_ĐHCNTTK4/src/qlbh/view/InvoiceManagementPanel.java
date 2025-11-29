package qlbh.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import qlbh.model.*;
import qlbh.controller.InvoiceController;
import qlbh.controller.CustomerController;
import qlbh.controller.ProductController;
import java.net.URL;

public class InvoiceManagementPanel extends JPanel {
    private JTextField txtMaHD, txtHoTen, txtSDT, txtDiaChi, txtNgayBan, txtTimKiem, txtSoLuong, txtTimSDT;
    private JTable tableSanPham, tableChiTietHD, tableLichSuHD;
    private DefaultTableModel tableModelSP, tableModelCTHD, tableModelLSHD;
    private JLabel lblTongTien;
    private JButton btnTimKiem, btnThemSP, btnXoaSP, btnThanhToan, btnTaoMoi, btnXemLichSu, btnTimKH;
    
    private List<InvoiceDetail> chiTietHD; 
    private InvoiceController invoiceController;
    private CustomerController customerController;
    private ProductController productController;
    private CustomerManagementPanel customerManagementPanel;

    public InvoiceManagementPanel(CustomerManagementPanel customerManagementPanel) {
        this.customerManagementPanel = customerManagementPanel;
        this.customerController = customerManagementPanel.getCustomerController();
        this.productController = new ProductController();
        this.invoiceController = new InvoiceController(customerController, productController);
        this.chiTietHD = new ArrayList<>();
        initComponents();
        taoHoaDonMoi(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === PANEL THÔNG TIN HÓA ĐƠN ===
        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        
        infoPanel.add(new JLabel("Mã hóa đơn:"));
        txtMaHD = new JTextField();
        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new Color(240, 240, 240));
        infoPanel.add(txtMaHD);
        
        // Mục tìm kiếm khách hàng riêng
        infoPanel.add(new JLabel("Tìm KH theo SĐT:"));
        JPanel timKHPanel = new JPanel(new BorderLayout());
        txtTimSDT = new JTextField();
        timKHPanel.add(txtTimSDT, BorderLayout.CENTER);
        
        btnTimKH = new JButton("Tìm");
        btnTimKH.setBackground(new Color(70, 130, 180));
        btnTimKH.setForeground(Color.WHITE);
        btnTimKH.setPreferredSize(new Dimension(60, 25));
        timKHPanel.add(btnTimKH, BorderLayout.EAST);
        infoPanel.add(timKHPanel);
        
        infoPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        infoPanel.add(txtHoTen);
        
        infoPanel.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        infoPanel.add(txtSDT);
        
        infoPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        infoPanel.add(txtDiaChi);
        
        infoPanel.add(new JLabel("Ngày bán:"));
        txtNgayBan = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtNgayBan.setEditable(false);
        txtNgayBan.setBackground(new Color(240, 240, 240));
        infoPanel.add(txtNgayBan);

        // === PANEL TÌM KIẾM SẢN PHẨM ===
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));
        
        JPanel searchTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchTopPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        searchTopPanel.add(txtTimKiem);
        
        btnTimKiem = new JButton("Tìm kiếm");
        searchTopPanel.add(btnTimKiem);
        
        searchPanel.add(searchTopPanel, BorderLayout.NORTH);

        // === PANEL CHỌN SẢN PHẨM VÀ SỐ LƯỢNG ===
        JPanel selectProductPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectProductPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(5);
        txtSoLuong.setText("1");
        selectProductPanel.add(txtSoLuong);
        
        btnThemSP = new JButton("Thêm vào hóa đơn");
        btnThemSP.setBackground(new Color(0, 102, 204));
        btnThemSP.setForeground(Color.WHITE);
        selectProductPanel.add(btnThemSP);
        
        btnXoaSP = new JButton("Xóa sản phẩm");
        btnXoaSP.setBackground(new Color(204, 0, 0));
        btnXoaSP.setForeground(Color.WHITE);
        selectProductPanel.add(btnXoaSP);
        
        searchPanel.add(selectProductPanel, BorderLayout.SOUTH);

        // === BẢNG DANH SÁCH SẢN PHẨM ===
        String[] columnsSP = {"Mã SP", "Tên sản phẩm", "Loại", "Giá bán", "Tồn kho"};
        tableModelSP = new DefaultTableModel(columnsSP, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSanPham = new JTable(tableModelSP);
        tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneSP = new JScrollPane(tableSanPham);
        scrollPaneSP.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        scrollPaneSP.setPreferredSize(new Dimension(500, 150));

        // === BẢNG CHI TIẾT HÓA ĐƠN ===
        String[] columnsCTHD = {"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModelCTHD = new DefaultTableModel(columnsCTHD, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableChiTietHD = new JTable(tableModelCTHD);
        tableChiTietHD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneCTHD = new JScrollPane(tableChiTietHD);
        scrollPaneCTHD.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        scrollPaneCTHD.setPreferredSize(new Dimension(500, 150));

        // === PANEL TỔNG TIỀN VÀ NÚT THANH TOÁN ===
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("TỔNG TIỀN:"));
        lblTongTien = new JLabel("0 VND");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);
        totalPanel.add(lblTongTien);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTaoMoi = new JButton("Tạo hóa đơn mới");
        btnTaoMoi.setBackground(new Color(255, 153, 0));
        btnTaoMoi.setForeground(Color.WHITE);
        buttonPanel.add(btnTaoMoi);
        
        btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setBackground(new Color(0, 153, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(btnThanhToan);
        
        btnXemLichSu = new JButton("XEM LỊCH SỬ HÓA ĐƠN");
        btnXemLichSu.setBackground(new Color(102, 0, 153));
        btnXemLichSu.setForeground(Color.WHITE);
        btnXemLichSu.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(btnXemLichSu);
        
        bottomPanel.add(totalPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        // === KHUNG CHỦ ĐỀ & LOGO MỚI ===
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
        
        // === GOM NHÓM HEADER VÀ THÔNG TIN HÓA ĐƠN ===
        JPanel topMainPanel = new JPanel(new BorderLayout()); 
        topMainPanel.add(headerPanel, BorderLayout.NORTH); 
        topMainPanel.add(infoPanel, BorderLayout.CENTER); 

        // === TABBED PANE ĐỂ CHỨA CÁC CHỨC NĂNG ===
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab Tạo hóa đơn
        JPanel createInvoicePanel = new JPanel(new BorderLayout());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(topMainPanel, BorderLayout.NORTH); 
        leftPanel.add(searchPanel, BorderLayout.CENTER);
        leftPanel.add(scrollPaneSP, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(scrollPaneCTHD, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);
        
        createInvoicePanel.add(splitPane, BorderLayout.CENTER);
        tabbedPane.addTab("Tạo hóa đơn", createInvoicePanel);

        // Tab Lịch sử hóa đơn
        JPanel historyPanel = createHistoryPanel();
        tabbedPane.addTab("Lịch sử hóa đơn", historyPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // === XỬ LÝ SỰ KIỆN ===
        setupEventHandlers();
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Panel tìm kiếm lịch sử
        JPanel searchHistoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchHistoryPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm hóa đơn"));
        
        JTextField txtTimKiemHD = new JTextField(20);
        searchHistoryPanel.add(new JLabel("Mã HD hoặc Tên KH:"));
        searchHistoryPanel.add(txtTimKiemHD);
        
        JButton btnTimKiemHD = new JButton("Tìm kiếm");
        searchHistoryPanel.add(btnTimKiemHD);
        
        JButton btnLamMoiLS = new JButton("Làm mới");
        searchHistoryPanel.add(btnLamMoiLS);
        
        panel.add(searchHistoryPanel, BorderLayout.NORTH);

        // Bảng lịch sử hóa đơn
        String[] columnsLSHD = {"Mã HD", "Tên KH", "SĐT", "Ngày bán", "Số sản phẩm", "Tổng tiền", "Chi tiết"};
        tableModelLSHD = new DefaultTableModel(columnsLSHD, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLichSuHD = new JTable(tableModelLSHD);
        tableLichSuHD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneLSHD = new JScrollPane(tableLichSuHD);
        scrollPaneLSHD.setBorder(BorderFactory.createTitledBorder("Lịch sử hóa đơn"));
        
        panel.add(scrollPaneLSHD, BorderLayout.CENTER);

        // Panel chi tiết hóa đơn được chọn
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        
        String[] columnsCTHDLS = {"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel tableModelCTHDLS = new DefaultTableModel(columnsCTHDLS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableCTHDLS = new JTable(tableModelCTHDLS);
        JScrollPane scrollPaneCTHDLS = new JScrollPane(tableCTHDLS);
        
        detailPanel.add(scrollPaneCTHDLS, BorderLayout.CENTER);
        
        panel.add(detailPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho tab lịch sử
        btnTimKiemHD.addActionListener(e -> {
            String keyword = txtTimKiemHD.getText().trim().toLowerCase();
            tableModelLSHD.setRowCount(0);
            
            List<Invoice> invoices = invoiceController.getAllInvoices();
            for (Invoice hd : invoices) {
                String tenKH = layTenKhachHang(hd.getMaKH());
                if (hd.getMaHD().toLowerCase().contains(keyword) || 
                    tenKH.toLowerCase().contains(keyword) ||
                    keyword.isEmpty()) {
                    
                    tableModelLSHD.addRow(new Object[]{
                        hd.getMaHD(),
                        tenKH,
                        laySDTKhachHang(hd.getMaKH()),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(hd.getNgayBan()),
                        hd.getChiTiet().size(),
                        String.format("%,.0f VND", hd.getTongTien()),
                        "Xem chi tiết"
                    });
                }
            }
        });
        
        btnLamMoiLS.addActionListener(e -> {
            txtTimKiemHD.setText("");
            loadLichSuHoaDon();
        });
        
        tableLichSuHD.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            
            int selectedRow = tableLichSuHD.getSelectedRow();
            if (selectedRow >= 0) {
                String maHD = tableModelLSHD.getValueAt(selectedRow, 0).toString();
                Invoice hoaDon = invoiceController.findInvoiceById(maHD);
                
                if (hoaDon != null) {
                    tableModelCTHDLS.setRowCount(0);
                    for (InvoiceDetail detail : hoaDon.getChiTiet()) {
                        tableModelCTHDLS.addRow(new Object[]{
                            detail.getMaSP(),
                            detail.getTenSP(),
                            detail.getSoLuong(),
                            String.format("%,.0f VND", detail.getDonGia()),
                            String.format("%,.0f VND", detail.getThanhTien())
                        });
                    }
                }
            }
        });

        // Load dữ liệu ban đầu
        loadLichSuHoaDon();
        
        return panel;
    }

    private String layTenKhachHang(String maKH) {
        Customer kh = customerController.findCustomerById(maKH);
        return kh != null ? kh.getHoTen() : "Không xác định";
    }

    private String laySDTKhachHang(String maKH) {
        Customer kh = customerController.findCustomerById(maKH);
        return kh != null ? kh.getSdt() : "Không xác định";
    }

    private void loadLichSuHoaDon() {
        tableModelLSHD.setRowCount(0);
        List<Invoice> invoices = invoiceController.getAllInvoices();
        for (Invoice hd : invoices) {
            tableModelLSHD.addRow(new Object[]{
                hd.getMaHD(),
                layTenKhachHang(hd.getMaKH()),
                laySDTKhachHang(hd.getMaKH()),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(hd.getNgayBan()),
                hd.getChiTiet().size(),
                String.format("%,.0f VND", hd.getTongTien()),
                "Xem chi tiết"
            });
        }
    }

    private void setupEventHandlers() {
        // Tìm kiếm sản phẩm
        btnTimKiem.addActionListener(e -> timKiemSanPham());
        
        // Thêm sản phẩm vào hóa đơn
        btnThemSP.addActionListener(e -> themSanPhamVaoHD());
        
        // Xóa sản phẩm khỏi hóa đơn
        btnXoaSP.addActionListener(e -> xoaSanPhamKhoiHD());
        
        // Thanh toán
        btnThanhToan.addActionListener(e -> thanhToan());
        
        // Tạo hóa đơn mới
        btnTaoMoi.addActionListener(e -> taoHoaDonMoi());
        
        // Xem lịch sử hóa đơn
        btnXemLichSu.addActionListener(e -> {
            JTabbedPane tabbedPane = (JTabbedPane) getComponent(0);
            tabbedPane.setSelectedIndex(1);
            loadLichSuHoaDon();
        });
        
        // Tìm khách hàng theo SĐT
        btnTimKH.addActionListener(e -> timKhachHangTheoSDT());
        
        // Enter để tìm kiếm sản phẩm
        txtTimKiem.addActionListener(e -> timKiemSanPham());
        
        // Enter để thêm sản phẩm
        txtSoLuong.addActionListener(e -> themSanPhamVaoHD());
        
        // Enter để tìm khách hàng
        txtTimSDT.addActionListener(e -> timKhachHangTheoSDT());
    }

    private void timKhachHangTheoSDT() {
        String sdtTim = txtTimSDT.getText().trim();
        if (sdtTim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập SĐT để tìm kiếm khách hàng!");
            return;
        }
        
        // Tìm khách hàng theo SĐT trong danh sách hiện có
        Customer kh = customerController.findCustomerByPhone(sdtTim);
        if (kh != null) {
            // Tìm thấy khách hàng - điền thông tin vào form
            txtHoTen.setText(kh.getHoTen());
            txtSDT.setText(kh.getSdt());
            txtDiaChi.setText(kh.getDiaChi());
            JOptionPane.showMessageDialog(this, "Đã tìm thấy khách hàng: " + kh.getHoTen());
        } else {
            // Không tìm thấy
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy khách hàng với SĐT: " + sdtTim + "\nVui lòng nhập thông tin khách hàng mới.",
                "Không tìm thấy",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void taoHoaDonMoi() {
        // Tạo mã hóa đơn mới
        txtMaHD.setText(invoiceController.generateInvoiceId());
        
        // Reset thông tin khách hàng
        txtTimSDT.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        
        // Reset chi tiết hóa đơn
        tableModelCTHD.setRowCount(0);
        chiTietHD.clear();
        lblTongTien.setText("0 VND");
        txtSoLuong.setText("1");
        
        // Cập nhật ngày bán
        txtNgayBan.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        
        // Reload danh sách sản phẩm
        timKiemSanPham();
    }

    private void thanhToan() {
        // Kiểm tra thông tin khách hàng
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên khách hàng!");
            txtHoTen.requestFocus();
            return;
        }
        
        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại khách hàng!");
            txtSDT.requestFocus();
            return;
        }

        if (chiTietHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm nào!");
            return;
        }

        // Tìm hoặc tạo khách hàng
        String maKH = null;
        Customer khachHangMoi = null;
        
        Customer existingCustomer = customerController.findCustomerByPhone(txtSDT.getText().trim());
        if (existingCustomer != null) {
            maKH = existingCustomer.getMaKH();
        } else {
            // Tạo khách hàng mới
            maKH = customerController.generateCustomerId();
            khachHangMoi = new Customer(maKH, txtHoTen.getText().trim(), txtSDT.getText().trim(), txtDiaChi.getText().trim());
            customerController.addCustomer(khachHangMoi);
            
            // ĐỒNG BỘ: Thêm khách hàng mới vào CustomerManagementPanel
            if (customerManagementPanel != null) {
                customerManagementPanel.themKhachHangTuHoaDon(khachHangMoi);
            }
        }

        // Tạo hóa đơn mới
        Invoice hoaDon = new Invoice(
            txtMaHD.getText().trim(),
            maKH,
            new Date()
        );

        // Thêm chi tiết vào hóa đơn
        for (InvoiceDetail detail : chiTietHD) {
            hoaDon.addChiTiet(detail);
        }

        if (invoiceController.createInvoice(hoaDon)) {
            // Hiển thị thông báo thành công
            String message = String.format(
                "THANH TOÁN THÀNH CÔNG!\n\n" +
                "Mã hóa đơn: %s\n" +
                "Khách hàng: %s\n" +
                "SĐT: %s\n" +
                "Tổng tiền: %s\n" +
                "Số sản phẩm: %d",
                hoaDon.getMaHD(),
                txtHoTen.getText().trim(),
                txtSDT.getText().trim(),
                String.format("%,.0f VND", hoaDon.getTongTien()),
                chiTietHD.size()
            );
            
            if (khachHangMoi != null) {
                message += "\n\nĐã thêm khách hàng mới vào danh sách!";
            }
            
            JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            // Tạo hóa đơn mới
            taoHoaDonMoi();
            // Cập nhật lại lịch sử
            loadLichSuHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại! Kiểm tra lại tồn kho sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemSanPham() {
        String keyword = txtTimKiem.getText().toLowerCase().trim();
        tableModelSP.setRowCount(0);
        
        List<Product> products = productController.searchProducts(keyword);
        for (Product sp : products) {
            tableModelSP.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getLoaiSP(),
                String.format("%,.0f VND", sp.getGiaBan()),
                sp.getSoLuongTon()
            });
        }
    }

    private void themSanPhamVaoHD() {
        int selectedRow = tableSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ danh sách!");
            return;
        }

        try {
            String maSP = tableModelSP.getValueAt(selectedRow, 0).toString();
            String tenSP = tableModelSP.getValueAt(selectedRow, 1).toString();
            
            // Parse giá từ chuỗi định dạng
            String giaBanStr = tableModelSP.getValueAt(selectedRow, 3).toString().replace(" VND", "").replace(",", "");
            double donGia = Double.parseDouble(giaBanStr);
            
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            int tonKho = Integer.parseInt(tableModelSP.getValueAt(selectedRow, 4).toString());

            // Kiểm tra số lượng
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }

            if (soLuong > tonKho) {
                JOptionPane.showMessageDialog(this, 
                    String.format("Số lượng vượt quá tồn kho! Tồn kho hiện có: %d", tonKho));
                return;
            }

            // Kiểm tra sản phẩm đã có trong hóa đơn chưa
            for (int i = 0; i < tableModelCTHD.getRowCount(); i++) {
                if (tableModelCTHD.getValueAt(i, 0).equals(maSP)) {
                    // Cập nhật số lượng nếu sản phẩm đã có
                    int oldSoLuong = Integer.parseInt(tableModelCTHD.getValueAt(i, 2).toString());
                    int newSoLuong = oldSoLuong + soLuong;
                    
                    if (newSoLuong > tonKho) {
                        JOptionPane.showMessageDialog(this, 
                            String.format("Tổng số lượng vượt quá tồn kho! Tồn kho hiện có: %d", tonKho));
                        return;
                    }
                    
                    double thanhTien = newSoLuong * donGia;
                    tableModelCTHD.setValueAt(newSoLuong, i, 2);
                    tableModelCTHD.setValueAt(String.format("%,.0f VND", thanhTien), i, 4);
                    
                    // Cập nhật trong danh sách chi tiết
                    for (InvoiceDetail detail : chiTietHD) {
                        if (detail.getMaSP().equals(maSP)) {
                            chiTietHD.remove(detail);
                            chiTietHD.add(new InvoiceDetail(maSP, tenSP, newSoLuong, donGia));
                            break;
                        }
                    }
                    
                    capNhatTongTien();
                    txtSoLuong.setText("1");
                    return;
                }
            }

            // Thêm sản phẩm mới vào hóa đơn
            double thanhTien = soLuong * donGia;
            tableModelCTHD.addRow(new Object[]{
                maSP,
                tenSP,
                soLuong,
                String.format("%,.0f VND", donGia),
                String.format("%,.0f VND", thanhTien)
            });

            // Thêm vào danh sách chi tiết
            chiTietHD.add(new InvoiceDetail(maSP, tenSP, soLuong, donGia));
            
            capNhatTongTien();
            txtSoLuong.setText("1");
            
            JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào hóa đơn!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!");
        }
    }

    private void xoaSanPhamKhoiHD() {
        int selectedRow = tableChiTietHD.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa sản phẩm này khỏi hóa đơn?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String maSP = tableModelCTHD.getValueAt(selectedRow, 0).toString();
            tableModelCTHD.removeRow(selectedRow);
            
            // Xóa khỏi danh sách chi tiết
            chiTietHD.removeIf(detail -> detail.getMaSP().equals(maSP));
            
            capNhatTongTien();
            JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi hóa đơn!");
        }
    }

    private void capNhatTongTien() {
        double tongTien = 0;
        for (InvoiceDetail detail : chiTietHD) {
            tongTien += detail.getThanhTien();
        }
        lblTongTien.setText(String.format("%,.0f VND", tongTien));
    }

    // THÊM PHƯƠNG THỨC GETTER ĐỂ STATISTICS PANEL LẤY DỮ LIỆU
    public java.util.List<Invoice> getDanhSachHoaDon() {
        return invoiceController.getAllInvoices();
    }
    
    public InvoiceController getInvoiceController() {
        return invoiceController;
    }
}