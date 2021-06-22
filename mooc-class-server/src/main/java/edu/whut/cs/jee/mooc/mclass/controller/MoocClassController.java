package edu.whut.cs.jee.mooc.mclass.controller;

import edu.whut.cs.jee.mooc.common.util.BeanConvertUtils;
import edu.whut.cs.jee.mooc.mclass.api.MoocClassApi;
import edu.whut.cs.jee.mooc.mclass.dto.JoinDto;
import edu.whut.cs.jee.mooc.mclass.dto.MoocClassDto;
import edu.whut.cs.jee.mooc.mclass.service.MoocClassService;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassAddVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassDetailVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassEditVo;
import edu.whut.cs.jee.mooc.mclass.vo.MoocClassNewVo;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mclass")
public class MoocClassController implements MoocClassApi {

    @Autowired
    private MoocClassService moocClassService;

    public Long save(MoocClassNewVo moocClassNewVo) {
        MoocClassDto moocClassDto = BeanConvertUtils.convertTo(moocClassNewVo, MoocClassDto::new);
        return moocClassService.saveMoocClass(moocClassDto);
    }

    public Long add(MoocClassAddVo moocClassAddVo) {
        MoocClassDto moocClassDto = BeanConvertUtils.convertTo(moocClassAddVo, MoocClassDto::new);
        return moocClassService.addMoocClass(moocClassDto);
    }

    public String join(JoinDto joinDto) {
        moocClassService.join(joinDto);
        return "success";
    }

    public Long edit(MoocClassEditVo moocClassEditVo) {
        return moocClassService.editMoocClass(BeanConvertUtils.convertTo(moocClassEditVo, MoocClassDto::new));
    }

    public List<MoocClassDto> list(Long teacherId) {
        return moocClassService.getOwnMoocClasses(teacherId);
    }

    public List<MoocClassDto> listJoin(Long userId) {
        return moocClassService.getJoinMoocClasses(userId);
    }

    public MoocClassDetailVo detail(Long id) {
        MoocClassDto moocClassDto = moocClassService.getMoocClass(id);
        return BeanConvertUtils.convertTo(moocClassDto, MoocClassDetailVo::new);
    }

    public List<UserDto> userList(Long id) {
        return moocClassService.getUserDtos(id);
    }

}
