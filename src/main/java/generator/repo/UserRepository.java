package generator.repo;

import generator.model.User;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by sfanni on 7/5/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {}
