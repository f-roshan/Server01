import java.util.HashMap;

public class UsersPosts {
    private final HashMap<String, String> data;

    public UsersPosts(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }


}
