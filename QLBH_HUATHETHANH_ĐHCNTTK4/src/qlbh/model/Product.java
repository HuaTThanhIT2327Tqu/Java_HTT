package qlbh.model;

public class Product {
    private String maSP;
    private String tenSP;
    private String loaiSP;
    private double giaBan;
    private int soLuongTon;

    public Product(String maSP, String tenSP, String loaiSP, double giaBan, int soLuongTon) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
    }

    // Getters and Setters
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public String getLoaiSP() { return loaiSP; }
    public void setLoaiSP(String loaiSP) { this.loaiSP = loaiSP; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
}