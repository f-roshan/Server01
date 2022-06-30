import java.util.HashMap;

public class Comment {
    private final HashMap<String, String> data;

    public Comment(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String addComment() {
        String id = String.valueOf(DataBase.commentIdGetter);
        DataBase.commentIdGetter++;
        DataBase.getSingleTone().getController("Comments").writeFile(
                data.get("postId") + ", " + id + ", " + data.get("userId") + ", " + data.get("comment") + ", \n");
        numberOfComments();
        return "_CommentIAdded.";
    }

    void numberOfComments(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : details) {
            if (str.startsWith(data.get("postId"))) {
                String[] update = str.split(", ");
                String number = update[5];
                int numberOfComments = Integer.parseInt(number);
                numberOfComments ++;
                number= String.valueOf(numberOfComments);
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
    }

}
