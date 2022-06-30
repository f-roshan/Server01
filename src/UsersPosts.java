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
                .writeFile(  id +", "+data.get("userId")+", " + data.get("communityName") + ", " + data.get("numberOfLikes")
                        + ", " + data.get("numberOfDisLikes") + ", " + data.get("numberOfComments")+ " ," + data.get("date") + ", \n");

        DataBase.getSingleTone().getController("UsersPostsTitle")
                .writeFile(  id +", " + data.get("userId")+", " + data.get("communityName") + ", " + data.get("title")+ ", \n");

        DataBase.getSingleTone().getController("UsersPostsCaption")
                .writeFile(  id +", "+data.get("userId")+", " + data.get("communityName") + ", " + data.get("caption")+ ", \n");

        return "_valid";
    }

    String getPostsDetails() {
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
            for (int i = 0; i < details.length ; i++) {
                if (details[i].startsWith(data.get("postId"))) {
                    return details[i];
                }
            }
        return "_invalid";
    }

    String getPostTitle(){
        String[] titles = DataBase.getSingleTone().getController("UsersPostsTitle").readFile().split("\n");
        for (int i = 0; i < titles.length ; i++) {
            if (titles[i].startsWith(data.get("postId"))) {
                return titles[i];
            }
        }
        return "_invalid";
    }

    String getPostCaption(){
        String[] captions = DataBase.getSingleTone().getController("UsersPostsCaption").readFile().split("\n");
        for (int i = 0; i < captions.length ; i++) {
            if (captions[i].startsWith(data.get("postId"))) {
                return captions[i];
            }
        }
        return "_invalid";
    }

    //String getPostComments()


    String likeThisPost(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            if (str.startsWith(data.get("postId"))) {
                String[] update = str.split(", ");
                update[4] = data.get("like");
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersPostsDetails").writeFile(ans.toString(), true);
        return "liked";
    }

    String disLikeThisPost(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : details) {
            if (str.startsWith(data.get("postId"))) {
                String[] update = str.split(", ");
                update[5] = data.get("disLike");
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersPostsDetails").writeFile(ans.toString(), true);
        return "disliked";
    }





}
