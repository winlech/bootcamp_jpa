package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turn_status")
public class TurnStatus {

    @Id
    @Column(name = "id_turn_status")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "turnStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turn> turns = new ArrayList<>();

    public void adicionaTurn(Turn turn) {
        this.turns.add(turn);
    }

    public TurnStatus() {
    }

    public TurnStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }
}
