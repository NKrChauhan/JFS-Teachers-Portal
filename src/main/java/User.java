
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

@RequestScoped
@Named("user")
public class User {

    int id;
    String name;
    int marks;
    String tname;
    ArrayList usersList;
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
// Used to establish connection  

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/User?autoReconnect=true&useSSL=false", "root", "toor");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
// Used to fetch all records  

    public ArrayList userList() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from testrec where tname=?");
            stmt.setString(1, (String) sessionMap.get("user"));
            ResultSet rs = stmt.executeQuery();
//System.out.print((String)sessionMap.get("user"));
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("sid"));
                user.setName(rs.getString("sname"));
                user.setMarks(rs.getInt("smarks"));
                user.setTname(rs.getString("tname"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }
// Used to save user record  

    public String save() {
        int result = 0;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into testrec(sname,smarks,tname) values(?,?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, marks);
            stmt.setString(3, (String) sessionMap.get("user"));
            result = stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "View";
    }
// Used to fetch record to update  

    public void edit() {
        User user = null;
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from testrec where sid = " +id);
            rs.next();
            user = new User();
            user.setId(rs.getInt("sid"));
            user.setName(rs.getString("sname"));
            user.setMarks(rs.getInt("smarks"));
            user.setTname(rs.getString("tname"));
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        sessionMap.put("edituser",(User)user);
    }
// Used to update user record  

    public void update(User u) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "update testrec set sname=?, smarks=? where sid=?");
            stmt.setString(1, u.getName());
            stmt.setInt(2, u.getMarks());
            stmt.setInt(3, u.getId());
            stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println();
        }
    }
// Used to delete user record  
    public void delete() {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from testrec where sid = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
