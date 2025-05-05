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
    @Mock
    static Service service = mock(Service.class);

    static Validator<Student> studentValidator = new StudentValidator();
    static Validator<Tema> temaValidator = new TemaValidator();
    static Validator<Nota> notaValidator = new NotaValidator();
    @BeforeAll
    static void setup()
    {

        when(service.saveStudent("120","Raul",932)).thenReturn(1);
        when(service.saveTema("100","C++ OOP",10,8)).thenReturn(1);
        when(service.saveNota("120","100",10,7,"some feedback")).thenReturn(1);
        when(service.saveNota("1","1",10,7,"some feedback")).thenReturn(0);
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
        Assertions.assertEquals(0,service.saveNota("1","1",10,7,"some feedback"));

        notaValidator.validate(new Nota(new Pair<String,String>("120","100"),10,7,"some feedback"));
        //Correct ids
        Assertions.assertEquals(1,service.saveNota("120","100",10,7,"some feedback"));
    }


}
