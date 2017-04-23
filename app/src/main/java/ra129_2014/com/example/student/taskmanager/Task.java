package ra129_2014.com.example.student.taskmanager;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Mia on 4/17/2017.
 */

public class Task implements Serializable {
    private String naziv;
    private String opis;
    private String vreme;
    private String datum;
    private int prioritet;
    private boolean podsetnik;
    private boolean finished;

    Task(String name,String opis, String time, String date, int priority, boolean gotov,boolean reminder ){
        naziv = name;
        opis = opis;
        vreme = time;
        datum = date;
        prioritet = priority;
        finished = gotov;
        podsetnik = reminder;
    }

    public Task(){

        naziv="";
        opis="";
        vreme="";
        datum="";
        prioritet=0;
        podsetnik=false;
        finished=false;

    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public boolean isPodsetnik() {
        return podsetnik;
    }

    public void setPodsetnik(boolean podsetnik) {
        this.podsetnik = podsetnik;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
