package fr.infodb.lazyenvers;

import fr.infodb.lazyenvers.entities.Professor;
import fr.infodb.lazyenvers.entities.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class TestCase {

    @Autowired
    private EntityManagerFactory factory;

    @Test
    public void myTest() {

        final Professor professor = new Professor();
        final Student student = new Student();
        professor.getStudents().add(student);
        student.getProfessors().add(professor);

        createInitialData(professor, student);

        deleteData(professor.getId(), student.getId());
    }

    private void createInitialData(Professor professor, Student student) {
        final EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(professor);
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    private void deleteData(long professorId, long studentId) {
        final EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Professor professor = em.find(Professor.class, professorId);
        em.remove(professor);
        Student student = em.find(Student.class, studentId);
        em.remove(student);
        em.getTransaction().commit();
        em.close();
    }
}
