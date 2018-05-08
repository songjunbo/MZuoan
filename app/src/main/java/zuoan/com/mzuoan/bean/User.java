package zuoan.com.mzuoan.bean;

/**
 * Created by Administrator on 2017/8/5.
 */
public class User {

    String mobile;
    String name;
    String uid;
    String username;
    String token;
    String status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    private static User user;

    public static User getInstance() {
        if (user == null) {
            synchronized ("") {
                if (user == null){
                    user = new User();
                }
            }
        }
        return user;

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
