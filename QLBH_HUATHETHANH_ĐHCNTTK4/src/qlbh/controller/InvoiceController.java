package qlbh.controller;

import qlbh.model.Invoice;
import qlbh.model.InvoiceDetail;
import qlbh.model.Customer;
import qlbh.model.Product;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceController {
    private List<Invoice> invoiceList;
    private CustomerController customerController;
    private ProductController productController;
    
    public InvoiceController(CustomerController customerController, ProductController productController) {
        this.invoiceList = new ArrayList<>();
        this.customerController = customerController;
        this.productController = productController;
        loadSampleData();
    }
    
    public String generateInvoiceId() {
        int maxId = invoiceList.stream()
                .map(invoice -> invoice.getMaHD())
                .filter(id -> id.startsWith("HD"))
                .map(id -> id.substring(2))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return "HD" + String.format("%03d", maxId + 1);
    }
    
    public boolean createInvoice(Invoice invoice) {
        // Kiểm tra tồn kho trước khi tạo hóa đơn
        for (InvoiceDetail detail : invoice.getChiTiet()) {
            Product product = productController.findProductById(detail.getMaSP());
            if (product == null || product.getSoLuongTon() < detail.getSoLuong()) {
                return false;
            }
        }
        
        // Cập nhật tồn kho
        for (InvoiceDetail detail : invoice.getChiTiet()) {
            Product product = productController.findProductById(detail.getMaSP());
            if (product != null) {
                product.setSoLuongTon(product.getSoLuongTon() - detail.getSoLuong());
            }
        }
        
        return invoiceList.add(invoice);
    }
    
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoiceList);
    }
    
    public List<Invoice> getInvoicesByDateRange(Date fromDate, Date toDate) {
        return invoiceList.stream()
                .filter(invoice -> !invoice.getNgayBan().before(fromDate) && 
                                  !invoice.getNgayBan().after(toDate))
                .collect(Collectors.toList());
    }
    
    public List<Invoice> getInvoicesByCustomer(String maKH) {
        return invoiceList.stream()
                .filter(invoice -> invoice.getMaKH().equals(maKH))
                .collect(Collectors.toList());
    }
    
    public double calculateTotalRevenue(Date fromDate, Date toDate) {
        return getInvoicesByDateRange(fromDate, toDate).stream()
                .mapToDouble(Invoice::getTongTien)
                .sum();
    }
    
    public Invoice findInvoiceById(String maHD) {
        return invoiceList.stream()
                .filter(invoice -> invoice.getMaHD().equals(maHD))
                .findFirst()
                .orElse(null);
    }
    
    public Map<String, Double> getRevenueByMonth(int year) {
        Map<String, Double> revenueByMonth = new LinkedHashMap<>();
        
        for (int month = 1; month <= 12; month++) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, 1);
            Date startDate = cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date endDate = cal.getTime();
            
            double revenue = calculateTotalRevenue(startDate, endDate);
            revenueByMonth.put("Tháng " + month, revenue);
        }
        
        return revenueByMonth;
    }
    
    public Map<String, Double> getRevenueByQuarter(int year) {
        Map<String, Double> revenueByQuarter = new LinkedHashMap<>();
        
        for (int quarter = 1; quarter <= 4; quarter++) {
            int startMonth = (quarter - 1) * 3;
            int endMonth = startMonth + 2;
            
            Calendar startCal = Calendar.getInstance();
            startCal.set(year, startMonth, 1);
            Date startDate = startCal.getTime();
            
            Calendar endCal = Calendar.getInstance();
            endCal.set(year, endMonth, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date endDate = endCal.getTime();
            
            double revenue = calculateTotalRevenue(startDate, endDate);
            revenueByQuarter.put("Quý " + quarter, revenue);
        }
        
        return revenueByQuarter;
    }
    
    private void loadSampleData() {
        // Thêm một số hóa đơn mẫu
        Invoice hd1 = new Invoice("HD001", "KH001", new Date());
        hd1.addChiTiet(new InvoiceDetail("SP001", "Laptop Dell Inspiron 15", 1, 15990000));
        hd1.addChiTiet(new InvoiceDetail("SP006", "Tai nghe AirPods Pro", 1, 5990000));
        invoiceList.add(hd1);

        Invoice hd2 = new Invoice("HD002", "KH002", new Date());
        hd2.addChiTiet(new InvoiceDetail("SP002", "iPhone 14 Pro Max", 1, 27990000));
        hd2.addChiTiet(new InvoiceDetail("SP013", "Đồng hồ thông minh Apple Watch Series 9", 1, 10990000));
        invoiceList.add(hd2);

        Invoice hd3 = new Invoice("HD003", "KH003", new Date());
        hd3.addChiTiet(new InvoiceDetail("SP003", "Samsung Smart TV 55 inch", 1, 12990000));
        hd3.addChiTiet(new InvoiceDetail("SP023", "Loa Soundbar Samsung HW-Q600C", 1, 7490000));
        invoiceList.add(hd3);
    }
}