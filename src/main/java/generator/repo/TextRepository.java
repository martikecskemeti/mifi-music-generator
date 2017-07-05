package generator.repo;

import generator.model.Text;
import generator.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by sfanni on 7/5/17.
 */
public interface TextRepository extends CrudRepository<Text, Long> {}
