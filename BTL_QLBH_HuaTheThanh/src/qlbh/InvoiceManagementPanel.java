package qlbh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceManagementPanel extends JPanel {
    private JTextField txtMaHD, txtMaKH, txtHoTen, txtNgayBan, txtTimKiem, txtSoLuong;
    private JTable tableSanPham, tableChiTietHD, tableLichSuHD;
    private DefaultTableModel tableModelSP, tableModelCTHD, tableModelLSHD;
    private JLabel lblTongTien;
    private JButton btnTimKiem, btnThemSP, btnXoaSP, btnThanhToan, btnTaoMoi, btnXemLichSu;
    
    private List<Product> danhSachSP;
    private List<Customer> danhSachKH;
    private List<Invoice> danhSachHD;
    private List<InvoiceDetail> chiTietHD;

    public InvoiceManagementPanel() {
        danhSachSP = new ArrayList<>();
        danhSachKH = new ArrayList<>();
        danhSachHD = new ArrayList<>();
        chiTietHD = new ArrayList<>();
        initComponents();
        loadSampleData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === PANEL THÔNG TIN HÓA ĐƠN ===
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Đổi từ 3,2 thành 4,2
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        
        infoPanel.add(new JLabel("Mã hóa đơn:"));
        txtMaHD = new JTextField();
        infoPanel.add(txtMaHD);
        
        infoPanel.add(new JLabel("Mã khách hàng:"));
        txtMaKH = new JTextField();
        infoPanel.add(txtMaKH);
        
        // THÊM DÒNG HỌ TÊN
        infoPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        infoPanel.add(txtHoTen);
        
        infoPanel.add(new JLabel("Ngày bán:"));
        txtNgayBan = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtNgayBan.setEditable(false);
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

        // === TABBED PANE ĐỂ CHỨA CÁC CHỨC NĂNG ===
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab Tạo hóa đơn
        JPanel createInvoicePanel = new JPanel(new BorderLayout());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(infoPanel, BorderLayout.NORTH);
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
        searchHistoryPanel.add(new JLabel("Mã HD hoặc Mã KH:"));
        searchHistoryPanel.add(txtTimKiemHD);
        
        JButton btnTimKiemHD = new JButton("Tìm kiếm");
        searchHistoryPanel.add(btnTimKiemHD);
        
        JButton btnLamMoiLS = new JButton("Làm mới");
        searchHistoryPanel.add(btnLamMoiLS);
        
        panel.add(searchHistoryPanel, BorderLayout.NORTH);

        // Bảng lịch sử hóa đơn
        String[] columnsLSHD = {"Mã HD", "Mã KH", "Ngày bán", "Số sản phẩm", "Tổng tiền", "Chi tiết"};
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
            
            for (Invoice hd : danhSachHD) {
                if (hd.getMaHD().toLowerCase().contains(keyword) || 
                    hd.getMaKH().toLowerCase().contains(keyword) ||
                    keyword.isEmpty()) {
                    
                    tableModelLSHD.addRow(new Object[]{
                        hd.getMaHD(),
                        hd.getMaKH(),
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
                Invoice hoaDon = timHoaDonTheoMa(maHD);
                
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

    private void loadLichSuHoaDon() {
        tableModelLSHD.setRowCount(0);
        for (Invoice hd : danhSachHD) {
            tableModelLSHD.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaKH(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(hd.getNgayBan()),
                hd.getChiTiet().size(),
                String.format("%,.0f VND", hd.getTongTien()),
                "Xem chi tiết"
            });
        }
    }

    private Invoice timHoaDonTheoMa(String maHD) {
        for (Invoice hd : danhSachHD) {
            if (hd.getMaHD().equals(maHD)) {
                return hd;
            }
        }
        return null;
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
            // Chuyển sang tab lịch sử
            JTabbedPane tabbedPane = (JTabbedPane) getComponent(0);
            tabbedPane.setSelectedIndex(1);
            loadLichSuHoaDon();
        });
        
        // Enter để tìm kiếm
        txtTimKiem.addActionListener(e -> timKiemSanPham());
        
        // Enter để thêm sản phẩm
        txtSoLuong.addActionListener(e -> themSanPhamVaoHD());
    }

    private void timKiemSanPham() {
        String keyword = txtTimKiem.getText().toLowerCase().trim();
        tableModelSP.setRowCount(0);
        
        for (Product sp : danhSachSP) {
            if (sp.getTenSP().toLowerCase().contains(keyword) || 
                sp.getMaSP().toLowerCase().contains(keyword) ||
                sp.getLoaiSP().toLowerCase().contains(keyword) ||
                keyword.isEmpty()) {
                
                tableModelSP.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getLoaiSP(),
                    String.format("%,.0f VND", sp.getGiaBan()),
                    sp.getSoLuongTon()
                });
            }
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
            double donGia = Double.parseDouble(tableModelSP.getValueAt(selectedRow, 3).toString().replace(" VND", "").replace(",", ""));
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

    private void thanhToan() {
        // Kiểm tra thông tin
        if (txtMaHD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn!");
            txtMaHD.requestFocus();
            return;
        }

        if (chiTietHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm nào!");
            return;
        }

        // Kiểm tra trùng mã hóa đơn
        for (Invoice hd : danhSachHD) {
            if (hd.getMaHD().equals(txtMaHD.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại! Vui lòng nhập mã khác.");
                txtMaHD.requestFocus();
                return;
            }
        }

        // Tạo hóa đơn mới
        Invoice hoaDon = new Invoice(
            txtMaHD.getText().trim(),
            txtMaKH.getText().trim(),
            new Date()
        );

        // Thêm chi tiết vào hóa đơn
        for (InvoiceDetail detail : chiTietHD) {
            hoaDon.addChiTiet(detail);
            
            // Cập nhật tồn kho
            for (Product sp : danhSachSP) {
                if (sp.getMaSP().equals(detail.getMaSP())) {
                    sp.setSoLuongTon(sp.getSoLuongTon() - detail.getSoLuong());
                    break;
                }
            }
        }

        danhSachHD.add(hoaDon);
        
        // Hiển thị thông báo thành công (CÓ THÊM HỌ TÊN)
        String message = String.format(
            "THANH TOÁN THÀNH CÔNG!\n\n" +
            "Mã hóa đơn: %s\n" +
            "Khách hàng: %s - %s\n" +
            "Tổng tiền: %s\n" +
            "Số sản phẩm: %d",
            hoaDon.getMaHD(),
            txtMaKH.getText().trim(),
            txtHoTen.getText().trim(),
            String.format("%,.0f VND", hoaDon.getTongTien()),
            chiTietHD.size()
        );
        
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        
        // Reset form
        taoHoaDonMoi();
        // Cập nhật lại lịch sử
        loadLichSuHoaDon();
    }

    private void taoHoaDonMoi() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtHoTen.setText(""); // THÊM CLEAR HỌ TÊN
        // Giữ nguyên ngày bán
        tableModelCTHD.setRowCount(0);
        chiTietHD.clear();
        lblTongTien.setText("0 VND");
        txtSoLuong.setText("1");
        timKiemSanPham(); // Reload danh sách sản phẩm với tồn kho mới
    }

    private void loadSampleData() {
        // Thêm dữ liệu mẫu sản phẩm
        danhSachSP.add(new Product("SP001", "Laptop Dell Inspiron 15", "Laptop", 15990000, 10));
        danhSachSP.add(new Product("SP002", "iPhone 14 Pro Max", "Điện thoại", 27990000, 5));
        danhSachSP.add(new Product("SP003", "Samsung Smart TV 55 inch", "TV", 12990000, 8));
        danhSachSP.add(new Product("SP004", "Tủ lạnh LG Inverter", "Điện lạnh", 8990000, 6));
        danhSachSP.add(new Product("SP005", "Máy giặt Toshiba", "Gia dụng", 6490000, 4));
        danhSachSP.add(new Product("SP006", "Tai nghe AirPods Pro", "Phụ kiện", 5990000, 15));
        danhSachSP.add(new Product("SP007", "Bàn phím cơ Logitech", "Phụ kiện", 1890000, 12));
        danhSachSP.add(new Product("SP008", "Chuột không dây Logitech MX Master 3", "Phụ kiện", 2490000, 20));
        danhSachSP.add(new Product("SP009", "Loa Bluetooth JBL Charge 5", "Âm thanh", 3990000, 9));
        danhSachSP.add(new Product("SP010", "Máy lạnh Daikin Inverter 1HP", "Điện lạnh", 9500000, 5));
        danhSachSP.add(new Product("SP011", "Nồi chiên không dầu Philips XXL", "Gia dụng", 5490000, 10));
        danhSachSP.add(new Product("SP012", "Điện thoại Samsung Galaxy S24 Ultra", "Điện thoại", 28990000, 6));
        danhSachSP.add(new Product("SP013", "Đồng hồ thông minh Apple Watch Series 9", "Thiết bị đeo tay", 10990000, 8));
        danhSachSP.add(new Product("SP014", "Máy tính bảng iPad Air M2", "Tablet", 16990000, 7));
        danhSachSP.add(new Product("SP015", "Tivi Sony Bravia 65 inch 4K", "TV", 19990000, 4));
        danhSachSP.add(new Product("SP016", "Máy ảnh Canon EOS M50", "Máy ảnh", 15490000, 5));
        danhSachSP.add(new Product("SP017", "Bếp điện từ Sunhouse SHD6868", "Gia dụng", 2590000, 10));
        danhSachSP.add(new Product("SP018", "Máy lọc không khí Sharp FP-J40E-W", "Gia dụng", 4890000, 7));
        danhSachSP.add(new Product("SP019", "Laptop HP Pavilion 14", "Laptop", 17990000, 9));
        danhSachSP.add(new Product("SP020", "Máy hút bụi Xiaomi Vacuum Cleaner G10", "Gia dụng", 6290000, 11));
        danhSachSP.add(new Product("SP021", "Bình siêu tốc Lock&Lock", "Gia dụng", 890000, 25));
        danhSachSP.add(new Product("SP022", "Máy sấy tóc Dyson Supersonic", "Phụ kiện", 11990000, 6));
        danhSachSP.add(new Product("SP023", "Loa Soundbar Samsung HW-Q600C", "Âm thanh", 7490000, 4));
        danhSachSP.add(new Product("SP024", "Máy tính bảng Xiaomi Pad 6", "Tablet", 10990000, 8));
        danhSachSP.add(new Product("SP025", "Tủ đông Sanaky 280L", "Điện lạnh", 7990000, 5));

        // Thêm dữ liệu mẫu khách hàng
        danhSachKH.add(new Customer("KH001", "Nguyễn Văn An", "0912345678", "Hà Nội"));
        danhSachKH.add(new Customer("KH002", "Trần Thị Bình", "0987654321", "TP.HCM"));
        danhSachKH.add(new Customer("KH003", "Lê Văn Cường", "0909123456", "Đà Nẵng"));
        danhSachKH.add(new Customer("KH004", "Phạm Minh Đức", "0938123456", "Hải Phòng"));
        danhSachKH.add(new Customer("KH005", "Hoàng Thị Lan", "0976123987", "Nghệ An"));
        danhSachKH.add(new Customer("KH006", "Vũ Quốc Huy", "0918765432", "Cần Thơ"));
        danhSachKH.add(new Customer("KH007", "Đặng Ngọc Hòa", "0905123987", "Huế"));
        danhSachKH.add(new Customer("KH008", "Ngô Thị Hằng", "0945123987", "Bắc Ninh"));
        danhSachKH.add(new Customer("KH009", "Bùi Anh Tuấn", "0982334455", "Hà Nội"));
        danhSachKH.add(new Customer("KH010", "Trịnh Thị Thu", "0938776655", "Thanh Hóa"));
        danhSachKH.add(new Customer("KH011", "Đỗ Minh Khôi", "0975667788", "TP.HCM"));
        danhSachKH.add(new Customer("KH012", "Nguyễn Thị Mai", "0965887788", "Bình Dương"));
        danhSachKH.add(new Customer("KH013", "Phan Văn Long", "0909123777", "Đà Nẵng"));
        danhSachKH.add(new Customer("KH014", "Trương Thị Hồng", "0916778899", "Hải Dương"));
        danhSachKH.add(new Customer("KH015", "Lý Tuấn Kiệt", "0938999777", "TP.HCM"));
        danhSachKH.add(new Customer("KH016", "Nguyễn Thị Thu Hà", "0978123123", "Quảng Ninh"));
        danhSachKH.add(new Customer("KH017", "Phạm Văn Dũng", "0904333555", "Nam Định"));
        danhSachKH.add(new Customer("KH018", "Lê Hoàng Yến", "0989123999", "Vĩnh Long"));
        danhSachKH.add(new Customer("KH019", "Đoàn Quốc Bảo", "0919555777", "Nha Trang"));
        danhSachKH.add(new Customer("KH020", "Trần Thị Kim Oanh", "0908444666", "Bình Thuận"));
        danhSachKH.add(new Customer("KH021", "Nguyễn Hữu Tài", "0933222111", "Hà Tĩnh"));
        danhSachKH.add(new Customer("KH022", "Phan Thị Ngọc Diệp", "0977333222", "Cà Mau"));
        danhSachKH.add(new Customer("KH023", "Võ Minh Khang", "0988111222", "TP.HCM"));
        danhSachKH.add(new Customer("KH024", "Đặng Thị Thu Trang", "0911222333", "Hà Nội"));
        danhSachKH.add(new Customer("KH025", "Bùi Thanh Phong", "0966778899", "Đà Lạt"));

        // Thêm một số hóa đơn mẫu
        Invoice hd1 = new Invoice("HD001", "KH001", new Date());
        hd1.addChiTiet(new InvoiceDetail("SP001", "Laptop Dell Inspiron 15", 1, 15990000));
        hd1.addChiTiet(new InvoiceDetail("SP006", "Tai nghe AirPods Pro", 1, 5990000));
        danhSachHD.add(hd1);

        Invoice hd2 = new Invoice("HD002", "KH002", new Date());
        hd2.addChiTiet(new InvoiceDetail("SP002", "iPhone 14 Pro Max", 1, 27990000));
        hd2.addChiTiet(new InvoiceDetail("SP013", "Đồng hồ thông minh Apple Watch Series 9", 1, 10990000));
        danhSachHD.add(hd2);

        Invoice hd3 = new Invoice("HD003", "KH003", new Date());
        hd3.addChiTiet(new InvoiceDetail("SP003", "Samsung Smart TV 55 inch", 1, 12990000));
        hd3.addChiTiet(new InvoiceDetail("SP023", "Loa Soundbar Samsung HW-Q600C", 1, 7490000));
        danhSachHD.add(hd3);

        // Hiển thị tất cả sản phẩm
        timKiemSanPham();
        loadLichSuHoaDon();
    }

    // Inner class cho chi tiết hóa đơn
    public static class InvoiceDetail {
        private String maSP;
        private String tenSP;
        private int soLuong;
        private double donGia;
        private double thanhTien;

        public InvoiceDetail(String maSP, String tenSP, int soLuong, double donGia) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = soLuong * donGia;
        }

        // Getters
        public String getMaSP() { return maSP; }
        public String getTenSP() { return tenSP; }
        public int getSoLuong() { return soLuong; }
        public double getDonGia() { return donGia; }
        public double getThanhTien() { return thanhTien; }
    }

    // THÊM PHƯƠNG THỨC GETTER ĐỂ STATISTICS PANEL LẤY DỮ LIỆU
    public java.util.List<Invoice> getDanhSachHoaDon() {
        return danhSachHD;
    }
}