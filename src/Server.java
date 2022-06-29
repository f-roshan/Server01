import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
class Server {
    static boolean isServerUp=true;
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(1999);
            DataBase.getSingleTone().addDataBase("UsersInformation", new Controller("D:\\DataBase\\user\\UsersInformation.txt"));
            DataBase.getSingleTone().addDataBase("UsersFollowingCommunities", new Controller("D:\\DataBase\\user\\UsersFollowingCommunities.txt"));
            DataBase.getSingleTone().addDataBase("UsersPosts", new Controller("D:\\DataBase\\user\\UsersPosts.txt"));
            DataBase.getSingleTone().addDataBase("AllCommunities", new Controller("D:\\DataBase\\community\\AllCommunities.txt"));
            DataBase.getSingleTone().addDataBase("AllCommunitiesDescription", new Controller("D:\\DataBase\\community\\AllCommunitiesDescription.txt"));
            DataBase.getSingleTone().addDataBase("ThisCommunityPosts", new Controller("D:\\DataBase\\community\\ThisCommunityPosts.txt"));
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
class RequestHandler extends Thread{
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        String commend;

    }
}
