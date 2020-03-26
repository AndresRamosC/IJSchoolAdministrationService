package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryAttachmentsRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttachmentsDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttachmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ExculpatoryAttachments}.
 */
@Service
@Transactional
public class ExculpatoryAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryAttachmentsService.class);

    private final ExculpatoryAttachmentsRepository exculpatoryAttachmentsRepository;

    private final ExculpatoryAttachmentsMapper exculpatoryAttachmentsMapper;

    public ExculpatoryAttachmentsService(ExculpatoryAttachmentsRepository exculpatoryAttachmentsRepository, ExculpatoryAttachmentsMapper exculpatoryAttachmentsMapper) {
        this.exculpatoryAttachmentsRepository = exculpatoryAttachmentsRepository;
        this.exculpatoryAttachmentsMapper = exculpatoryAttachmentsMapper;
    }

    /**
     * Save a exculpatoryAttachments.
     *
     * @param exculpatoryAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public ExculpatoryAttachmentsDTO save(ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO) {
        log.debug("Request to save ExculpatoryAttachments : {}", exculpatoryAttachmentsDTO);
        ExculpatoryAttachments exculpatoryAttachments = exculpatoryAttachmentsMapper.toEntity(exculpatoryAttachmentsDTO);
        exculpatoryAttachments = exculpatoryAttachmentsRepository.save(exculpatoryAttachments);
        return exculpatoryAttachmentsMapper.toDto(exculpatoryAttachments);
    }

    /**
     * Get all the exculpatoryAttachments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExculpatoryAttachmentsDTO> findAll() {
        log.debug("Request to get all ExculpatoryAttachments");
        return exculpatoryAttachmentsRepository.findAll().stream()
            .map(exculpatoryAttachmentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one exculpatoryAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExculpatoryAttachmentsDTO> findOne(Long id) {
        log.debug("Request to get ExculpatoryAttachments : {}", id);
        return exculpatoryAttachmentsRepository.findById(id)
            .map(exculpatoryAttachmentsMapper::toDto);
    }

    /**
     * Delete the exculpatoryAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExculpatoryAttachments : {}", id);
        exculpatoryAttachmentsRepository.deleteById(id);
    }
}
