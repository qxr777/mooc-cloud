package edu.whut.cs.jee.mooc.mclass.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto implements Serializable {

    private Long id;

    private Long teacherId;

    private Integer type;

    @NotNull(message = "课程名称不允许为空")
    private String name;

}
