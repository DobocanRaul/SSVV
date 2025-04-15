package Service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.io.FileWriter;
import java.io.IOException;

@Tag("white-box")
public class WhiteBoxAddAssignmentTests {

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "testestudenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "testnou.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    public static void clearFile(String filename) {
        try (FileWriter fw = new FileWriter(filename, false)) {
            // false = do not append, overwrite the file
            fw.write(""); // Write nothing to effectively clear the file
        } catch (IOException e) {
            System.out.println("Eroare la stergerea continutului fisierului: " + e.getMessage());
        }
    }

    @BeforeEach
    void setup() {
        FileUtils.clearFile("testnou.xml");
    }

    @Test
    public void addAssignmentWithValidParameters() {
        Assertions.assertEquals(1, service.saveTema("101","SQLInjection",10,6));
    }

    @Test
    public void addAssignmentWithInvalidID() {
        Assertions.assertEquals(0, service.saveTema("", "SQLInjection", 10, 6));
    }

    @Test
    public void addAssignmentWithNullID() {
        Tema tema = new Tema(null, "SQLInjection", 10, 6);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAssignmentWithNullDescription() {
        Tema tema = new Tema("102", null, 10, 6);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAssignmentWithEmptyDescription() {
        Tema tema = new Tema("103", "", 10, 6);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAsignmentWithLowerStartline() {
        Tema tema = new Tema("104", "description", 10, -1);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAssignmentWithLowerEndlineThanStartline() {
        Tema tema = new Tema("106", "description", 10, 11);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAssignmentWithHigherEndline() {
        Tema tema = new Tema("106", "SQLInjection", 17, 6);
        Assertions.assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addAssignmentAlreadyInDatabase() {
        Assertions.assertEquals(0, service.saveTema("101", "SQLInjection", 10, 6));
    }
}
