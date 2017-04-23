package fi.c5msiren;

import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class acts as a controller for the scores
 *
 * @author Miika
 * @version 2017.4.23
 * @since 1.8
 */
@RestController
public class ScoreController {

    /**
     * Database attribute containing scores
     */   
    @Autowired
    private ScoreRepository scores;

    // curl -H "Content-type: application/json" -X POST http://localhost:8080/scores -d "{\"score\": 72, \"name\": \"Testiii\"}"
    /**
     * Method to save a score to the database
     * 
     * @param s Score to be added to database
     */
    @RequestMapping(value = "/scores",  method=RequestMethod.POST)
    public void saveScore(@RequestBody MyScore s) {
        scores.save(s);
    }

    /**
     * Method to delete a score from the database
     * 
     * @param id Id of score to delete
     */
    @RequestMapping(value = "/scores/{id}",  method=RequestMethod.DELETE)
    public void deleteScore(@PathVariable long id) {
        scores.delete(id);
    }

    /**
     * Method to fetch all scores from the database
     */
    @RequestMapping(value = "/scores",  method=RequestMethod.GET)
    public Iterable<MyScore> fetchScore() {
        return scores.findAll();
    }

    /**
     * Method to fetch a score from the database
     * 
     * @param id Id of score to fetch
     */
    @RequestMapping(value = "/scores/{id}",  method=RequestMethod.GET)
    public MyScore fetchScore(@PathVariable long id) {
        return scores.findById(id);
    }
}