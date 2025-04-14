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
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;
import service.Service;

@Tag("white-box")
public class WhiteBoxAddAssignmentTests {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "testestudenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "testeteme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
    
    @Test
    public void addAssignmentWithValidParameters() {
        Assertions.assertEquals(1, service.saveTema("101","SQLInjection",10,6));
    }

    @Test
    public void addAssignmentWithInvalidID() {
        Assertions.assertEquals(0, service.saveTema("", "SQLInjection", 10, 6));
    }
}
