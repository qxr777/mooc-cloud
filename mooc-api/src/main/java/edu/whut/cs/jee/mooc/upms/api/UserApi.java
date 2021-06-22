package edu.whut.cs.jee.mooc.upms.api;

import edu.whut.cs.jee.mooc.upms.dto.StudentDto;
import edu.whut.cs.jee.mooc.upms.dto.TeacherDto;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@RequestMapping("/user")
public interface UserApi {

    @GetMapping(value = "")
    List<UserDto> list();

    @GetMapping(value = "{id}")
    UserDto findById(@PathVariable Long id);

    @PostMapping
    List<UserDto> getUserByUsername(@RequestParam(value = "name", required = true) String name);

    @PostMapping(value = "/register")
    Long register(@RequestBody @Valid UserDto userToAdd);

    @PostMapping("/saveStudent")
    Long saveStudent(@RequestBody @Valid StudentDto studentDto);

    @PostMapping("/saveTeacher")
    Long saveTeacher(@RequestBody @Valid TeacherDto teacherDto);

    @DeleteMapping("/{id}")
    String delete(@PathVariable Long id);

    @PostMapping("findAllByIds")
    List<UserDto> findAllByIds(@RequestParam(value = "ids", required = true) List<Long> ids);
}
