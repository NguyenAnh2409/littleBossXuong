package nguyen.anh.littleboss_xuong.model;

public class Cart {

        private String _id;
        private String name;
        private String image;
        private int quantity;
        private String price;
        private String product_id;
        private boolean isCheck;
        private String receipt_id;

        public Cart(String _id, String name, String image, int quantity, String price) {
            this._id = _id;
            this.name = name;
            this.image = image;
            this.quantity = quantity;
            this.price = price;
        }

    public Cart() {
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


}
