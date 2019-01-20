package pl.coderslab.app;

import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        User user = new User("Kubuś", "kubus123@gmail.com", "qweasd", new UserGroup(1,"WAR_JEE_S_15"));

        try (Connection connection = DbUtil.getConnection()){
//            user.saveToDB(connection);

            User loadedUser = User.loadUserById(connection,1);
            loadedUser.setUserName("KubusPoPrzejsciach");
            loadedUser.saveToDB(connection);



//            System.out.println(loadedUser);

//            List<User> users = User.loadAllUsers(connection);
//            for (User thisUser : users) {
//                System.out.println(thisUser);
//            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
