package BackEnd.dao;

import BackEnd.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sasha ins on 17.04.2017.
 */
public interface UserDao extends JpaRepository<User,Long> {



    //this interface implements methods Jpa
}
