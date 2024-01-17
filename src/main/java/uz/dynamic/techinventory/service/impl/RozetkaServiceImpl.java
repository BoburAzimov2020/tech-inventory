package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Rozetka;
import uz.dynamic.techinventory.repository.RozetkaRepository;
import uz.dynamic.techinventory.service.RozetkaService;
import uz.dynamic.techinventory.service.dto.RozetkaDTO;
import uz.dynamic.techinventory.service.mapper.RozetkaMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Rozetka}.
 */
@Service
@Transactional
public class RozetkaServiceImpl implements RozetkaService {

    private final Logger log = LoggerFactory.getLogger(RozetkaServiceImpl.class);

    private final RozetkaRepository rozetkaRepository;

    private final RozetkaMapper rozetkaMapper;

    public RozetkaServiceImpl(RozetkaRepository rozetkaRepository, RozetkaMapper rozetkaMapper) {
        this.rozetkaRepository = rozetkaRepository;
        this.rozetkaMapper = rozetkaMapper;
    }

    @Override
    public RozetkaDTO save(RozetkaDTO rozetkaDTO) {
        log.debug("Request to save Rozetka : {}", rozetkaDTO);
        Rozetka rozetka = rozetkaMapper.toEntity(rozetkaDTO);
        rozetka = rozetkaRepository.save(rozetka);
        return rozetkaMapper.toDto(rozetka);
    }

    @Override
    public RozetkaDTO update(RozetkaDTO rozetkaDTO) {
        log.debug("Request to update Rozetka : {}", rozetkaDTO);
        Rozetka rozetka = rozetkaMapper.toEntity(rozetkaDTO);
        rozetka = rozetkaRepository.save(rozetka);
        return rozetkaMapper.toDto(rozetka);
    }

    @Override
    public Optional<RozetkaDTO> partialUpdate(RozetkaDTO rozetkaDTO) {
        log.debug("Request to partially update Rozetka : {}", rozetkaDTO);

        return rozetkaRepository
            .findById(rozetkaDTO.getId())
            .map(existingRozetka -> {
                rozetkaMapper.partialUpdate(existingRozetka, rozetkaDTO);

                return existingRozetka;
            })
            .map(rozetkaRepository::save)
            .map(rozetkaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RozetkaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rozetkas");
        return rozetkaRepository.findAll(pageable).map(rozetkaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RozetkaDTO> findOne(Long id) {
        log.debug("Request to get Rozetka : {}", id);
        return rozetkaRepository.findById(id).map(rozetkaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rozetka : {}", id);
        rozetkaRepository.deleteById(id);
    }
}
