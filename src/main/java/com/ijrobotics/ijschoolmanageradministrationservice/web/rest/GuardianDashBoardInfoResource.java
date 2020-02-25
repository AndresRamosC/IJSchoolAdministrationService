package com.ijrobotics.ijschoolmanageradministrationservice.web.rest;


import com.ijrobotics.ijschoolmanageradministrationservice.service.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.*;
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
    private final Logger log = LoggerFactory.getLogger(GuardianService.class);
    private static final String ENTITY_NAME = "ijSchoolManagerAdministrationServiceGuardianDashBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private  UserExtendService userExtendService;
    @Autowired
    private PersonService personService;
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private StudentService studentService;

    /**
     * Get all the information of a Guard Dashboard using the ID of keycloak.
     *
     * @return the list of entities.
     */
    @GetMapping("/GuardianDashBoard/{userName}")
    public GuardianDashBoardInfoDto  findInfo(@PathVariable String userName) { //GuardianDashBoardInfoDto findInfo() {
        log.debug("Request to get all Guardians");

        Optional<UserExtendDTO> guardianUserExtendDTO= userExtendService.findOneByKeycloakUserName(userName);
        //Get the user extend from the user of keycloak

        if (guardianUserExtendDTO.isPresent()){
            log.info("GuardianUserExtend*************"+guardianUserExtendDTO.toString());
            //get the person from the user extend id (the person has the contacts)
            Optional<PersonDTO> personDTO=personService.findOneWithUserId(guardianUserExtendDTO.get().getId());
            if (personDTO.isPresent()){
                //Get the Guardian
                Optional<GuardianDTO> guardianDTO= guardianService.findOneByPersonId(personDTO.get().getId());
                //Get the Students
                List<StudentDTO> studentDTOList=studentService.findStudentsWithGuardian(guardianDTO.get().getId());
                List<StudentAndPersonDto> studentAndPersonDTOList = new ArrayList<>();
                studentDTOList.forEach(studentDTO -> {
                    studentAndPersonDTOList.add(new StudentAndPersonDto(studentDTO,personService.findOne(studentDTO.getPersonId()).get()));
                });
                GuardianDashBoardInfoDto fulldto= new GuardianDashBoardInfoDto(guardianUserExtendDTO.get(),personDTO.get(),guardianDTO.get(),studentAndPersonDTOList);
                log.info("************************FULL DTO:   "+fulldto.toString());
                return fulldto;
            }else {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
            }
        }else {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, " ");
        }



    }
}


