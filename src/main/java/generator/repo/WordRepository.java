package generator.repo;

import generator.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by sfanni on 7/5/17.
 */
public interface WordRepository extends CrudRepository<Word, Long> {

    @Query("SELECT w FROM Word w WHERE w.text.id=:id")
    List<Word> findByTextId(@Param("id") Long id);

}
