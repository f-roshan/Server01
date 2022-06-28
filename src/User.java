import java.util.HashMap;
public class User{
  private final HashMap<String,String> data;
    public User(HashMap<String, String> data) {
        this.data = data;
    }
    public HashMap<String, String> getData() {
        return data;
    }

    String doWeHaveThisId(){
        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.startsWith(data.get("userId"))) {
                return str;
            }
        }
        return "We already have this Id";
    }

    String signUp() {
        String userId = doWeHaveThisId();
        if (userId.equals("We already have this Id")){
            return "invalid";
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(data.get("userId")
                + ", " + data.get("password") + ", \n");
        DataBase.getSingleTone().getController("UsersFollowingCommunities").writeFile(data.get("userId") + ", \n");
        DataBase.userCounter++;
        return "valid";
    }

    String signIn() {
        String userId = doWeHaveThisId();
        if (userId.equals("We already have this Id")) {
            return "invalid";
        } else if (!userId.split(", ")[1].equals(data.get("password"))) {
            return "invalid-match";
        }
        return "valid";
    }

    String getAccount() {
        return DataBase.getSingleTone().getController("UsersInformation").getRow(data.get("userId"));
    }
}
