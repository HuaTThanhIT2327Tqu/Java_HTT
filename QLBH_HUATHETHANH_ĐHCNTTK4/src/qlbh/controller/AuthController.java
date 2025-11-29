package qlbh.controller;

public class AuthController {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123456";
    
    public boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
    
    public boolean changePassword(String oldPassword, String newPassword) {
        if (ADMIN_PASSWORD.equals(oldPassword)) {
            // Trong thực tế, sẽ lưu mật khẩu mới vào database
            // Ở đây chỉ là mô phỏng
            return true;
        }
        return false;
    }
    
    public String getAdminUsername() {
        return ADMIN_USERNAME;
    }
}