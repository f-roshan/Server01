import java.util.HashMap;
public class UsersPosts {
    private final HashMap<String, String> data;

    public UsersPosts(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String addPost() {
        String id = String.valueOf(DataBase.postIdGetter);
        DataBase.postIdGetter++;
        String zero=String.valueOf(0);

       //id_userId_CommunityName_Like_disLike_numberOfComments_date_title_caption
        DataBase.getSingleTone().getController("UsersPostsDetails")
                .writeFile(id + ", " + data.get("userId") + ", " + data.get("communityName") + ", " + zero
                        + ", " + zero + ", " + zero + ", " + data.get("date") + ", " + data.get("title") + ", " +  data.get("caption") +", \n");
        return "_valid";
    }


    String getPostsDetails() {
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        for (int i = 0; i < details.length; i++) {
            if (details[i].startsWith(data.get("postId"))) {
                return details[i];
            }
        }
        return "_invalid";
    }


    String getPostComments(){
        String[] comments = DataBase.getSingleTone().getController("Comments").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : comments) {
            if (str.startsWith(data.get("postId"))) {
                ans.append(str).append("\n");
            }
        }
        return ans.toString();
    }

}
