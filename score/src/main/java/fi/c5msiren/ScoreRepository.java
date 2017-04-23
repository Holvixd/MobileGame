package fi.c5msiren;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ScoreRepository extends CrudRepository<MyScore, Long> {
    MyScore findById(long id);
}