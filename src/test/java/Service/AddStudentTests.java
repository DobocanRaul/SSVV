package Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.StudentXMLRepository;
import service.Service;

class StudentServiceTest {

    @Mock
    private StudentXMLRepository studentXmlRepo;

    @InjectMocks
    private Service studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveStudent_SuccessfulSave_Returns1() {
        // Arrange
        Student student = new Student("123", "Alice", 101);
        when(studentXmlRepo.save(any(Student.class))).thenReturn(null);

        // Act
        int result = studentService.saveStudent("123", "Alice", 101);

        // Assert
        assertEquals(1, result, "Expected save to return 1 on success");
    }

    @Test
    void saveStudent_FailedSave_Returns0() {
        // Arrange
        Student student = new Student("124", "Nume", 102);
        when(studentXmlRepo.save(any(Student.class))).thenReturn(student);

        // Act
        int result = studentService.saveStudent("124", "Nume", 102);

        // Assert
        assertEquals(0, result, "Expected save to return 0 on failure");
    }
}
