package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class AkumulatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkumulatorDTO.class);
        AkumulatorDTO akumulatorDTO1 = new AkumulatorDTO();
        akumulatorDTO1.setId(1L);
        AkumulatorDTO akumulatorDTO2 = new AkumulatorDTO();
        assertThat(akumulatorDTO1).isNotEqualTo(akumulatorDTO2);
        akumulatorDTO2.setId(akumulatorDTO1.getId());
        assertThat(akumulatorDTO1).isEqualTo(akumulatorDTO2);
        akumulatorDTO2.setId(2L);
        assertThat(akumulatorDTO1).isNotEqualTo(akumulatorDTO2);
        akumulatorDTO1.setId(null);
        assertThat(akumulatorDTO1).isNotEqualTo(akumulatorDTO2);
    }
}
