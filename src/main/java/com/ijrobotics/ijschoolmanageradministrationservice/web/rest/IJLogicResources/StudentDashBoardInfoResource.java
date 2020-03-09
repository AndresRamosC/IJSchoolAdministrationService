package com.ijrobotics.ijschoolmanageradministrationservice.web.rest.IJLogicResources;


import com.ijrobotics.ijschoolmanageradministrationservice.service.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.ClassGroupAndSubjectDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.StudentAndPersonDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.StudentDashBoardInfoDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;
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
public class StudentDashBoardInfoResource {
    private final Logger log = LoggerFactory.getLogger(StudentDashBoardInfoResource.class);
    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceStudentDashBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private PersonService personService;
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
    @GetMapping("/StudentDashBoard/{userName}")
    public StudentDashBoardInfoDto findInfo(@PathVariable String userName) { //GuardianDashBoardInfoDto findInfo() {

            //get the person from the user extend id (the person has the contacts)
            Optional<PersonDTO> personDTO=personService.findOneWithUserId(userName);
            if (personDTO.isPresent()){
                //Get the Guardian
                Optional<StudentDTO> studentDTO=studentService.findOneByPersonId(personDTO.get().getId());
                    if (studentDTO.isPresent()){
                        List<ClassGroupDTO>  classGroupDTOList=classGroupService.findAllWhereStudentIdOrderedByStartHour(studentDTO.get().getId());

                        List<ClassGroupAndSubjectDto> classGroupAndSubjectDtoList=new ArrayList<>();
                        classGroupDTOList.forEach(classGroupDTO -> {
                            classGroupAndSubjectDtoList.add(new ClassGroupAndSubjectDto(classGroupDTO,subjectService.findOne(classGroupDTO.getSubjectId()).get()));
                        });



                        return new StudentDashBoardInfoDto(new StudentAndPersonDto(studentDTO.get(),personService.findOne(studentDTO.get().getPersonId()).get(),classGroupAndSubjectDtoList));
                    }else{
                        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
                    }
            }else {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
            }
    }
}
