package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.UserExtend;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.UserExtendRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.UserExtendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link UserExtend}.
 */
@Service
@Transactional
public class UserExtendService {

    private final Logger log = LoggerFactory.getLogger(UserExtendService.class);

    private final UserExtendRepository userExtendRepository;

    private final UserExtendMapper userExtendMapper;

    public UserExtendService(UserExtendRepository userExtendRepository, UserExtendMapper userExtendMapper) {
        this.userExtendRepository = userExtendRepository;
        this.userExtendMapper = userExtendMapper;
    }

    /**
     * Save a userExtend.
     *
     * @param userExtendDTO the entity to save.
     * @return the persisted entity.
     */
    public UserExtendDTO save(UserExtendDTO userExtendDTO) {
        log.debug("Request to save UserExtend : {}", userExtendDTO);
        UserExtend userExtend = userExtendMapper.toEntity(userExtendDTO);
        userExtend = userExtendRepository.save(userExtend);
        return userExtendMapper.toDto(userExtend);
    }

    /**
     * Get all the userExtends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExtendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtends");
        return userExtendRepository.findAll(pageable)
            .map(userExtendMapper::toDto);
    }


    /**
     *  Get all the userExtends where Person is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtendDTO> findAllWherePersonIsNull() {
        log.debug("Request to get all userExtends where Person is null");
        return StreamSupport
            .stream(userExtendRepository.findAll().spliterator(), false)
            .filter(userExtend -> userExtend.getPerson() == null)
            .map(userExtendMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userExtend by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExtendDTO> findOne(Long id) {
        log.debug("Request to get UserExtend : {}", id);
        return userExtendRepository.findById(id)
            .map(userExtendMapper::toDto);
    }
    /**
     * Get one userExtend by KeycloakUserName.
     *
     * @param keycloakUserName the userName of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExtendDTO> findOneByKeycloakUserName(String keycloakUserName) {
        log.debug("Request to get UserExtend : {}", keycloakUserName);
        return userExtendRepository.findByKeycloakUserId(keycloakUserName)
            .map(userExtendMapper::toDto);
    }


    /**
     * Delete the userExtend by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExtend : {}", id);
        userExtendRepository.deleteById(id);
    }
}
