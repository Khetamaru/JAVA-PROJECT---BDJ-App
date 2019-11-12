package fr.intech.javaproject.bdeujapi;

public class Login {

    private String log;
    private String password;

    public Login() {

    }
    public Login(String _log, String _password) {

        log = _log;
        password = _password;
    }

    public String getLog() {
        return log;
    }
    public String getPassword() {
        return password;
    }


    public void setLog(String log) {
        this.log = log;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
