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
        return "_newId";
    }

    String doWeHaveThisEmail() {
        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.split(", ")[1].equals(data.get("userEmail"))) {
                return "_oldEmail";
            }
        }
        return "_newEmail";
    }

    String signUp() {
        String userId = doWeHaveThisId();
        String userEmail = doWeHaveThisEmail();

        if(!(userEmail.equals("_newEmail"))){
            return "_invalid";
        }
        if (!(userId.equals("_newId"))){
            return "_invalid";
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(data.get("userId")
                + ", " + data.get("userEmail")+", "+data.get("password") + ", \n");
        DataBase.getSingleTone().getController("UsersFollowingCommunities").writeFile(data.get("userId") + ", " + data.get("userEmail")+ ", \n");
        DataBase.userCounter++;
        return "_valid";
    }

    String signIn() {
        String userId = doWeHaveThisId();
        if (userId.equals("_newId")) {
            return "_invalid";
        } else if (!userId.split(", ")[2].equals(data.get("password"))) {
            return "_invalidMatch";
        }
        return "_valid";
    }

    String getAccount() {
        return DataBase.getSingleTone().getController("UsersInformation").getRow(data.get("userId"));
    }


}
