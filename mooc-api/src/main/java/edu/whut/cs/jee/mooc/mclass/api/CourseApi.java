package edu.whut.cs.jee.mooc.mclass.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
//    @HystrixCommand        //没有具体指明就使用全局的
    Long save(@RequestBody @Valid CourseDto courseDto);

    @GetMapping(value = "/{courseId}")
//    @HystrixCommand        //没有具体指明就使用全局的
    CourseDto findById(@PathVariable Long courseId);

}
