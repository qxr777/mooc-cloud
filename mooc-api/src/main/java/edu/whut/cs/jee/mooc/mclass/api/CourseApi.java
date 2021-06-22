package edu.whut.cs.jee.mooc.mclass.api;

import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@RequestMapping("/course")
public interface CourseApi {

    @PostMapping("")
    Long save(@RequestBody @Valid CourseDto courseDto);

    @GetMapping(value = "/{courseId}")
    CourseDto findById(@PathVariable Long courseId);

}
