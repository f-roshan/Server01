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
        String zero=String.valueOf(0);

       //id_userId_CommunityName_Like_disLike_numberOfComments_date
        DataBase.getSingleTone().getController("UsersPostsDetails")
                .writeFile(id + ", " + data.get("userId") + ", " + data.get("communityName") + ", " + zero
                        + ", " + zero + ", " + zero + ", " + data.get("date") + ", \n");

        DataBase.getSingleTone().getController("UsersPostsTitle")
                .writeFile(id + ", " + data.get("userId") + ", " + data.get("communityName") + ", " + data.get("title") + ", \n");

        DataBase.getSingleTone().getController("UsersPostsCaption")
                .writeFile(id + ", " + data.get("userId") + ", " + data.get("communityName") + ", " + data.get("caption") + ", \n");

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

    String getPostTitle() {
        String[] titles = DataBase.getSingleTone().getController("UsersPostsTitle").readFile().split("\n");
        for (int i = 0; i < titles.length; i++) {
            if (titles[i].startsWith(data.get("postId"))) {
                return titles[i];
            }
        }
        return "_invalid";
    }

    String getPostCaption() {
        String[] captions = DataBase.getSingleTone().getController("UsersPostsCaption").readFile().split("\n");
        for (int i = 0; i < captions.length; i++) {
            if (captions[i].startsWith(data.get("postId"))) {
                return captions[i];
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





}
