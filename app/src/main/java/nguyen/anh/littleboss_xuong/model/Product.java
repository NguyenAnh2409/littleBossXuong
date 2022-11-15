package nguyen.anh.littleboss_xuong.model;

public class Product {


        private String _id;
        private String name;
        private String price;
        private String describes;
        private String evaluate;
        private String quantity;
        private String image;
        private boolean isPet;
        private boolean isStop;
        private String category;

        public Product(String _id, String name, String price, String describes, String evaluate, String quantity, String image, boolean isPet, boolean isStop, String category) {
            this._id = _id;
            this.name = name;
            this.price = price;
            this.describes = describes;
            this.evaluate = evaluate;
            this.quantity = quantity;
            this.image = image;
            this.isPet = isPet;
            this.isStop = isStop;
            this.category = category;
        }

    public Product() {
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isPet() {
            return isPet;
        }

        public void setPet(boolean pet) {
            isPet = pet;
        }

        public boolean isStop() {
            return isStop;
        }

        public void setStop(boolean stop) {
            isStop = stop;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

}
