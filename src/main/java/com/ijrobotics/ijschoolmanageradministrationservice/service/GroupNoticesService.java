package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.GroupNotices;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.GroupNoticesRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GroupNoticesDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.GroupNoticesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GroupNotices}.
 */
@Service
@Transactional
public class GroupNoticesService {

    private final Logger log = LoggerFactory.getLogger(GroupNoticesService.class);

    private final GroupNoticesRepository groupNoticesRepository;

    private final GroupNoticesMapper groupNoticesMapper;

    public GroupNoticesService(GroupNoticesRepository groupNoticesRepository, GroupNoticesMapper groupNoticesMapper) {
        this.groupNoticesRepository = groupNoticesRepository;
        this.groupNoticesMapper = groupNoticesMapper;
    }

    /**
     * Save a groupNotices.
     *
     * @param groupNoticesDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupNoticesDTO save(GroupNoticesDTO groupNoticesDTO) {
        log.debug("Request to save GroupNotices : {}", groupNoticesDTO);
        GroupNotices groupNotices = groupNoticesMapper.toEntity(groupNoticesDTO);
        groupNotices = groupNoticesRepository.save(groupNotices);
        return groupNoticesMapper.toDto(groupNotices);
    }

    /**
     * Get all the groupNotices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GroupNoticesDTO> findAll() {
        log.debug("Request to get all GroupNotices");
        return groupNoticesRepository.findAll().stream()
            .map(groupNoticesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one groupNotices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GroupNoticesDTO> findOne(Long id) {
        log.debug("Request to get GroupNotices : {}", id);
        return groupNoticesRepository.findById(id)
            .map(groupNoticesMapper::toDto);
    }

    /**
     * Delete the groupNotices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupNotices : {}", id);
        groupNoticesRepository.deleteById(id);
    }
}
