package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.BuyurtmaRaqam;
import uz.dynamic.techinventory.repository.BuyurtmaRaqamRepository;
import uz.dynamic.techinventory.service.BuyurtmaRaqamService;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;
import uz.dynamic.techinventory.service.mapper.BuyurtmaRaqamMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.BuyurtmaRaqam}.
 */
@Service
@Transactional
public class BuyurtmaRaqamServiceImpl implements BuyurtmaRaqamService {

    private final Logger log = LoggerFactory.getLogger(BuyurtmaRaqamServiceImpl.class);

    private final BuyurtmaRaqamRepository buyurtmaRaqamRepository;

    private final BuyurtmaRaqamMapper buyurtmaRaqamMapper;

    public BuyurtmaRaqamServiceImpl(BuyurtmaRaqamRepository buyurtmaRaqamRepository, BuyurtmaRaqamMapper buyurtmaRaqamMapper) {
        this.buyurtmaRaqamRepository = buyurtmaRaqamRepository;
        this.buyurtmaRaqamMapper = buyurtmaRaqamMapper;
    }

    @Override
    public BuyurtmaRaqamDTO save(BuyurtmaRaqamDTO buyurtmaRaqamDTO) {
        log.debug("Request to save BuyurtmaRaqam : {}", buyurtmaRaqamDTO);
        BuyurtmaRaqam buyurtmaRaqam = buyurtmaRaqamMapper.toEntity(buyurtmaRaqamDTO);
        buyurtmaRaqam = buyurtmaRaqamRepository.save(buyurtmaRaqam);
        return buyurtmaRaqamMapper.toDto(buyurtmaRaqam);
    }

    @Override
    public BuyurtmaRaqamDTO update(BuyurtmaRaqamDTO buyurtmaRaqamDTO) {
        log.debug("Request to update BuyurtmaRaqam : {}", buyurtmaRaqamDTO);
        BuyurtmaRaqam buyurtmaRaqam = buyurtmaRaqamMapper.toEntity(buyurtmaRaqamDTO);
        buyurtmaRaqam = buyurtmaRaqamRepository.save(buyurtmaRaqam);
        return buyurtmaRaqamMapper.toDto(buyurtmaRaqam);
    }

    @Override
    public Optional<BuyurtmaRaqamDTO> partialUpdate(BuyurtmaRaqamDTO buyurtmaRaqamDTO) {
        log.debug("Request to partially update BuyurtmaRaqam : {}", buyurtmaRaqamDTO);

        return buyurtmaRaqamRepository
            .findById(buyurtmaRaqamDTO.getId())
            .map(existingBuyurtmaRaqam -> {
                buyurtmaRaqamMapper.partialUpdate(existingBuyurtmaRaqam, buyurtmaRaqamDTO);

                return existingBuyurtmaRaqam;
            })
            .map(buyurtmaRaqamRepository::save)
            .map(buyurtmaRaqamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyurtmaRaqamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BuyurtmaRaqams");
        return buyurtmaRaqamRepository.findAll(pageable).map(buyurtmaRaqamMapper::toDto);
    }

    @Override
    public Page<BuyurtmaRaqamDTO> findAllByLoyiha(Pageable pageable, Long loyihaId) {
        log.debug("Request to get all BuyurtmaRaqams");
        return buyurtmaRaqamRepository.findAllByLoyihaId(pageable, loyihaId).map(buyurtmaRaqamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyurtmaRaqamDTO> findOne(Long id) {
        log.debug("Request to get BuyurtmaRaqam : {}", id);
        return buyurtmaRaqamRepository.findById(id).map(buyurtmaRaqamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyurtmaRaqam : {}", id);
        buyurtmaRaqamRepository.deleteById(id);
    }
}
