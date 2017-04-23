package fi.c5msiren;

import javax.persistence.*;

/**
 * Class represents an Score entity in the database
 *
 * @author Miika
 * @version 2017.4.23
 * @since 1.8
 */
@Entity
public class MyScore {
	
	/* auto generated ID field for identifying entities */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	/* Attribute containing the amount of the score */
	private int score;

	/* Attribute containing the name of the who achieved the score */
	private String name;

	/**
     * Empty constructor for code requirements
     */
	public MyScore() {}

	/**
     * Constructor for the class
     *
     * @param score Score amount
     * @param name Name of the score achiever
     */
	public MyScore(int score, String name) {
		this.score = score;
		this.name = name;
	}

	/**
     * Getter method for Id
     *
     * @return current value of id
     */
	public long getId() {
		return id;
	}

	/**
     * Getter method for score amount
     *
     * @return current value of score
     */
	public int getScore() {
		return score;
	}

	/**
     * Setter method for score
     *
     * @param score Score to be set as scores amount
     */
	public void setScore(int score) {
		this.score = score;
	}

	/**
     * Getter method for name
     *
     * @return current value of name
     */
	public String getName() {
		return name;
	}

	/**
     * Setter method for name
     *
     * @param name Name to be set as scores achievers name
     */
	public void setName(String name) {
		this.name = name;
	}
}