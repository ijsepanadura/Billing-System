package lk.ijse.dep11;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep11.tm.Order1;

import java.util.ArrayList;

public class OrderSceneController {
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtStock;
    public TextField txtBuyPrice;
    public TextField txtSellPrice;
    public Button btnAdd;
    public Button btnNewOrder;
    public Label lblTotal;
    public Label lblProfit;
    public TableView<Order> tblCart;
    public Spinner<Integer> spnQty;

    ArrayList<Item> itemList = new ArrayList<>();
    double totalPrice=0;
    double profit =0 ;

    public void initialize(){

        Item item1 = new Item("123", "Atlas Marker Blue pen", 10, 150,170);

        Item item2 = new Item("456","Black pen",20,160,180);

        itemList.add(item1);
        itemList.add(item2);

        btnAdd.setDisable(true);
        btnAdd.setDefaultButton(true);
        spnQty.setDisable(true);
        spnQty.setEditable(true);
        spnQty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1,1));

        spnQty.valueProperty().addListener(e ->{
            if(spnQty.getValue()>Integer.parseInt(txtStock.getText())){
                btnAdd.setDisable(true);
            }
            else btnAdd.setDisable(false);
        });
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("barCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String inputCode = txtCode.getText().strip();
        String description = txtDescription.getText();
        int qty = spnQty.getValue();
        double price = Double.parseDouble(txtSellPrice.getText());
        double total = qty*price;
        double prof=0;

        for (Item item : itemList){
            if(item.getBarCode().equals(inputCode)){
                item.setStock(item.getStock()-qty);
                prof = (item.getSellingPrice()-item.getBuyingPrice())*qty;
                txtStock.setText(item.getStock()+"");
                btnAdd.setDisable(item.getStock()<=0);
                break;
            }
        }
        totalPrice += total;
        lblTotal.setText(String.format("Total : Rs. %.2f",totalPrice));
        profit += prof;
        lblProfit.setText(String.format("Profit : Rs. %.2f",profit));

        ObservableList<Order> orderList = tblCart.getItems();
        defaultState();

        if(orderList.size()>0){
            for(Order order : orderList){
                if(order.getBarCode().equals(inputCode)){
                    qty += order.getQty();
                    order.setQty(qty);
                    total = qty*price;
                    order.setTotal(total);
                    tblCart.refresh();
                    return;
                }
            }
            Order placeOrder = new Order(inputCode,description,qty,price,total);
            orderList.add(placeOrder);
            return;
        }
        Order placeOrder = new Order(inputCode,description,qty,price,total);
        orderList.add(placeOrder);

    }

    public void btnNewOrderOnAction(ActionEvent actionEvent) {
        defaultState();
        ObservableList<Order> observableList = tblCart.getItems();
        observableList.clear();
    }

    public void txtCodeOnAction(ActionEvent actionEvent) {
        spnQty.setDisable(false);
        String inputCode = txtCode.getText().strip();
        for(Item item : itemList){
            if(item.getBarCode().equals(inputCode)){
                txtDescription.setText(item.getDescription());
                txtStock.setText(item.getStock() +"");
                txtBuyPrice.setText(String.format("%.2f", item.getBuyingPrice()));
                txtSellPrice.setText(String.format("%.2f", item.getSellingPrice()));
                spnQty.requestFocus();

                if(item.getStock() >0 && spnQty.getValue()<=Integer.parseInt(txtStock.getText())){
                    btnAdd.setDisable(false);
                    spnQty.setDisable(false);
                }else{
                    new Alert(Alert.AlertType.ERROR,"Stock is not available").showAndWait();
                    defaultState();
                }
                return;

            }
        }
        new Alert(Alert.AlertType.ERROR,"Item doesn't exist").show();
        txtCode.requestFocus();
    }

    public void txtStockOnAction(ActionEvent actionEvent) {

    }
    public void defaultState(){
        txtCode.clear();
        txtCode.requestFocus();
        txtDescription.clear();
        spnQty.getValueFactory().setValue(1);
        txtStock.clear();
        txtSellPrice.clear();
        txtBuyPrice.clear();
        spnQty.setDisable(true);
        btnAdd.setDisable(true);
    }
}
class Item{
    private String barCode;
    private String description;
    private int stock;
    private double buyingPrice;
    private double sellingPrice;

    public Item(String barCode,String description, int stock, double buyingPrice,double sellingPrice){
        this.setBarCode(barCode);
        this.setDescription(description);
        this.setStock(stock);
        this.setBuyingPrice(buyingPrice);
        this.setSellingPrice(sellingPrice);
    }

    double getProfit(){
        return getSellingPrice() - getBuyingPrice();
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

}
class Order {
    private String barCode;
    private String description;
    private int qty;
    private double price;
    private  double total;

    public Order() {
    }

    public Order(String barCode, String description, int qty, double price, double total) {
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

