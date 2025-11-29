package qlbh.model;

public class InvoiceDetail {
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