package entities;

import javax.persistence.*;

@Entity
@Table(name = "diarys")
public class Diary {

    @Id
    @Column(name = "id_diary")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String start_time;
    private String ending_time;

    @ManyToOne
    private Dentist dentist;

    public Diary() {
    }

    public Diary(String start_time, String ending_time, Dentist dentist) {
        this.start_time = start_time;
        this.ending_time = ending_time;
        this.dentist = dentist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnding_time() {
        return ending_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }
}
