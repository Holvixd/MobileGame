package fi.c5msiren;

import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

// This class acts as a controller.
@RestController
public class ScoreController {

    @Autowired
    private ScoreRepository scores;

    /*public ScoreController() {
        scores.save(new MyScore(64, "Testi01"));
    }*/
    // curl -H "Content-type: application/json" -X POST http://localhost:8080/scores -d "{\"score\": 72, \"name\": \"Testiii\"}"
    @RequestMapping(value = "/scores",  method=RequestMethod.POST)
    public void saveScore(@RequestBody MyScore s) {
        scores.save(s);
    }

    @RequestMapping(value = "/scores",  method=RequestMethod.GET)
    public Iterable<MyScore> fetchScore() {
        return scores.findAll();
    }

    @RequestMapping(value = "/scores/{id}",  method=RequestMethod.GET)
    public MyScore fetchScore(@PathVariable long id) {
        return scores.findById(id);
    }
}