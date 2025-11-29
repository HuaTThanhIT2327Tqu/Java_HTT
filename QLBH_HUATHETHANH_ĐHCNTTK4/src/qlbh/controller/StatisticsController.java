package qlbh.controller;

import qlbh.model.Invoice;
import qlbh.model.InvoiceDetail;
import qlbh.model.Customer;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsController {
    private InvoiceController invoiceController;
    private CustomerController customerController;
    
    public StatisticsController(InvoiceController invoiceController, CustomerController customerController) {
        this.invoiceController = invoiceController;
        this.customerController = customerController;
    }
    
    public Map<String, Double> getRevenueStatistics(String type, int year, String period) {
        switch (type) {
            case "Theo tháng":
                return invoiceController.getRevenueByMonth(year);
            case "Theo quý":
                return invoiceController.getRevenueByQuarter(year);
            case "Theo năm":
                Map<String, Double> yearlyRevenue = new HashMap<>();
                double totalRevenue = invoiceController.getInvoicesByDateRange(
                    getStartOfYear(year), getEndOfYear(year))
                    .stream()
                    .mapToDouble(Invoice::getTongTien)
                    .sum();
                yearlyRevenue.put("Năm " + year, totalRevenue);
                return yearlyRevenue;
            default:
                return new HashMap<>();
        }
    }
    
    public Customer getMostValuableCustomer(int year) {
        List<Invoice> yearlyInvoices = invoiceController.getInvoicesByDateRange(
            getStartOfYear(year), getEndOfYear(year));
        
        Map<String, Double> customerRevenue = new HashMap<>();
        for (Invoice invoice : yearlyInvoices) {
            customerRevenue.merge(invoice.getMaKH(), invoice.getTongTien(), Double::sum);
        }
        
        return customerRevenue.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> customerController.findCustomerById(entry.getKey()))
                .orElse(null);
    }
    
    public Map<String, Integer> getProductSalesRanking(Date fromDate, Date toDate) {
        List<Invoice> invoices = invoiceController.getInvoicesByDateRange(fromDate, toDate);
        Map<String, Integer> productSales = new HashMap<>();
        
        for (Invoice invoice : invoices) {
            for (InvoiceDetail detail : invoice.getChiTiet()) {
                productSales.merge(detail.getMaSP(), detail.getSoLuong(), Integer::sum);
            }
        }
        
        return productSales.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }
    
    public int getTotalInvoices(Date fromDate, Date toDate) {
        return invoiceController.getInvoicesByDateRange(fromDate, toDate).size();
    }
    
    public double getTotalRevenue(Date fromDate, Date toDate) {
        return invoiceController.calculateTotalRevenue(fromDate, toDate);
    }
    
    private Date getStartOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        return cal.getTime();
    }
    
    private Date getEndOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return cal.getTime();
    }
}