package Service;


import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("incremental")
public class IncrementalIntegrationTest
{

    static Validator<Student> studentValidator = new StudentValidator();
    static Validator<Tema> temaValidator = new TemaValidator();
    static Validator<Nota> notaValidator = new NotaValidator();
    static StudentXMLRepository studentRepository = mock(StudentXMLRepository.class);
    static TemaXMLRepository temaRepository = mock(TemaXMLRepository.class);
    static NotaXMLRepository notaRepository = mock(NotaXMLRepository.class);

    Service service= new Service(studentRepository,temaRepository,notaRepository);
    @BeforeAll
    static void setup()
    {

        when(studentRepository.save(new Student("120","Raul",932))).thenReturn(new Student("120","Raul",932));
        when(temaRepository.save(new Tema("100","C++ OOP",10,8))).thenReturn(new Tema("100","C++ OOP",10,8));
        when(notaRepository.save(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"))).thenReturn(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
        when(studentRepository.findOne("1")).thenReturn(null);
        when(temaRepository.findOne("1")).thenReturn(null);
        when(studentRepository.findOne("120")).thenReturn(new Student("120","Raul",932));
        when(temaRepository.findOne("100")).thenReturn(new Tema("100","C++ OOP",10,8));
        when(notaRepository.findOne(new Pair<String,String>("120","100"))).thenReturn(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
    }
    @Test
    public void addStudentTest()
    {

        studentValidator.validate(new Student("120","Raul",932));
        Assertions.assertEquals(1,service.saveStudent("120","Raul",932));
    }

    @Test
    public void addAssignmentTest()
    {
        studentValidator.validate(new Student("120","Raul",932));
        Assertions.assertEquals(1,service.saveStudent("120","Raul",932));
        temaValidator.validate(new Tema("100","C++ OOP",10,8));
        Assertions.assertEquals(1,service.saveTema("100","C++ OOP",10,8));
    }

    @Test
    public void addGradeTest()
    {
        studentValidator.validate(new Student("120","Raul",932));
        Assertions.assertEquals(1,service.saveStudent("120","Raul",932));
        temaValidator.validate(new Tema("100","C++ OOP",10,8));
        Assertions.assertEquals(1,service.saveTema("100","C++ OOP",10,8));

        //Wrong ids
        Assertions.assertEquals(-1,service.saveNota("1","1",10,7,"some feedback"));

        notaValidator.validate(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
        //Correct ids
        Assertions.assertEquals(1,service.saveNota("120","100",10,7,"some feedback"));
    }


}
