import java.util.HashMap;

public class UsersCommunities {
    private final HashMap<String, String> data;

    public UsersCommunities(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    String followCommunity(){
        String communities = DataBase.getSingleTone().getController("UsersFollowingCommunities").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();
        StringBuilder stringBuilder=new StringBuilder();

        for (String str : split) {
            if (str.startsWith(data.get("userId"))) {
                stringBuilder.append(str);
                stringBuilder.append(data.get("communityName")).append(", ");
                str = stringBuilder.toString();
            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("AllCommunities").writeFile(ans.toString(), true);
        return "_followCommunity";
    }

    String unFollowCommunity(){
        String communities = DataBase.getSingleTone().getController("UsersFollowingCommunities").readFile();
        String[] split = communities.split("\n");
        StringBuilder ans = new StringBuilder();

        for (String str : split) {
            if (str.startsWith(data.get("userId"))){
                String[] update = str.split(", ");
                StringBuilder change = new StringBuilder();

                if(update.length==1){
                    return "_invalid";
                }

                for (int i = 1; i < update.length; i++){
                    if(update[i].equals(data.get("communityName"))){
                        continue;
                    }
                    change.append(update[i]).append(", ");
                }

            }
            ans.append(str).append("\n");
        }
        DataBase.getSingleTone().getController("UsersFollowingCommunities").writeFile(ans.toString(), true);
        return "_unfollowCommunity";
    }
}
