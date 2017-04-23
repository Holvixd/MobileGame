package fi.c5msiren;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Interface for scores repository
 *
 * @author Miika
 * @version 2017.4.12
 * @since 1.8
 */
public interface ScoreRepository extends CrudRepository<MyScore, Long> {

	/**
     * Method for finding score with id
     *
     * @param id Value of the id to find
     */
    MyScore findById(long id);
}