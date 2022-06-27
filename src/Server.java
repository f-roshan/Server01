import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
class Server {
    static boolean isServerUp=true;
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(1991);
            DataBase.getSingleTone().addDataBase("UsersInformation", new Controller("D:\\DataBase\\Client\\UsersInformation.txt"));
            DataBase.getSingleTone().addDataBase("UsersInformation", new Controller("D:\\DataBase\\Client\\UserCommunities.txt"));
            while (isServerUp){
                Socket socket=serverSocket.accept();
                System.out.println("connected!");
                RequestHandler requestHandeler=new RequestHandler(socket);
                requestHandeler.start();
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
