package edu.whut.cs.jee.mooc.upms.controller;

import edu.whut.cs.jee.mooc.common.constant.AppConstants;
//import edu.whut.cs.jee.mooc.handler.NotControllerResponseAdvice;
import edu.whut.cs.jee.mooc.upms.api.UserApi;
import edu.whut.cs.jee.mooc.upms.dto.RoleDto;
import edu.whut.cs.jee.mooc.upms.dto.StudentDto;
import edu.whut.cs.jee.mooc.upms.dto.TeacherDto;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import edu.whut.cs.jee.mooc.upms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class UserController implements UserApi {
    @Autowired
    private UserService userService;

    public List<UserDto> list() {
        log.info("UserController: info");
        log.error("User Controller : error");
        return userService.getAllUsers();
    }

    public UserDto findById(Long id) {
        return userService.getUser(id);
    }

    public List<UserDto> getUserByUsername(String name) {
        return userService.getUserByUsername(name);
    }

    public Long register(UserDto userToAdd) {
        return userService.register(userToAdd);
    }

    public Long saveStudent(StudentDto studentDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = studentDto.getPassword();
        studentDto.setPassword(encoder.encode(rawPassword));
        RoleDto roleDto = new RoleDto(AppConstants.ROLE_STUDENT_ID);
        studentDto.addRole(roleDto);
        return userService.saveUser(studentDto);
    }

    public Long saveTeacher(TeacherDto teacherDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = teacherDto.getPassword();
        teacherDto.setPassword(encoder.encode(rawPassword));
        if (teacherDto.getRoles().size() == 0) {
            RoleDto roleDto = new RoleDto(AppConstants.ROLE_TEACHER_ID);
            teacherDto.addRole(roleDto);
        }
        return userService.saveUser(teacherDto);
    }

    public String delete(Long id) {
        userService.removeUser(id);
        return "success";
    }

    public List<UserDto> findAllByIds(List<Long> ids) {
        return userService.findAllById(ids);
    }

}
