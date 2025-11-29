package qlbh.controller;

import qlbh.model.Customer;
import java.util.List;
import java.util.ArrayList;

public class CustomerController {
    private List<Customer> customerList;
    
    public CustomerController() {
        this.customerList = new ArrayList<>();
        loadSampleData();
    }
    
    public boolean addCustomer(Customer customer) {
        // Kiểm tra trùng mã
        for (Customer c : customerList) {
            if (c.getMaKH().equals(customer.getMaKH())) {
                return false;
            }
        }
        return customerList.add(customer);
    }
    
    public boolean updateCustomer(String maKH, Customer updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getMaKH().equals(maKH)) {
                customerList.set(i, updatedCustomer);
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteCustomer(String maKH) {
        return customerList.removeIf(c -> c.getMaKH().equals(maKH));
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerList);
    }
    
    public Customer findCustomerById(String maKH) {
        return customerList.stream()
                .filter(c -> c.getMaKH().equals(maKH))
                .findFirst()
                .orElse(null);
    }
    
    public Customer findCustomerByPhone(String sdt) {
        return customerList.stream()
                .filter(c -> c.getSdt().equals(sdt))
                .findFirst()
                .orElse(null);
    }
    
    public String generateCustomerId() {
        int maxId = customerList.stream()
                .map(customer -> customer.getMaKH())
                .filter(id -> id.startsWith("KH"))
                .map(id -> id.substring(2))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return "KH" + String.format("%03d", maxId + 1);
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
    }
}