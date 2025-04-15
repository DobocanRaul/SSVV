package Service;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.*;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

@Tag("BigBang")
public class BigBangIntegrationTest {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "testestudenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "testeteme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "testenote.xml");

    Service service= new Service(fileRepository1,fileRepository2,fileRepository3);

    @BeforeEach
    @AfterEach
    void setup() {
        FileUtils.clearFile("testenote.xml");
        FileUtils.clearFile("testestudenti.xml");
        FileUtils.clearFile("testeteme.xml");
    }
    @Test
    public void addStudentTest(){
        Assertions.assertEquals(1,service.saveStudent("120","Raul",932));
    }

    @Test
    public void addAssignment(){
        Assertions.assertEquals(1,service.saveTema("100","C++ OOP",10,8));
    }

    @Test
    public void addGrade(){
        service.saveStudent("1","Ana",932);
        service.saveTema("1","Some Tema",8,6);
        Assertions.assertEquals(1,service.saveNota("1","1",10,7,"some feedback"));
    }

    @Test
    public void allTests(){

        Assertions.assertEquals(1,service.saveStudent("120","Raul",932));
        Assertions.assertEquals(1,service.saveTema("100","C++ OOP",10,8));
        Assertions.assertEquals(1,service.saveNota("120","100",10,9,"some feedback"));

    }
}
