package fr.infodb.lazyenvers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "PROFESSOR")
@SequenceGenerator(name = "SEQ_PROFESSOR", sequenceName = "SEQ_PROFESSOR", allocationSize = 1)
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFESSOR")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "professor_student",
            joinColumns = @JoinColumn(name = "professor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private Set<Student> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
