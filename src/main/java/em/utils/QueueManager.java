package em.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import em.model.QueuedSongInfo;
import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
public class QueueManager {
    
    private static QueueManager instance;
    
    private final ArrayList<QueuedSongInfo> queue;
    private volatile int playIndex;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private QueueManager() {
        super();
        queue = new ArrayList<>();
        setPlayIndex(0);
    }
    
    /* ******************* */
    /* Singleton Functions */
    /* ******************* */
    
    public static synchronized QueueManager getInstance() {
        if(instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }
    
    /* ***************** */
    /* Manager Functions */
    /* ***************** */
    
    public synchronized List<QueuedSongInfo> getContents() {
        final ArrayList<QueuedSongInfo> contents = new ArrayList<>();
        
        contents.addAll(queue);
        return contents;
    }
    
    public int size() {
        return queue.size();
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public synchronized void clear() {
        queue.clear();
        setPlayIndex(0);
        updateIndices();
    }
    
    public synchronized SongInfo remove(int queueIndex) {
        final Iterator<QueuedSongInfo> it = queue.iterator();
        SongInfo removed = null;
        
        while(it.hasNext() && removed == null) {
            final QueuedSongInfo info = it.next();
            
            if(info.getQueueIndex() == queueIndex) {
                it.remove();
                removed = info;
                updateIndices();
            }
        }
        
        return removed;
    }
    
    public synchronized boolean queueLast(Collection<? extends SongInfo> infos) {
        boolean altered = false;
        
        if(EMUtils.hasValues(infos)) {
            for(SongInfo info : infos) {
                altered = queueLast(info, false) || altered;
            }
            
            if(altered) {
                updateIndices();
            }
        }
        
        return altered;
    }
    
    public synchronized boolean queueLast(SongInfo info) {
        return queueLast(info, true);
    }
    
    private synchronized boolean queueLast(SongInfo info, boolean updateIndices) {
        final boolean queued = info == null ? false : queue.add(new QueuedSongInfo(info));
        
        if(queued && updateIndices) {
            updateIndices();
        }
        
        return queued;
    }
    
    public synchronized QueuedSongInfo getByQueueIndex(int queueIndex) {
        final Iterator<QueuedSongInfo> it = queue.iterator();
        QueuedSongInfo requested = null;
        
        while(it.hasNext() && requested == null) {
            final QueuedSongInfo info = it.next();
            
            if(info.getQueueIndex() == queueIndex) {
                requested = info;
            }
        }
        
        return requested;
    }
    
    private void updateIndices() {
        int qI = 0;
        int pI = Math.max(0, getPlayIndex());
        
        pI = -Math.min(pI, size() - 1);
        
        for(QueuedSongInfo info : queue) {
            info.setQueueIndex(qI++);
            info.setPlayIndex(pI++);
        }
    }
    
    public int getPlayIndex() {
        return playIndex;
    }
    
    public void setPlayIndex(int playIndex) {
        if(playIndex != this.playIndex) {
            this.playIndex = playIndex;
            updateIndices();
        }
    }
}
