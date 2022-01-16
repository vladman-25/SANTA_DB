package data;

public final class Gift {
    private String productName;
    private Double price;
    private String category;
    private int quantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Gift(final String productName,
                final Double price,
                final String category,
                final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "GiftData{"
                + "giftName='" + getProductName() + '\''
                + ", price=" + getPrice()
                + ", category='" + getCategory() + '\'' + "}\n";
    }
}
