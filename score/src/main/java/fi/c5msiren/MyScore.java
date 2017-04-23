package fi.c5msiren;

import javax.persistence.*;

@Entity
public class MyScore {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int score;
	private String name;

	public MyScore() {}

	public MyScore(int score, String name) {
		this.score = score;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}