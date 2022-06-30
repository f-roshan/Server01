import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class Server {
    static boolean isServerUp=true;
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(1999);
            DataBase.getSingleTone().addDataBase("UsersInformation", new Controller("D:\\DataBase\\user\\UsersInformation.txt"));
            DataBase.getSingleTone().addDataBase("UsersFollowingCommunities", new Controller("D:\\DataBase\\user\\UsersFollowingCommunities.txt"));
            DataBase.getSingleTone().addDataBase("UsersPostsDetails", new Controller("D:\\DataBase\\user\\UsersPostsDetails.txt"));
            DataBase.getSingleTone().addDataBase("Comments", new Controller("D:\\DataBase\\user\\Comments.txt"));
            DataBase.getSingleTone().addDataBase("AllCommunities", new Controller("D:\\DataBase\\community\\AllCommunities.txt"));
            DataBase.getSingleTone().addDataBase("AllCommunitiesDescription", new Controller("D:\\DataBase\\community\\AllCommunitiesDescription.txt"));
            while (isServerUp){
                Socket socket=serverSocket.accept();
                System.out.println("connected!");
                RequestHandler requestHandler=new RequestHandler(socket);
                requestHandler.start();
            }
        }catch (IOException e){
            System.out.println("System was not create");
        }
    }
}
class RequestHandler extends Thread {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    void writer(String write) {
        if (write != null && !write.isEmpty()) {
            try {
                dos.writeUTF(write);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println("_writingProblem");
    }

    @Override
    public void run() {
        String command = "";

        try {
            command = dis.readLine();
            System.out.println("new command received: " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (command.equals(null)) {
            command = "do nothing";
        }

        String[] split = command.split("_");
        HashMap<String, String> data;
        if (split[0].equals("user")) {
            User user;
            UsersCommunities usersCommunities;
            UsersPosts usersPosts;
            Comment comment;
            StringBuilder answer= new StringBuilder();
            switch (split[1]){
                case "signUp": {
                    data = new HashMap<>(Map.of("userId", split[2], "userEmail", split[3], "password", split[4]));
                    user = new User(data);
                    writer(user.signUp());
                    break;
                }
                case "signIn": {
                    data = new HashMap<>(
                            Map.of("userId", split[2], "password", split[3]));
                    user = new User(data);
                    writer(user.signIn());
                    break;
                }
                case "feed": {
                    data = new HashMap<>(
                            Map.of("userId",split[2])
                    );
                    user=new User(data);
                    writer(user.getFeed());
                    break;
                }
                case "postDetail": {
                    data = new HashMap<>(
                            Map.of("userId",split[2])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.getPostsDetails());
                    break;
                }
                case "postComment": {
                    data = new HashMap<>(
                            Map.of("userId",split[2])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.getPostComments());
                    break;
                }
                case "likePost":{
                    data = new HashMap<>(
                            Map.of("PostId",split[2])
                    );
                    user=new User(data);
                    writer(user.likeThisPost());
                    break;
                }
                case "dislikePost":{
                    data = new HashMap<>(
                            Map.of("PostId",split[2])
                    );
                    user=new User(data);
                    writer(user.disLikeThisPost());
                    break;
                }
                case "addComment": {
                    data = new HashMap<>(
                            Map.of("postId", split[2], "userId", split[3], "comment", split[4])
                    );
                    comment=new Comment(data);
                    writer(comment.addComment());
                    break;
                }
                case "likeComment":{
                    data = new HashMap<>(
                            Map.of("commentId",split[2])
                    );
                    user=new User(data);
                    writer(user.likeThisComment());
                    break;
                }
                case "dislikeComment": {
                    data = new HashMap<>(
                            Map.of("commentId",split[2])
                    );
                    user=new User(data);
                    writer(user.dislikeThisComment());
                    break;
                }
                case "addPost": {
                    data = new HashMap<>(
                            Map.of("userId",split[2],"communityName", split[3], "date", split[4],
                                    "title", split[5], "caption", split[5])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.addPost());
                    break;
                }
                case "profile": {
                    data = new HashMap<>(
                            Map.of("userId", split[2]));
                    user=new User(data);
                    writer(user.getAccount());
                }
                case "changeProfile": {
                    data=new HashMap<>(
                            Map.of("userId", split[2], "userEmail", split[3],"password",split[4], "newUserId",split[5], "newUserEmail", split[6], "newPassword",split[7])
                    );
                    user=new User(data);
                    if(!(split[2].equals(split[5]))){
                        user.editId();
                    }

                    if(!(split[3].equals(split[6]))){
                        user.editEmail();
                    }

                    if(!(split[4].equals(split[7]))){
                        user.editPassword();
                    }
                    break;
                }
            }}else if(split[0].equals("community")){
            Community community;
                switch (split[1]){
                    case "addCommunity": {
                      data = new HashMap<>(Map.of("communityName", split[2], "adminId", split[3], "description", split[4])
                        );
                      community=new Community(data);
                      writer(community.addCommunity());
                      break;
                    }
                    case "editName": {
                        data = new HashMap<>(Map.of("communityName", split[2], "newCommunityName", split[3])
                        );
                        community = new Community(data);
                        writer(community.editName());
                        break;
                    }
                    case "editDescription": {
                        data = new HashMap<>(Map.of("communityName", split[2], "newDescription",split[3]));
                        community = new Community(data);
                        break;
                    }
                }
        }
    }
}
