
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named("usercreation")
public class UserCreation implements Serializable {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    private Connection con;
    private String uname;
    private String pass;
    private String maker;

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getUname() {
        return uname;
    }

    public String getPass() {
        return pass;
    }

    public String getMaker() {
        return maker;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/User?autoReconnect=true&useSSL=false", "root", "toor");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e);
        }
        return con;
    }

    public void createuser() {
        try {
            con = getConnection();
            PreparedStatement pst = con.prepareStatement("insert into userreg(name,pass,createdby) values(?,?,?);");
            pst.setString(1, uname);
            pst.setString(2, pass);
            pst.setString(3, (String) session.getAttribute("user"));
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changepassword() {
        try {
            con = getConnection();
            PreparedStatement pst = con.prepareStatement("update userreg set pass=? where name =?;");
            pst.setString(1, uname);
            pst.setString(2, pass);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String deleteuser() {
        try {
            con = getConnection();
            PreparedStatement pst = con.prepareStatement("delete from userreg where name =?;");
            pst.setString(1, (String) session.getAttribute("user"));
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
        String u=new LoginInvoker().logout();
        return u;
    }
}
