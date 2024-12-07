package com.example.demo.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
       return userRepository.findAll();
    }

    public void addNewUser(User student){
        Optional<User> studentOptional = userRepository.findUserByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw  new IllegalStateException("email taken");
        }
        userRepository.save(student);
    }

    public void deleteUser(Long studentId){
        boolean exists = userRepository.existsById(studentId);

        if(!exists){
            throw new IllegalStateException("student with id" + studentId + "does not exists");
        }
        userRepository.deleteById(studentId);
    }

    @Transactional
    public void updateUser(Long studentId, String name, String email){
        User student = userRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "student with id " + studentId + "  does not exists"
        ));

        if(name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)){
            Optional<User> studentOptional = userRepository.findUserByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
