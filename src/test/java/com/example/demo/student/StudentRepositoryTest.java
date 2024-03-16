package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
   @AfterEach
   void tearDown(){
       studentRepository.deleteAll();
   }
    @Test
    void itShouldCheckIfStudentExistsEmail(){
        //given
        var email = "jamila@gmail.com";
        Student student = new Student(
                "Jamila",
                email,
                Gender.FEMALE);
        studentRepository.save(student);
        //when
        boolean expected = studentRepository.selectExistsEmail(email);
        //then
        assertThat(expected).isTrue();

    }
    @Test
    void itShouldCheckIfStudentExistsEmailDoesNotExist(){
        //given
        var email = "jamila@gmail.com";
        //when
        boolean expected = studentRepository.selectExistsEmail(email);
        //then
        assertThat(expected).isFalse();

    }
}