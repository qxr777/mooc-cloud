package edu.whut.cs.jee.mooc.client;

import edu.whut.cs.jee.mooc.mclass.api.CourseApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@FeignClient(value = "course-server")
public interface CourseClient extends CourseApi {
}
