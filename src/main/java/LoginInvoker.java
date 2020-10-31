
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named("LoginInvoker")
public class LoginInvoker implements Serializable {

    private String name, pass;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

    public String val() {
        if (LoginVal.validate(name, pass)) {
            session.setAttribute("user", name);
            session.setAttribute("loggedin", true);
            System.out.print("success");
            return "View";
        } else {
            session.setAttribute("loggedin", false);
            System.out.print("failed");
            return "index";
        }
    }

    public String logout() {
        if ((boolean) session.getAttribute("loggedin")) {
            session.invalidate();
            return "index";
        } else {
            return "View";
        }
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
