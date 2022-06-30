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
        return "_CommentIAdded.";
    }

}
