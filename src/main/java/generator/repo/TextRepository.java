package generator.repo;

import generator.model.Text;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sfanni on 7/5/17.
 */
public interface TextRepository extends CrudRepository<Text, Long> {}
