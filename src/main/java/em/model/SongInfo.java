package em.model;

public class SongInfo {
	
	private String artist;
	private String album;
	
	private int year;
	private int seconds; 
	
	/* ************ */
	/* Constructors */
	/* ************ */
	
	public SongInfo() {
		super();
	}

	/* ***************** */
	/* Getters / Setters */
	/* ***************** */
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
