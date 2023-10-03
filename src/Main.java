import Helper.DBConnecter;

public class Main {
    public static void main(String[] args) {
        DBConnecter.getConnection();
        System.out.println("DB OPEN");
        DBConnecter.closeConnection();
        System.out.println("DB CLOSE");
    }
}

