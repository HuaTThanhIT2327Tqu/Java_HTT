package qlbh.controller;

import qlbh.model.Product;
import java.util.List;
import java.util.ArrayList;

public class ProductController {
    private List<Product> productList;
    
    public ProductController() {
        this.productList = new ArrayList<>();
        loadSampleData();
    }
    
    public boolean addProduct(Product product) {
        // Kiểm tra trùng mã
        for (Product p : productList) {
            if (p.getMaSP().equals(product.getMaSP())) {
                return false;
            }
        }
        return productList.add(product);
    }
    
    public boolean updateProduct(String maSP, Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getMaSP().equals(maSP)) {
                productList.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteProduct(String maSP) {
        return productList.removeIf(p -> p.getMaSP().equals(maSP));
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }
    
    public Product findProductById(String maSP) {
        return productList.stream()
                .filter(p -> p.getMaSP().equals(maSP))
                .findFirst()
                .orElse(null);
    }
    
    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        
        for (Product p : productList) {
            if (p.getMaSP().toLowerCase().contains(lowerKeyword) ||
                p.getTenSP().toLowerCase().contains(lowerKeyword) ||
                p.getLoaiSP().toLowerCase().contains(lowerKeyword)) {
                result.add(p);
            }
        }
        return result;
    }
    
    public boolean updateProductQuantity(String maSP, int quantityChange) {
        Product product = findProductById(maSP);
        if (product != null) {
            int newQuantity = product.getSoLuongTon() + quantityChange;
            if (newQuantity >= 0) {
                product.setSoLuongTon(newQuantity);
                return true;
            }
        }
        return false;
    }
    
    private void loadSampleData() {
        // Thêm dữ liệu mẫu
        productList.add(new Product("SP001", "Laptop Dell Inspiron 15", "Laptop", 15990000, 10));
        productList.add(new Product("SP002", "iPhone 14 Pro Max", "Điện thoại", 27990000, 5));
        productList.add(new Product("SP003", "Samsung Smart TV 55 inch", "TV", 12990000, 8));
        productList.add(new Product("SP004", "Tủ lạnh LG Inverter", "Điện lạnh", 8990000, 6));
        productList.add(new Product("SP005", "Máy giặt Toshiba", "Gia dụng", 6490000, 4));
        productList.add(new Product("SP006", "Tai nghe AirPods Pro", "Phụ kiện", 5990000, 15));
        productList.add(new Product("SP007", "Bàn phím cơ Logitech", "Phụ kiện", 1890000, 12));
        productList.add(new Product("SP008", "Chuột không dây Logitech MX Master 3", "Phụ kiện", 2490000, 20));
        productList.add(new Product("SP009", "Loa Bluetooth JBL Charge 5", "Âm thanh", 3990000, 9));
        productList.add(new Product("SP010", "Máy lạnh Daikin Inverter 1HP", "Điện lạnh", 9500000, 5));
        productList.add(new Product("SP011", "Nồi chiên không dầu Philips XXL", "Gia dụng", 5490000, 10));
        productList.add(new Product("SP012", "Điện thoại Samsung Galaxy S24 Ultra", "Điện thoại", 28990000, 6));
        productList.add(new Product("SP013", "Đồng hồ thông minh Apple Watch Series 9", "Thiết bị đeo tay", 10990000, 8));
        productList.add(new Product("SP014", "Máy tính bảng iPad Air M2", "Tablet", 16990000, 7));
        productList.add(new Product("SP015", "Tivi Sony Bravia 65 inch 4K", "TV", 19990000, 4));
        productList.add(new Product("SP016", "Máy ảnh Canon EOS M50", "Máy ảnh", 15490000, 5));
        productList.add(new Product("SP017", "Bếp điện từ Sunhouse SHD6868", "Gia dụng", 2590000, 10));
        productList.add(new Product("SP018", "Máy lọc không khí Sharp FP-J40E-W", "Gia dụng", 4890000, 7));
        productList.add(new Product("SP019", "Laptop HP Pavilion 14", "Laptop", 17990000, 9));
        productList.add(new Product("SP020", "Máy hút bụi Xiaomi Vacuum Cleaner G10", "Gia dụng", 6290000, 11));
        productList.add(new Product("SP021", "Bình siêu tốc Lock&Lock", "Gia dụng", 890000, 25));
        productList.add(new Product("SP022", "Máy sấy tóc Dyson Supersonic", "Phụ kiện", 11990000, 6));
        productList.add(new Product("SP023", "Loa Soundbar Samsung HW-Q600C", "Âm thanh", 7490000, 4));
        productList.add(new Product("SP024", "Máy tính bảng Xiaomi Pad 6", "Tablet", 10990000, 8));
        productList.add(new Product("SP025", "Tủ đông Sanaky 280L", "Điện lạnh", 7990000, 5));
    }
}