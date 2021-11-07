package myPack;
// Generated Nov 16, 2018 2:05:21 AM by Hibernate Tools 4.3.1


import classes.Human;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class Humandb  implements java.io.Serializable {


     private int humanid;
     private String name;
     private String color;
     private Integer size;
     private Integer posx;
     private Integer posy;
     private String time;
     private Date created;

    public Humandb() {
    }

	
    public Humandb(int humanid) {
        this.humanid = humanid;
    }
    public Humandb(int humanid, String name, String color, Integer size, Integer posx, Integer posy, String time, LocalDateTime created) {
       this.humanid = humanid;
       this.name = name;
       this.color = color;
       this.size = size;
       this.posx = posx;
       this.posy = posy;
       this.time = time;
        Date out = Date.from(created.atZone(ZoneId.systemDefault()).toInstant());
       this.created = out;
    }
    
    public Humandb(Human h){
        this.humanid = h.id;
       this.name = h.name;
       this.color = h.color;
       this.size = h.size;
       this.posx = h.PosX;
       this.posy = h.PosY;
       this.time = h.time;
        Date out = Date.from(h.created.atZone(ZoneId.systemDefault()).toInstant());
       this.created = out;
    }
   
    public int getHumanid() {
        return this.humanid;
    }
    
    public void setHumanid(int humanid) {
        this.humanid = humanid;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    public Integer getSize() {
        return this.size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getPosx() {
        return this.posx;
    }
    
    public void setPosx(Integer posx) {
        this.posx = posx;
    }
    public Integer getPosy() {
        return this.posy;
    }
    
    public void setPosy(Integer posy) {
        this.posy = posy;
    }
    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    public Date getCreated() {
        return this.created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }




}


