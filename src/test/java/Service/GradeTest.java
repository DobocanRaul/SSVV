package Service;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.*;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("add_grade")
public class GradeTest {
    static Validator<Student> studentValidator = new StudentValidator();
    static Validator<Tema> temaValidator = new TemaValidator();
    static Validator<Nota> notaValidator = new NotaValidator();
    static StudentXMLRepository studentRepository = mock(StudentXMLRepository.class);
    static TemaXMLRepository temaRepository = mock(TemaXMLRepository.class);
    static NotaXMLRepository notaRepository = mock(NotaXMLRepository.class);

    Service service= new Service(studentRepository,temaRepository,notaRepository);

    @BeforeEach
    void setup()
    {

        when(studentRepository.save(new Student("120","Raul",932))).thenReturn(new Student("120","Raul",932));
        when(temaRepository.save(new Tema("100","C++ OOP",10,8))).thenReturn(new Tema("100","C++ OOP",10,8));
        //when(notaRepository.save(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"))).thenReturn(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
        when(studentRepository.findOne("1")).thenReturn(null);
        when(temaRepository.findOne("1")).thenReturn(null);
        when(studentRepository.findOne("120")).thenReturn(new Student("120","Raul",932));
        when(temaRepository.findOne("100")).thenReturn(new Tema("100","C++ OOP",10,8));
        //when(notaRepository.findOne(new Pair<String,String>("120","100"))).thenReturn(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
    }

    @Test
    public void addGradeForValidHomeworkAndStudent () {
        notaValidator.validate(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
        Assertions.assertEquals(1,service.saveNota("120","100",10,7,"some feedback"));
    }

    @Test
    public void addGradeForInvalidHomeworkAndValidStudent () {
        notaValidator.validate(new Nota(new Pair<String,String>("120","935438"),10,7,"some feedback"));
        Assertions.assertEquals(-1,service.saveNota("120","935438",10,7,"some feedback"));
    }

    @Test
    public void addGradeForValidHomeworkAndInvalidStudent () {
        notaValidator.validate(new Nota(new Pair<String,String>("1329084","100"),10,7,"some feedback"));
        Assertions.assertEquals(-1,service.saveNota("1329084","100",10,7,"some feedback"));
    }

    @Test
    public void addGradeForAlreadyGradedHomework () {
        notaValidator.validate(new Nota(new Pair<String,String>("120","100"),10,8,"some feedback"));
        Assertions.assertEquals(0,service.saveNota("120","100",10,8,"some feedback"));
        notaValidator.validate(new Nota(new Pair<String,String>("120","100"),9,9,"another feedback"));
        Assertions.assertEquals(1,service.saveNota("120","100",9,9,"another feedback"));
    }

    @Test
    public void addInvalidGrade () {
        Assertions.assertEquals(-1, service.saveNota("","",0,0,""));
    }

    @Test
    public void addGradeToHomeworkPostedWayToLateAfterDeadline () {
        Assertions.assertEquals(1,service.saveNota("120","100", 10, 13, "too late"));
    }
}
