package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.AttachmentsContent;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttachmentsContentRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsContentDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsContentMapper;
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
 * Service Implementation for managing {@link AttachmentsContent}.
 */
@Service
@Transactional
public class AttachmentsContentService {

    private final Logger log = LoggerFactory.getLogger(AttachmentsContentService.class);

    private final AttachmentsContentRepository attachmentsContentRepository;

    private final AttachmentsContentMapper attachmentsContentMapper;

    public AttachmentsContentService(AttachmentsContentRepository attachmentsContentRepository, AttachmentsContentMapper attachmentsContentMapper) {
        this.attachmentsContentRepository = attachmentsContentRepository;
        this.attachmentsContentMapper = attachmentsContentMapper;
    }

    /**
     * Save a attachmentsContent.
     *
     * @param attachmentsContentDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentsContentDTO save(AttachmentsContentDTO attachmentsContentDTO) {
        log.debug("Request to save AttachmentsContent : {}", attachmentsContentDTO);
        AttachmentsContent attachmentsContent = attachmentsContentMapper.toEntity(attachmentsContentDTO);
        attachmentsContent = attachmentsContentRepository.save(attachmentsContent);
        return attachmentsContentMapper.toDto(attachmentsContent);
    }

    /**
     * Get all the attachmentsContents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttachmentsContentDTO> findAll() {
        log.debug("Request to get all AttachmentsContents");
        return attachmentsContentRepository.findAll().stream()
            .map(attachmentsContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the attachmentsContents where Attachments is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AttachmentsContentDTO> findAllWhereAttachmentsIsNull() {
        log.debug("Request to get all attachmentsContents where Attachments is null");
        return StreamSupport
            .stream(attachmentsContentRepository.findAll().spliterator(), false)
            .filter(attachmentsContent -> attachmentsContent.getAttachments() == null)
            .map(attachmentsContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attachmentsContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttachmentsContentDTO> findOne(Long id) {
        log.debug("Request to get AttachmentsContent : {}", id);
        return attachmentsContentRepository.findById(id)
            .map(attachmentsContentMapper::toDto);
    }

    /**
     * Delete the attachmentsContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttachmentsContent : {}", id);
        attachmentsContentRepository.deleteById(id);
    }
}
