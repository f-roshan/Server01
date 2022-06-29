import java.util.HashMap;
public class UsersPosts {
    private final HashMap<String, String> data;

    public UsersPosts(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String AddPost() {
        String id = String.valueOf(DataBase.postIdGetter);
        DataBase.postIdGetter++;
        DataBase.getSingleTone().getController("UsersPostsDetails")
                .writeFile(  id +", "+data.get("userId")+", " + data.get("communityName") + ", " + data.get("date")
                        + ", " + data.get("numberOfLikes") + ", " + data.get("numberOfDisLikes") + ", " + data.get("numberOfComments")+ ", \n");

        DataBase.getSingleTone().getController("UsersPostsTitle")
                .writeFile(  id +", "+data.get("userId")+", " + data.get("communityName") + ", " + data.get("title")+ ", \n");

        DataBase.getSingleTone().getController("UsersPostsCaption")
                .writeFile(  id +", "+data.get("userId")+", " + data.get("communityName") + ", " + data.get("caption")+ ", \n");
        return "valid";
    }

    String getPostsDetails() {
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
            for (int i = 0; i < details.length ; i++) {
                if (details[i].startsWith(data.get("id"))) {
                    return details[i];
                }
            }
        return "invalid";
    }


}
