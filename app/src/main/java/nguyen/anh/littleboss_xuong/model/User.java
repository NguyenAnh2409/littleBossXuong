package nguyen.anh.littleboss_xuong.model;

public class User implements java.io.Serializable {

    private String _id;
    private String name;
    private String username;
    private String email;
    private String phone;

    public User(String _id, String name, String username, String email, String phone) {
        this._id = _id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public User(String _id, String name, String email, String phone) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public User() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
