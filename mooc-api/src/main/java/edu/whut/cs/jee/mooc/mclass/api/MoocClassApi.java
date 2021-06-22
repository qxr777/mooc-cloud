package edu.whut.cs.jee.mooc.mclass.api;

import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import edu.whut.cs.jee.mooc.mclass.dto.JoinDto;
import edu.whut.cs.jee.mooc.mclass.dto.MoocClassDto;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassAddVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassDetailVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassEditVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassNewVo;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qixin on 2021/6/19.
 * @version 1.0
 */
@RequestMapping("/mclass")
public interface MoocClassApi {
    @PostMapping("")
    Long save(@RequestBody @Valid MoocClassNewVo moocClassNewVo);

    @PostMapping("add")
    Long add(@RequestBody @Valid MoocClassAddVo moocClassAddVo);

    @PostMapping("join")
    String join(@RequestBody @Valid JoinDto joinDto);

    @PutMapping("")
    Long edit(@RequestBody @Valid MoocClassEditVo moocClassEditVo);

    @GetMapping(value = "own")
    List<MoocClassDto> list(@RequestParam(value = "teacherId", required = true) Long teacherId);

    @GetMapping(value = "join")
    List<MoocClassDto> listJoin(@RequestParam(value = "userId", required = true) Long userId);

    @GetMapping(value = "{id}")
    MoocClassDetailVo detail(@PathVariable Long id);

    @GetMapping(value = "{id}/users")
    List<UserDto> userList(@PathVariable Long id);

}
