package Service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;


@Tag("StudentPack")
public class StudentTest{

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "testestudenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");


    @Test
    public void addStudentWithValidGroup(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(1, service.saveStudent("1", "Eduard", 932));
    }

    @Test
    public void addStudentWithInvalidGroup(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("2", "Eduard", 100));
    }

    @Test
    public void addStudentWithValidName(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(1, service.saveStudent("3", "Raul", 932));
    }

    @Test
    public void addStudentWithEmptyName(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("8", "", 932));
    }



    @Test
    public void addStudentWithNullName(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("9", null, 932));
    }



    @Test
    public void addStudentWithEmptyID(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("", "Raul", 932));
    }

    @Test
    public void addStudentWithNullID(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent(null, "Raul", 932));
    }

    @Test
    public void addStudentWithInvalidIdType(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("Da", "Raul", 932));
    }

    @Test
    public void addStudentWithValidMinimumGrupValue(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(1, service.saveStudent("4", "Eduard", 111));
    }

    @Test
    public void addStudentWithValidJustAboveMinimumGrupValue(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(1, service.saveStudent("5", "Eduard", 112));
    }

    @Test
    public void addStudentWithValidMaximumGrupValue(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(1, service.saveStudent("6", "Eduard", 937));
    }

    @Test
    public void addStudentWithInvalidJustAboveMaximumGrupValue(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Assertions.assertEquals(0, service.saveStudent("7", "Eduard", 938));
    }
}
