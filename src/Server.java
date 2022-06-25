import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
class Server {
    static boolean isServerUp=true;
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(1991);





            while (isServerUp){
                Socket socket=serverSocket.accept();
                System.out.println("connected!");
                RequestHandeler requestHandeler=new RequestHandeler(socket);
                requestHandeler.start();
            }
        }catch (IOException e){
            System.out.println("System was not create");
        }
    }
}


class RequestHandeler extends Thread{
    Socket socket;
    public RequestHandeler(Socket socket) {
        this.socket=socket;
    }
}
