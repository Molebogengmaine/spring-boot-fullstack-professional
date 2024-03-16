package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Java6Assertions.assertThat;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService studentService;
   // private AutoCloseable autoCloseable;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setup(){
        //autoCloseable = MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository);
    }
  /*  @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();//allows us to close the resource after a test
    }*/
    @Test
    void canGetAllStudents() {
        //when
        studentService.getAllStudents();
        //then
        verify(studentRepository).findAll();//we verify that indeed findAll() was called
    }

    @Test
    void addStudent() {
        //given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE);
        //when
        studentService.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());//indeed the repository was
        // invoked with save() and also have captured the same student
        Student capturedStudent = studentArgumentCaptor.getValue();//captured student is the one the service received
        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE);
        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining( "Email " + student.getEmail() + " taken");

        verify(studentRepository,never()).save(any());
    }

    @Test
    @Disabled
    void willThrowStudentNotFound() {
        //given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE);
        //then
        assertThatThrownBy(() ->  studentService.deleteStudent(student.getId()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists");
        verify(studentRepository,never()).deleteById(anyLong());
    }
    @Test
    @Disabled
    void deleteStudent() {
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE);
        studentRepository.deleteById(student.getId());
        assertThat(studentRepository.count()).isEqualTo(0);
    }
}