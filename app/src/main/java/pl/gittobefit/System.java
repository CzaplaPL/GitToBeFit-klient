package pl.gittobefit;


import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.user.User;

/***
 * klasa do usunięcia
 * bedzie dosepna do konca springu po nowym roku
 * miej więcej do 11.01.2021
 * @author Czapla
 * @deprecated
 */
public class System
{
    /**
     * @deprecated
     */
    public final static ConnectionToServer conect = new ConnectionToServer("https://77.55.236.227:8443");
    /**
     * @deprecated
     */
    public final static User user = new User();
}
