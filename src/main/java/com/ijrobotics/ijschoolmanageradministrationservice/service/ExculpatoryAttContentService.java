package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttContent;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryAttContentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttContentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryAttContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ExculpatoryAttContent}.
 */
@Service
@Transactional
public class ExculpatoryAttContentService {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryAttContentService.class);

    private final ExculpatoryAttContentRepository exculpatoryAttContentRepository;

    private final ExculpatoryAttContentMapper exculpatoryAttContentMapper;

    public ExculpatoryAttContentService(ExculpatoryAttContentRepository exculpatoryAttContentRepository, ExculpatoryAttContentMapper exculpatoryAttContentMapper) {
        this.exculpatoryAttContentRepository = exculpatoryAttContentRepository;
        this.exculpatoryAttContentMapper = exculpatoryAttContentMapper;
    }

    /**
     * Save a exculpatoryAttContent.
     *
     * @param exculpatoryAttContentDTO the entity to save.
     * @return the persisted entity.
     */
    public ExculpatoryAttContentDTO save(ExculpatoryAttContentDTO exculpatoryAttContentDTO) {
        log.debug("Request to save ExculpatoryAttContent : {}", exculpatoryAttContentDTO);
        ExculpatoryAttContent exculpatoryAttContent = exculpatoryAttContentMapper.toEntity(exculpatoryAttContentDTO);
        exculpatoryAttContent = exculpatoryAttContentRepository.save(exculpatoryAttContent);
        return exculpatoryAttContentMapper.toDto(exculpatoryAttContent);
    }

    /**
     * Get all the exculpatoryAttContents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExculpatoryAttContentDTO> findAll() {
        log.debug("Request to get all ExculpatoryAttContents");
        return exculpatoryAttContentRepository.findAll().stream()
            .map(exculpatoryAttContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the exculpatoryAttContents where ExculpatoryAttachments is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ExculpatoryAttContentDTO> findAllWhereExculpatoryAttachmentsIsNull() {
        log.debug("Request to get all exculpatoryAttContents where ExculpatoryAttachments is null");
        return StreamSupport
            .stream(exculpatoryAttContentRepository.findAll().spliterator(), false)
            .filter(exculpatoryAttContent -> exculpatoryAttContent.getExculpatoryAttachments() == null)
            .map(exculpatoryAttContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one exculpatoryAttContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExculpatoryAttContentDTO> findOne(Long id) {
        log.debug("Request to get ExculpatoryAttContent : {}", id);
        return exculpatoryAttContentRepository.findById(id)
            .map(exculpatoryAttContentMapper::toDto);
    }

    /**
     * Delete the exculpatoryAttContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExculpatoryAttContent : {}", id);
        exculpatoryAttContentRepository.deleteById(id);
    }
}
