package edu.whut.cs.jee.mooc.mclass.service;

import edu.whut.cs.jee.mooc.common.exception.APIException;
import edu.whut.cs.jee.mooc.common.exception.AppCode;
import edu.whut.cs.jee.mooc.common.util.BeanConvertUtils;
import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import edu.whut.cs.jee.mooc.mclass.model.Course;
import edu.whut.cs.jee.mooc.mclass.model.Teacher;
import edu.whut.cs.jee.mooc.mclass.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "courses")
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    @Cacheable(key="T(String).valueOf('id').concat('-').concat(#courseId)")
    public CourseDto getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        CourseDto courseDto = BeanConvertUtils.convertTo(course, CourseDto::new, (s, t) -> {
            t.setTeacherId(s.getTeacher().getId());
        });
        return courseDto;
    }

//    @CacheEvict(key="T(String).valueOf('id').concat('-').concat(#courseDto.id)")
    public Long saveCourse(CourseDto courseDto) {
        Course course = BeanConvertUtils.convertTo(courseDto, Course::new);
        Teacher teacher = new Teacher();
        teacher.setId(courseDto.getTeacherId());
        course.setTeacher(teacher);
        course = courseRepository.save(course);
        return course.getId();
    }

    @CacheEvict(key="T(String).valueOf('id').concat('-').concat(#courseId)")
    public void removeCourse(Long courseId) {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        } else {
            throw new APIException(AppCode.NO_COURSE_ERROR, AppCode.NO_COURSE_ERROR.getMsg() + courseId);
        }
    }
}
