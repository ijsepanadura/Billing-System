package lk.ijse.dep11.tm;

public class Order1 {
    private String barCode;
    private String description;
    private int qty;
    private double price;
    private  double total;

    public Order1() {
    }

    public Order1(String barCode, String description, int qty, double price, double total) {
        this.setBarCode(barCode);
        this.setDescription(description);
        this.setQty(qty);
        this.setPrice(price);
        this.setTotal(total);
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
