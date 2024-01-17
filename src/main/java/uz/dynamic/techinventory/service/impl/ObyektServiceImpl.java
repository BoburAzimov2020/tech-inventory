package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.repository.ObyektRepository;
import uz.dynamic.techinventory.service.ObyektService;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.mapper.ObyektMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Obyekt}.
 */
@Service
@Transactional
public class ObyektServiceImpl implements ObyektService {

    private final Logger log = LoggerFactory.getLogger(ObyektServiceImpl.class);

    private final ObyektRepository obyektRepository;

    private final ObyektMapper obyektMapper;

    public ObyektServiceImpl(ObyektRepository obyektRepository, ObyektMapper obyektMapper) {
        this.obyektRepository = obyektRepository;
        this.obyektMapper = obyektMapper;
    }

    @Override
    public ObyektDTO save(ObyektDTO obyektDTO) {
        log.debug("Request to save Obyekt : {}", obyektDTO);
        Obyekt obyekt = obyektMapper.toEntity(obyektDTO);
        obyekt = obyektRepository.save(obyekt);
        return obyektMapper.toDto(obyekt);
    }

    @Override
    public ObyektDTO update(ObyektDTO obyektDTO) {
        log.debug("Request to update Obyekt : {}", obyektDTO);
        Obyekt obyekt = obyektMapper.toEntity(obyektDTO);
        obyekt = obyektRepository.save(obyekt);
        return obyektMapper.toDto(obyekt);
    }

    @Override
    public Optional<ObyektDTO> partialUpdate(ObyektDTO obyektDTO) {
        log.debug("Request to partially update Obyekt : {}", obyektDTO);

        return obyektRepository
            .findById(obyektDTO.getId())
            .map(existingObyekt -> {
                obyektMapper.partialUpdate(existingObyekt, obyektDTO);

                return existingObyekt;
            })
            .map(obyektRepository::save)
            .map(obyektMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObyektDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Obyekts");
        return obyektRepository.findAll(pageable).map(obyektMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObyektDTO> findOne(Long id) {
        log.debug("Request to get Obyekt : {}", id);
        return obyektRepository.findById(id).map(obyektMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Obyekt : {}", id);
        obyektRepository.deleteById(id);
    }
}
