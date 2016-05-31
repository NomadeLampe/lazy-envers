package fr.infodb.lazyenvers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "STUDENT")
@SequenceGenerator(name = "SEQ_STUDENT", sequenceName = "SEQ_STUDENT", allocationSize = 1)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STUDENT")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    private Set<Professor> professors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }
}
