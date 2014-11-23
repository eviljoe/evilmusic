package em.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class QueueElement implements Identifiable, Cloneable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int queueIndex;
    private int playIndex;
    
    @OneToOne(fetch = FetchType.EAGER)
    private SongInfo song;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public QueueElement() {
        this(null, null);
    }
    
    public QueueElement(Integer id) {
        this(id, null);
    }
    
    public QueueElement(SongInfo song) {
        this(null, song);
    }
    
    public QueueElement(Integer id, SongInfo song) {
        super();
        setID(id);
        setSong(song);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    @Override
    public Integer getID() {
        return id;
    }
    
    @Override
    public void setID(Integer id) {
        this.id = id;
    }
    
    public int getQueueIndex() {
        return queueIndex;
    }
    
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    public int getPlayIndex() {
        return playIndex;
    }
    
    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }
    
    public SongInfo getSong() {
        return song;
    }
    
    public void setSong(SongInfo song) {
        this.song = song;
    }
    
    @Override
    public QueueElement clone() {
        QueueElement clone;
        
        try {
            clone = (QueueElement)super.clone();
        } catch(CloneNotSupportedException e) {
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.queueIndex = queueIndex;
            clone.playIndex = playIndex;
            clone.song = song == null ? null : song.clone();
        }
        
        return clone;
    }
}
