package hu.xannosz.tyho;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Db db = new Db();
        db.getData();
        server.createServer(db.getConfiguration().getPort());
    }
}
