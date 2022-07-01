import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
            DataBase.getSingleTone().addDataBase("NamesOfCommunities", new Controller("D:\\DataBase\\community\\NamesOfCommunities.txt"));
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
                dos.writeBytes(write);
                dos.flush();

            } catch (IOException e) {
                try {
                    dos.close();
                    dis.close();
                    socket.close();
                }catch (IOException o){
                    o.printStackTrace();
                }
            }
            return;
        }
        System.out.println("_writingProblem");
    }

    @Override
    public void run() {
        String command = "";

        try {
            StringBuilder request=new StringBuilder();
            int c= dis.read();
            while ((char)c != ','){
                request.append((char) c);
            }
            Scanner sc = new Scanner(request.toString());
            command = sc.nextLine();
            System.out.println("new command received: " + command);
        } catch (IOException e) {
            try {
                dos.close();
                dis.close();
                socket.close();
            }catch (IOException o){
                o.printStackTrace();
            }
        }
        if (command.equals(null)) {
            command = "other-";
        }

        String[] split = command.split("-");
        HashMap<String, String> data;
        if (split[0].equals("user")) {
            User user;
            UsersCommunities usersCommunities;
            UsersPosts usersPosts;
            Comment comment;
            Community community;
            switch (split[0]){
                case "signUp": {
                    data = new HashMap<>(Map.of("userId", split[1], "userEmail", split[2], "password", split[3]));
                    user = new User(data);
                    writer(user.signUp());
                    break;
                }
                case "signIn": {
                    data = new HashMap<>(
                            Map.of("userId", split[1], "password", split[2]));
                    user = new User(data);
                    writer(user.signIn());
                    break;
                }
                case "feed": {
                    data = new HashMap<>(
                            Map.of("userId",split[1])
                    );
                    user=new User(data);
                    writer(user.getFeed());
                    break;
                }
                case "postDetail": {
                    data = new HashMap<>(
                            Map.of("userId",split[1])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.getPostsDetails());
                    break;
                }
                case "postComment": {
                    data = new HashMap<>(
                            Map.of("userId",split[1])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.getPostComments());
                    break;
                }
                case "likePost":{
                    data = new HashMap<>(
                            Map.of("PostId",split[1])
                    );
                    user=new User(data);
                    writer(user.likeThisPost());
                    break;
                }
                case "dislikePost":{
                    data = new HashMap<>(
                            Map.of("PostId",split[1])
                    );
                    user=new User(data);
                    writer(user.disLikeThisPost());
                    break;
                }
                case "addComment": {
                    data = new HashMap<>(
                            Map.of("postId", split[1], "userId", split[2], "comment", split[3])
                    );
                    comment=new Comment(data);
                    writer(comment.addComment());
                    break;
                }
                case "likeComment":{
                    data = new HashMap<>(
                            Map.of("commentId",split[1])
                    );
                    user=new User(data);
                    writer(user.likeThisComment());
                    break;
                }
                case "dislikeComment": {
                    data = new HashMap<>(
                            Map.of("commentId",split[1])
                    );
                    user=new User(data);
                    writer(user.dislikeThisComment());
                    break;
                }
                case "addPost": {
                    data = new HashMap<>(
                            Map.of("userId",split[1],"communityName", split[2], "date", split[3],
                                    "title", split[4], "caption", split[5])
                    );
                    usersPosts=new UsersPosts(data);
                    writer(usersPosts.addPost());
                    break;
                }
                case "profile": {
                    data = new HashMap<>(
                            Map.of("userId", split[1]));
                    user=new User(data);
                    writer(user.getAccount());
                }
                case "changeProfile": {
                    data=new HashMap<>(
                            Map.of("userId", split[1], "userEmail", split[2],"password",split[3], "newUserId",split[4], "newUserEmail", split[5], "newPassword",split[7])
                    );
                    user=new User(data);
                    if(!(split[1].equals(split[4]))){
                        user.editId();
                    }

                    if(!(split[2].equals(split[5]))){
                        user.editEmail();
                    }

                    if(!(split[3].equals(split[6]))){
                        user.editPassword();
                    }
                    break;
                }
                    case "addCommunity": {
                      data = new HashMap<>(Map.of("communityName", split[1], "adminId", split[2], "description", split[3])
                        );
                      community=new Community(data);
                      writer(community.addCommunity());
                      break;
                    }
                    case "editName": {
                        data = new HashMap<>(Map.of("communityName", split[1], "newCommunityName", split[2])
                        );
                        community = new Community(data);
                        writer(community.editName());
                        break;
                    }
                    case "editDescription": {
                        data = new HashMap<>(Map.of("communityName", split[1], "newDescription",split[2]));
                        community = new Community(data);
                        break;
                    }
                }
        }
        if (split[0].equals("Other")) {
            System.out.println("other");
        }
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
