package com.ijrobotics.ijschoolmanageradministrationservice.service;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Contact;
import com.ijrobotics.ijschoolmanageradministrationservice.repository.ContactRepository;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    /**
     * Get all the contacts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContactDTO> findAll() {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll().stream()
            .map(contactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactDTO> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id)
            .map(contactMapper::toDto);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
