package edu.whut.cs.jee.mooc.mclass.controller;

import edu.whut.cs.jee.mooc.mclass.api.CourseApi;
import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import edu.whut.cs.jee.mooc.mclass.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@Slf4j
@RestController
public class CourseController implements CourseApi {

    @Autowired
    CourseService courseService;

    @Override
    public Long save(CourseDto courseDto) {
        Long courseId = courseService.saveCourse(courseDto);
        return courseId;
    }

    @Override
    public CourseDto findById(Long courseId) {
        return courseService.getCourse(courseId);
    }
}
