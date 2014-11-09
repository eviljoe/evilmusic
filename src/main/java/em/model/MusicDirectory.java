package em.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class MusicDirectory implements Identifiable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(unique = true)
    private File directory;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public MusicDirectory() {
        this((File)null);
    }
    
    public MusicDirectory(String directory) {
        this(directory == null ? null : new File(directory));
    }
    
    public MusicDirectory(File directory) {
        super();
        setDirectory(directory);
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
    
    public File getDirectory() {
        return directory;
    }
    
    public void setDirectory(File directory) {
        this.directory = directory;
    }
    
}
