package qlbh.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String maHD;
    private String maKH;
    private Date ngayBan;
    private List<InvoiceDetail> chiTiet;
    private double tongTien;

    public Invoice(String maHD, String maKH, Date ngayBan) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayBan = ngayBan;
        this.chiTiet = new ArrayList<>();
        this.tongTien = 0;
    }

    // Getters and Setters
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public Date getNgayBan() { return ngayBan; }
    public void setNgayBan(Date ngayBan) { this.ngayBan = ngayBan; }
    public List<InvoiceDetail> getChiTiet() { return chiTiet; }
    public double getTongTien() { return tongTien; }
    
    public void addChiTiet(InvoiceDetail detail) {
        chiTiet.add(detail);
        tongTien += detail.getThanhTien();
    }
}