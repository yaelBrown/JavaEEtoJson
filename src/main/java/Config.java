public class Config {

    public static String getUrl() {
        return "jdbc:mysql://localhost/movies_db?serverTimezone=UTC&useSSL=false";
    }

    public static String getUsername() {
        return "root";
    }

    public static String getPassword() {
        return "codeup";
    }

}