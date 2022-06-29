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
        DataBase.getSingleTone().getController("Comments").writeFile( data.get("postId") + ", " + id + ", " + data.get("comment") + ", \n");
        return "Comment is added.";
    }

    String thisPostComments() {
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
