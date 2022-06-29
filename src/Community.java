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
        return "We already have this Community";
    }

    String addCommunity() {
        String userId = doWeHaveThisCommunity();
        if (userId.equals("We already have this Community")){
            return "invalid name";
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(data.get("communityName")
                +", "+ data.get("admin") + ", "+DataBase.communityCounter + ", \n");
        DataBase.getSingleTone().getController("AllCommunitiesDescription").writeFile(data.get("communityName")
                +", "+data.get("description")+", \n");
        return "valid";
    }

    String getName() {
        return doWeHaveThisCommunity().equals("We already have this Community") ? "invalid" : doWeHaveThisCommunity().split(", ")[0];
    }

    String getAdmin() {
        return doWeHaveThisCommunity().equals("We already have this Community") ? "invalid" : doWeHaveThisCommunity().split(", ")[1];
    }

    String editName() {
        String communities = DataBase.getSingleTone().getController("AllCommunities").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.startsWith(data.get("newName"))){
                return "invalid";
            }
        }

        for (String str : split) {
            if (str.startsWith(data.get("communityName"))) {
                String[] update = str.split(", ");
                update[0] = data.get("newName");
                StringBuilder change = new StringBuilder();
                for (int i = 0; i < update.length; i++) {
                    change.append(update[i]).append(", ");
                }
                str = change.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(ans.toString(), true);
        return "valid";
    }

    String editDescription() {
        String communities = DataBase.getSingleTone().getController("AllCommunitiesDescription").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("communityName"))) {
                str = data.get("communityName") + ", " + data.get("newDescription");
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunitiesDescriptions").writeFile(ans.toString(), true);
        return "valid";
    }

    String deletePost(){
        String posts = DataBase.getSingleTone().getController("UsersPostsDetails").readFile();
        String[] split = posts.split("\n");
        StringBuilder ans = new StringBuilder();
        for (String str : split) {
            if (str.startsWith(data.get("id"))) {
              continue;
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunitiesDescriptions").writeFile(ans.toString(), true);
        return "delete Post is done";
    }

}
