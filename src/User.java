import java.util.HashMap;
public class User {
    private final HashMap<String, String> data;

    public User(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String doWeHaveThisId() {
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

        if (!(userEmail.equals("_newEmail"))) {
            return "_invalid";
        }
        if (!(userId.equals("_newId"))) {
            return "_invalid";
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(data.get("userId")
                + ", " + data.get("userEmail") + ", " + data.get("password") + ", \n");
        DataBase.getSingleTone().getController("UsersFollowingCommunities").writeFile(data.get("userId") + ", " + data.get("userEmail") + ", \n");
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

    String editId() {
        if (!(doWeHaveThisId().equals("_newId"))) {
            return "_invalid";
        }
        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.startsWith(data.get("userId"))) {
                String[] update = str.split(", ");
                update[0] = data.get("newId");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    stringBuilder.append(update[0]).append(", ");
                }
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(ans.toString(), true);
        return "_valid";
    }

    String editEmail() {
        if (!(doWeHaveThisEmail().equals("_newEmail"))) {
            return "_invalid";
        }
        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.split(", ")[1].equals(data.get("userEmail"))) {
                String[] update = str.split(", ");
                update[1] = data.get("newEmail");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    stringBuilder.append(update[0]).append(", ");
                }
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(ans.toString(), true);
        return "_valid";
    }
}

