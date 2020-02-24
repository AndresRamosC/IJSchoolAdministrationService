package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Exculpatory;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ExculpatoryRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ExculpatoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exculpatory}.
 */
@Service
@Transactional
public class ExculpatoryService {

    private final Logger log = LoggerFactory.getLogger(ExculpatoryService.class);

    private final ExculpatoryRepository exculpatoryRepository;

    private final ExculpatoryMapper exculpatoryMapper;

    public ExculpatoryService(ExculpatoryRepository exculpatoryRepository, ExculpatoryMapper exculpatoryMapper) {
        this.exculpatoryRepository = exculpatoryRepository;
        this.exculpatoryMapper = exculpatoryMapper;
    }

    /**
     * Save a exculpatory.
     *
     * @param exculpatoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ExculpatoryDTO save(ExculpatoryDTO exculpatoryDTO) {
        log.debug("Request to save Exculpatory : {}", exculpatoryDTO);
        Exculpatory exculpatory = exculpatoryMapper.toEntity(exculpatoryDTO);
        exculpatory = exculpatoryRepository.save(exculpatory);
        return exculpatoryMapper.toDto(exculpatory);
    }

    /**
     * Get all the exculpatories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExculpatoryDTO> findAll() {
        log.debug("Request to get all Exculpatories");
        return exculpatoryRepository.findAll().stream()
            .map(exculpatoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one exculpatory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExculpatoryDTO> findOne(Long id) {
        log.debug("Request to get Exculpatory : {}", id);
        return exculpatoryRepository.findById(id)
            .map(exculpatoryMapper::toDto);
    }

    /**
     * Delete the exculpatory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exculpatory : {}", id);
        exculpatoryRepository.deleteById(id);
    }
}
