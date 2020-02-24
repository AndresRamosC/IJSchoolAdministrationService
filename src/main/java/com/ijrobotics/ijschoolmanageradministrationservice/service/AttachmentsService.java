package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.AttachmentsRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.AttachmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Attachments}.
 */
@Service
@Transactional
public class AttachmentsService {

    private final Logger log = LoggerFactory.getLogger(AttachmentsService.class);

    private final AttachmentsRepository attachmentsRepository;

    private final AttachmentsMapper attachmentsMapper;

    public AttachmentsService(AttachmentsRepository attachmentsRepository, AttachmentsMapper attachmentsMapper) {
        this.attachmentsRepository = attachmentsRepository;
        this.attachmentsMapper = attachmentsMapper;
    }

    /**
     * Save a attachments.
     *
     * @param attachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentsDTO save(AttachmentsDTO attachmentsDTO) {
        log.debug("Request to save Attachments : {}", attachmentsDTO);
        Attachments attachments = attachmentsMapper.toEntity(attachmentsDTO);
        attachments = attachmentsRepository.save(attachments);
        return attachmentsMapper.toDto(attachments);
    }

    /**
     * Get all the attachments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttachmentsDTO> findAll() {
        log.debug("Request to get all Attachments");
        return attachmentsRepository.findAll().stream()
            .map(attachmentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttachmentsDTO> findOne(Long id) {
        log.debug("Request to get Attachments : {}", id);
        return attachmentsRepository.findById(id)
            .map(attachmentsMapper::toDto);
    }

    /**
     * Delete the attachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attachments : {}", id);
        attachmentsRepository.deleteById(id);
    }
}
