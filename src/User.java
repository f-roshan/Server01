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
                return str;
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
        DataBase.getSingleTone().getController("UsersFollowingCommunities").writeFile(data.get("userId") +", \n");
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


    String editId() {
        String old=data.get("userId");
        data.put("userId", data.get("newUserId"));
        if (!(doWeHaveThisId().equals("_newId"))) {
            return "_invalid";
        }
        data.put("userId",old);
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
        String old = data.get("userEmail");
        data.put("userEmail", data.get("newUserEmail"));
        if (!(doWeHaveThisEmail().equals("_newEmail"))) {
            return "_invalid";
        }
        data.put("userEmail",old);

        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.split(", ")[1].equals(data.get("userEmail"))) {
                String[] update = str.split(", ");
                update[1] = data.get("newEmail");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    stringBuilder.append(update[i]).append(", ");
                }
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersInformation").writeFile(ans.toString(), true);
        return "_valid";
    }


    String editPassword(){
        String users = DataBase.getSingleTone().getController("UsersInformation").readFile();
        String[] split = users.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("userId"))) {
                String[] update = str.split(", ");
                update[2] = data.get("newPassword");
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < update.length; i++) {
                    stringBuilder.append(update[i]).append(", ");
                }
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("ClientAccounts").writeFile(ans.toString(), true);
        return "_passwordChanged";
    }


    String getAccount() {
        return DataBase.getSingleTone().getController("UsersInformation").getRow(data.get("userId"));
    }


    String getCommunities(){
        String communities = DataBase.getSingleTone().getController("UsersFollowingCommunities").readFile();
        String[] split = communities.split("\n");

        for (String str : split) {
            if (str.split(", ")[0].equals(data.get("userId"))) {
                String[] update = str.split(", ");

                if(update.length==1){
                    return ", ";
                }

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 1; i < update.length; i++) {
                    stringBuilder.append(update[i]).append(", ");
                }

                return stringBuilder.toString();
            }
        }
        return "_invalid";
    }


    String getFeed(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            String[] split = str.split(", ");
            if (split[1].equals(data.get("userId"))){
                ans.append(str).append(" \n");
            }
        }
        return ans.toString();
    }


    String likeThisPost(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            if (str.startsWith(data.get("postId"))) {

                String[] update = str.split(", ");
                String number= update[3];
                int numberOfLikes=Integer.parseInt(number);
                numberOfLikes++;
                number= String.valueOf(numberOfLikes);
                update[3]=number;

                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersPostsDetails").writeFile(ans.toString(), true);
        return "_liked";
    }


    String disLikeThisPost(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            if (str.startsWith(data.get("postId"))) {

                String[] update = str.split(", ");
                String number = update[4];
                int numberOfDislikes = Integer.parseInt(number);
                numberOfDislikes ++;
                number= String.valueOf(numberOfDislikes);
                update[4] = number;

                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersPostsDetails").writeFile(ans.toString(), true);
        return "_liked";
    }


    String likeThisComment(){
        String[] details = DataBase.getSingleTone().getController("Comments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            String[] split = str.split(", ");
            if (split[1].equals(data.get("commentId"))) {
                String number= split[2];
                int numberOfLikes=Integer.parseInt(number);
                numberOfLikes++;
                number= String.valueOf(numberOfLikes);
                split[2]=number;
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    change.append(split[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("Comments").writeFile(ans.toString(), true);
        return "_liked";
    }


    String dislikeThisComment(){
        String[] details = DataBase.getSingleTone().getController("Comments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            String[] split = str.split(", ");
            if (split[1].equals(data.get("commentId"))){
                String number= split[3];
                int numberOfLikes=Integer.parseInt(number);
                numberOfLikes++;
                number= String.valueOf(numberOfLikes);
                split[3]=number;
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    change.append(split[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("Comments").writeFile(ans.toString(), true);
        return "_disliked";
    }

}

