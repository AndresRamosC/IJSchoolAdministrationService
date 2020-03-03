package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;


import com.ijrobotics.ijschoolmanageradministrationservice.service.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.SubjectAmountDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.TeacherDashBoardInfoDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class TeacherDashBoardInfoResource {
    private final Logger log = LoggerFactory.getLogger(GuardianService.class);
    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceTeacherDashBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private PersonService personService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    private SubjectService subjectService;
    /**
     * Get all the information of a Guard Dashboard using the ID of keycloak.
     *
     * @return the list of entities.
     */
    @GetMapping("/TeacherDashBoard/{userName}")
    public TeacherDashBoardInfoDTO findInfo(@PathVariable String userName) {
        log.debug("Request to get all Teacher´s info");

        Optional<UserExtendDTO> teacherUserExtendDTO= userExtendService.findOneByKeycloakUserName(userName);
        //Get the user extend from the user of keycloak

        if (teacherUserExtendDTO.isPresent()){
            log.info("TeacherUserExtend*************"+teacherUserExtendDTO.toString());
            //get the person from the user extend id (the person has the contacts)
            Optional<PersonDTO> personDTO=personService.findOneWithUserId(teacherUserExtendDTO.get().getId());
            if (personDTO.isPresent()){
                //Get the Employee
                Optional<EmployeeDTO> employeeDTO = employeeService.findOneByPersonId(personDTO.get().getId());
                //Get the Teacher
                if (employeeDTO.isPresent()){
                    Optional<TeacherDTO> teacherDTO= teacherService.findOneByEmployeeId(employeeDTO.get().getId());
                    List<ClassGroupDTO> classGroupDTOList = classGroupService.findAllWhereTeacherIdOrderedBySubjectIdAndStartHour(teacherDTO.get().getId());
                    List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList=new ArrayList<>();
                    classGroupDTOList.forEach(classGroupDTO -> {
                        classGroupAndSubjectDtoList.add(new ClassGroupAndSubjectDto(classGroupDTO,subjectService.findOne(classGroupDTO.getSubjectId()).get()));
                    });
                    TeacherDashBoardInfoDTO fulldto= new TeacherDashBoardInfoDTO(teacherUserExtendDTO.get(),personDTO.get(),employeeDTO.get(),teacherDTO.get(),classGroupAndSubjectDtoList,getAmountOfGroups(classGroupDTOList));
                    return fulldto;
                }else {
                    throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
                }
            }else {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
            }
        }else {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
        }
    }

    static List<SubjectAmountDto> getAmountOfGroups(List<ClassGroupDTO> classGroupDTOList){
        Map<Long,Long> amountOfGroups = new HashMap<>();
        classGroupDTOList.forEach(classGroupDTO -> {
            if (!amountOfGroups.containsKey(classGroupDTO.getSubjectId())){
                amountOfGroups.put(classGroupDTO.getSubjectId(), 1L);
            }else {
                amountOfGroups.put(classGroupDTO.getSubjectId(),amountOfGroups.get(classGroupDTO.getSubjectId())+1);
            }
        });
        List<SubjectAmountDto> subjectAmountDtoList=new ArrayList<>();
        amountOfGroups.forEach((key,val) -> {
            subjectAmountDtoList.add(new SubjectAmountDto(key,val));
        });
        return subjectAmountDtoList;
    }
}
