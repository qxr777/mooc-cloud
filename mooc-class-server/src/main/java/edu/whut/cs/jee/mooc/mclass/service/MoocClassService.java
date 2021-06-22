package edu.whut.cs.jee.mooc.mclass.service;

import com.google.common.collect.Lists;
import edu.whut.cs.jee.mooc.common.constant.MoocClassConstatnts;
import edu.whut.cs.jee.mooc.common.exception.APIException;
import edu.whut.cs.jee.mooc.common.exception.AppCode;
import edu.whut.cs.jee.mooc.common.util.BeanConvertUtils;
import edu.whut.cs.jee.mooc.client.CourseClient;
import edu.whut.cs.jee.mooc.client.UserClient;
import edu.whut.cs.jee.mooc.mclass.dto.CourseDto;
import edu.whut.cs.jee.mooc.mclass.dto.JoinDto;
import edu.whut.cs.jee.mooc.mclass.dto.MoocClassDto;
import edu.whut.cs.jee.mooc.mclass.model.Course;
import edu.whut.cs.jee.mooc.mclass.model.MoocClass;
import edu.whut.cs.jee.mooc.mclass.repository.MoocClassRepository;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import edu.whut.cs.jee.mooc.mclass.model.Teacher;
import edu.whut.cs.jee.mooc.mclass.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "mclasses")
public class MoocClassService {

    @Autowired
    private MoocClassRepository moocClassRepository;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserClient userClient;

    /**
     * 【参考】各层命名规约: 插入的方法用 save/insert 做前缀。
     * @param moocClassDto
     * @return
     */
    @CacheEvict(key="T(String).valueOf('teacherId').concat('-').concat(#moocClassDto.teacherId)")
    public Long saveMoocClass(MoocClassDto moocClassDto) {
        CourseDto courseDto = null;
        if(moocClassDto.getOfflineCourse() != null) {
            Teacher teacher = new Teacher();
            teacher.setId(moocClassDto.getTeacherId());
            courseDto = CourseDto.builder()
                    .name(moocClassDto.getOfflineCourse())
                    .type(MoocClassConstatnts.COURSE_TYPE_OFFLINE)
                    .teacherId(moocClassDto.getTeacherId())
                    .build();
            long courseId = courseClient.save(courseDto);
            courseDto = courseClient.findById(courseId);
        }
        if(moocClassDto.getCourseId() != null) {
            courseDto = courseClient.findById(moocClassDto.getCourseId());
        }
        MoocClass moocClass = BeanConvertUtils.convertTo(moocClassDto, MoocClass::new);
        Course course = BeanConvertUtils.convertTo(courseDto, Course::new);
        moocClass.setCourse(course);
        if (moocClass.getCode() == null) {
            moocClass.setCode(this.generateMClassCode());
        }
        MoocClass saved =  moocClassRepository.save(moocClass);
        return saved.getId();
    }

    /**
     * 关联在线课程，生成慕课堂
     * @param moocClassDto
     * @return
     */
    @CacheEvict(key="T(String).valueOf('teacherId').concat('-').concat(#moocClassDto.teacherId)")
    public Long addMoocClass(MoocClassDto moocClassDto) {
        CourseDto courseDto = courseClient.findById(moocClassDto.getCourseId());
        MoocClass moocClass = BeanConvertUtils.convertTo(moocClassDto, MoocClass::new);
        Course course = BeanConvertUtils.convertTo(courseDto, Course::new);
        moocClass.setCourse(course);
        if (moocClass.getCode() == null) {
            moocClass.setCode(this.generateMClassCode());
        }
        MoocClass saved =  moocClassRepository.save(moocClass);
        return saved.getId();
    }

    private String generateMClassCode() {
        String code = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        while (this.getMoocClassByCode(code) != null) {
            code = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        }
        return code;
    }

    private MoocClass getMoocClassByCode(String code) {
        MoocClass moocClass = null;
        List<MoocClass> moocClasses = moocClassRepository.findByCode(code);
        if (moocClasses.size() > 0) {
            moocClass = moocClasses.get(0);
        }
        return moocClass;
    }
    @CacheEvict(key="T(String).valueOf('id').concat('-').concat(#moocClassDto.id)")
    public Long editMoocClass(MoocClassDto moocClassDto) {
        MoocClass moocClass = moocClassRepository.findById(moocClassDto.getId()).get();
        BeanUtils.copyProperties(moocClassDto, moocClass);
        MoocClass saved =  moocClassRepository.save(moocClass);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(key="T(String).valueOf('teacherId').concat('-').concat(#teacherId)")
    public List<MoocClassDto> getOwnMoocClasses(Long teacherId) {
        List<MoocClass> moocClasses = moocClassRepository.findByTeacher(teacherId);
        List<MoocClassDto> moocClassDtos = BeanConvertUtils.convertListTo(moocClasses, MoocClassDto::new,
                (s, t) -> {
                    t.setCourseId(s.getCourse().getId());
                    t.setCourseName(s.getCourse().getName());
                    t.setCode(s.getCode());
                    t.setTeacherId(s.getCourse().getTeacher().getId());
                    t.setTeacherName(s.getCourse().getTeacher().getName());
                });
        return moocClassDtos;
    }

    @Transactional(readOnly = true)
    @Cacheable(key="T(String).valueOf('id').concat('-').concat(#moocClassId)")
    public MoocClassDto getMoocClass(Long moocClassId) {
        MoocClass moocClass = moocClassRepository.findById(moocClassId).get();
        MoocClassDto moocClassDto = BeanConvertUtils.convertTo(moocClass, MoocClassDto::new, (s, t) -> {
            t.setCourseId(s.getCourse().getId());
            t.setCourseName(s.getCourse().getName());
            t.setCode(s.getCode());
            t.setTeacherId(s.getCourse().getTeacher().getId());
            t.setTeacherName(s.getCourse().getTeacher().getName());
        });
        return moocClassDto;
    }

    @Transactional(readOnly = true)
    @Cacheable(key="T(String).valueOf('userId').concat('-').concat(#userId)")
    public List<MoocClassDto> getJoinMoocClasses(Long userId) {
        List<BigInteger> moocClassIdList = moocClassRepository.findMoocClassIds(userId);
        List<Long> moocClassIds = Lists.newArrayList();
        moocClassIdList.stream().forEach(bigInteger ->{
            moocClassIds.add(bigInteger.longValue());
        });
        List<MoocClass> moocClasses = Lists.newArrayList(moocClassRepository.findAllById(moocClassIds));
        return BeanConvertUtils.convertListTo(moocClasses, MoocClassDto::new);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUserDtos(Long moocClassId) {
        List<BigInteger> useIdList = moocClassRepository.findUserIds(moocClassId);
        List<Long> userIds = Lists.newArrayList();
        useIdList.stream().forEach(bigInteger ->{
            userIds.add(bigInteger.longValue());
        });
        List<UserDto> users = Lists.newArrayList(userClient.findAllByIds(userIds));
        return users;
    }

    private List<UserDto> getUsers(Long moocClassId) {
        List<BigInteger> useIdList = moocClassRepository.findUserIds(moocClassId);
        List<Long> userIds = Lists.newArrayList();
        useIdList.stream().forEach(bigInteger ->{
            userIds.add(bigInteger.longValue());
        });
        List<UserDto> users = Lists.newArrayList(userClient.findAllByIds(userIds));
        return users;
    }

    public void removeMoocClass(Long moocClassId) {
        if (moocClassRepository.existsById(moocClassId)) {
            moocClassRepository.deleteById(moocClassId);
        } else {
            throw new APIException(AppCode.NO_MCLASS_ERROR, AppCode.NO_MCLASS_ERROR.getMsg() + moocClassId);
        }
    }

    /**
     * 学生加入慕课堂
     * @param joinDto
     * @return
     */
    @CacheEvict(key="T(String).valueOf('userId').concat('-').concat(#joinDto.userId)")
    public void join(JoinDto joinDto) {
        String code = joinDto.getMoocClassCode();
        MoocClass moocClass = this.getMoocClassByCode(code);
        if(moocClass == null) {
            throw new APIException(AppCode.NO_MCLASS_ERROR, code + AppCode.NO_MCLASS_ERROR.getMsg());
        }
        UserDto userDto = userClient.findById(joinDto.getUserId());
        List<UserDto> userDtos = this.getUsers(moocClass.getId());
        if (userDtos.contains(userDto)) {
            throw new APIException(AppCode.USER_HAS_JOINED_ERROR, userDto.getName() + AppCode.USER_HAS_JOINED_ERROR.getMsg());
        }
        User user = BeanConvertUtils.convertTo(userDto, User::new);
        moocClass.getUsers().add(user);
        moocClassRepository.save(moocClass);
    }

}
