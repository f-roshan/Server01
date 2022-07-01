import java.util.HashMap;
public class Community {

    private final HashMap<String, String> data;

    public Community(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }


    String doWeHaveThisCommunity(){
        String users = DataBase.getSingleTone().getController("AllCommunities").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            if (str.startsWith(data.get("communityName"))) {
                return str;
            }
        }
        return "_newCommunity";
    }


    String addCommunity() {
        String userId = doWeHaveThisCommunity();
        if (!(userId.equals("_newCommunityName"))){
            return "_invalidName";
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(data.get("communityName")
                +", "+ data.get("adminId") + ", " +data.get("description") + ", \n");
        DataBase.getSingleTone().getController("NamesOfCommunities").writeFile(data.get("communityName")+", \n");
        return "_valid";
    }


    String editName() {
        String old=data.get("newCommunityName");
        data.put("communityName", data.get("newCommunityName"));
        if (!(doWeHaveThisCommunity().equals("_newCommunity"))) {
            return "_invalid";
        }
        data.put("newCommunityName",old);

        String communities = DataBase.getSingleTone().getController("AllCommunities").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.startsWith(data.get("communityName"))) {
                String[] update = str.split(", ");
                update[0] = data.get("newCommunityName");
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(ans.toString(), true);
        return "_valid";
    }


    String editDescription() {
        String communities = DataBase.getSingleTone().getController("AllCommunities").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("communityName"))) {
                str = data.get("communityName") + ", " + data.get("adminId") + ", "+ data.get("newDescription")+", ";
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(ans.toString(), true);
        return "_valid";
    }


    String getCommunitiesPosts(){
        String[] details = DataBase.getSingleTone().getController("UsersPostsDetails").readFile().split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : details) {
            String[] split = str.split(", ");
            if (split[2].equals(data.get("communityName"))){
                ans.append(str).append(" \n");
            }
        }
        return ans.toString();
    }


    String deletePost(){
        String posts = DataBase.getSingleTone().getController("UsersPostsDetails").readFile();
        String[] split = posts.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("postId"))) {
              continue;
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersPostsDetails").writeFile(ans.toString(), true);
        return "_PostIsDeleted";
    }

    String getAllCommunities(){
        String communities = DataBase.getSingleTone().getController("NamesOfCommunities").readFile();
        if(communities.equals(null)){
            return "_null";
        }
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split){
            ans.append(str).append(", ");
        }
        return ans.toString();
    }

    String communitySearch(){
        String communities = getAllCommunities();
        if(communities.equals("_null")){
            return "_null";
        }
        StringBuilder ans = new StringBuilder();
        String[] split = communities.split(", ");
        for (String str : split){
            if(str.startsWith(data.get("searchKey"))){
                ans.append(str).append(", ");
            }
        }
        if(ans.equals(null)){
            return "_null";
        }
        return ans.toString();
    }
}
