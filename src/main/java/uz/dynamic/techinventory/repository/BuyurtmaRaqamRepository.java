package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.BuyurtmaRaqam;

/**
 * Spring Data JPA repository for the BuyurtmaRaqam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyurtmaRaqamRepository extends JpaRepository<BuyurtmaRaqam, Long> {}
