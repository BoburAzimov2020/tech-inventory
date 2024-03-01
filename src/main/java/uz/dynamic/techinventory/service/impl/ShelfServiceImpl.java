package uz.dynamic.techinventory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Shelf;
import uz.dynamic.techinventory.repository.ShelfRepository;
import uz.dynamic.techinventory.service.ShelfService;
import uz.dynamic.techinventory.service.dto.ShelfDTO;
import uz.dynamic.techinventory.service.mapper.ShelfMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Shelf}.
 */
@Service
@Transactional
public class ShelfServiceImpl implements ShelfService {

    private final Logger log = LoggerFactory.getLogger(ShelfServiceImpl.class);

    private final ShelfRepository shelfRepository;

    private final ShelfMapper shelfMapper;

    public ShelfServiceImpl(ShelfRepository shelfRepository, ShelfMapper shelfMapper) {
        this.shelfRepository = shelfRepository;
        this.shelfMapper = shelfMapper;
    }

    @Override
    public ShelfDTO save(ShelfDTO shelfDTO) {
        log.debug("Request to save Shelf : {}", shelfDTO);
        Shelf shelf = shelfMapper.toEntity(shelfDTO);
        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    @Override
    public ShelfDTO update(ShelfDTO shelfDTO) {
        log.debug("Request to update Shelf : {}", shelfDTO);
        Shelf shelf = shelfMapper.toEntity(shelfDTO);
        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    @Override
    public Optional<ShelfDTO> partialUpdate(ShelfDTO shelfDTO) {
        log.debug("Request to partially update Shelf : {}", shelfDTO);

        return shelfRepository
                .findById(shelfDTO.getId())
                .map(existingShelf -> {
                    shelfMapper.partialUpdate(existingShelf, shelfDTO);

                    return existingShelf;
                })
                .map(shelfRepository::save)
                .map(shelfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShelfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shelves");
        return shelfRepository.findAll(pageable).map(shelfMapper::toDto);
    }

    @Override
    public Page<ShelfDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all ShelfTypes");
        return shelfRepository.findAllByObyektId(pageable, obyektId).map(shelfMapper::toDto);
    }

    @Override
    public Page<ShelfDTO> findAllByShelfType(Pageable pageable, Long shelfTypeId) {
        log.debug("Request to get all Shelves");
        return shelfRepository.findAllByShelfTypeId(pageable, shelfTypeId).map(shelfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShelfDTO> findOne(Long id) {
        log.debug("Request to get Shelf : {}", id);
        return shelfRepository.findById(id).map(shelfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shelf : {}", id);
        shelfRepository.deleteById(id);
    }
}
