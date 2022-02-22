package pti.sb_squash.model;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "useridplayerone")
	private User useridPlayerOne;
	@Column(name = "pointsplayerone")
	private int pointsPlayerOne;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="useridplayertwo")
	private User useridPlayerTwo;
	@Column(name = "pointsplayertwo")
	private int pointsPlayerTwo;
	@Column(name = "locationid")
	private int locationid;
	@Column(name = "date")
	private String date;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUseridPlayerOne() {
		return useridPlayerOne;
	}

	public void setUseridPlayerOne(User useridPlayerOne) {
		this.useridPlayerOne = useridPlayerOne;
	}

	public int getPointsPlayerOne() {
		return pointsPlayerOne;
	}

	public void setPointsPlayerOne(int pointsPlayerOne) {
		this.pointsPlayerOne = pointsPlayerOne;
	}

	public User getUseridPlayerTwo() {
		return useridPlayerTwo;
	}

	public void setUseridPlayerTwo(User useridPlayerTwo) {
		this.useridPlayerTwo = useridPlayerTwo;
	}

	public int getPointsPlayerTwo() {
		return pointsPlayerTwo;
	}

	public void setPointsPlayerTwo(int pointsPlayerTwo) {
		this.pointsPlayerTwo = pointsPlayerTwo;
	}

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Match{" +
				"id=" + id +
				", useridPlayerOne=" + useridPlayerOne +
				", pointsPlayerOne=" + pointsPlayerOne +
				", useridPlayerTwo=" + useridPlayerTwo +
				", pointsPlayerTwo=" + pointsPlayerTwo +
				", locationid=" + locationid +
				", date='" + date + '\'' +
				'}';
	}
}