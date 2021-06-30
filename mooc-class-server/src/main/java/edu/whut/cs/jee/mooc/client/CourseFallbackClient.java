package edu.whut.cs.jee.mooc.client;

import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author qixin on 2021/6/29.
 * @version 1.0
 */
@Component
@RequestMapping("/fallback")
public class CourseFallbackClient implements CourseClient {
    @Override
    public Long save(@Valid CourseDto courseDto) {
        return -1L;
    }

    @Override
    public CourseDto findById(Long courseId) {
        return null;
    }
}
