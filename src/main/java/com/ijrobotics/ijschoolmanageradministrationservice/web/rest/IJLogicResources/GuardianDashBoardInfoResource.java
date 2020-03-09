package com.ijrobotics.ijschoolmanageradministrationservice.web.rest.IJLogicResources;


import com.ijrobotics.ijschoolmanageradministrationservice.service.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.GuardianDashBoardInfoDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.StudentAndPersonDto;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class GuardianDashBoardInfoResource {
    private final Logger log = LoggerFactory.getLogger(GuardianDashBoardInfoResource.class);
    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceGuardianDashBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private PersonService personService;
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    private SubjectService subjectService;

    /**
     * Get all the information of a Guard Dashboard using the ID of keycloak.
     *
     * @return the list of entities.
     */
    @GetMapping("/GuardianDashBoard/{userName}")
    public GuardianDashBoardInfoDTO findInfo(@PathVariable String userName) { //GuardianDashBoardInfoDto findInfo() {
            Optional<PersonDTO> personDTO=personService.findOneWithUserId(userName);
            if (personDTO.isPresent()){
                //Get the Guardian
                Optional<GuardianDTO> guardianDTO= guardianService.findOneByPersonId(personDTO.get().getId());
                //Get the Students
                List<StudentDTO> studentDTOList=studentService.findStudentsWithGuardian(guardianDTO.get().getId());
                List<StudentAndPersonDto> studentAndPersonDTOList = new ArrayList<>();
                studentDTOList.forEach(studentDTO -> {
                   List<ClassGroupDTO>  classGroupDTOList=classGroupService.findAllWhereStudentIdOrderedByStartHour(studentDTO.getId());

                   List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList=new ArrayList<>();
                   classGroupDTOList.forEach(classGroupDTO -> {
                       classGroupAndSubjectDtoList.add(new ClassGroupAndSubjectDto(classGroupDTO,subjectService.findOne(classGroupDTO.getSubjectId()).get()));
                   });
                    studentAndPersonDTOList.add(new StudentAndPersonDto(studentDTO,personService.findOne(studentDTO.getPersonId()).get(),classGroupAndSubjectDtoList));
                });
                GuardianDashBoardInfoDTO fulldto= new GuardianDashBoardInfoDTO(personDTO.get(),guardianDTO.get(),studentAndPersonDTOList);
                return fulldto;
            }else {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
            }

    }
}


