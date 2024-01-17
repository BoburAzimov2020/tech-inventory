package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class BuyurtmaRaqamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyurtmaRaqamDTO.class);
        BuyurtmaRaqamDTO buyurtmaRaqamDTO1 = new BuyurtmaRaqamDTO();
        buyurtmaRaqamDTO1.setId(1L);
        BuyurtmaRaqamDTO buyurtmaRaqamDTO2 = new BuyurtmaRaqamDTO();
        assertThat(buyurtmaRaqamDTO1).isNotEqualTo(buyurtmaRaqamDTO2);
        buyurtmaRaqamDTO2.setId(buyurtmaRaqamDTO1.getId());
        assertThat(buyurtmaRaqamDTO1).isEqualTo(buyurtmaRaqamDTO2);
        buyurtmaRaqamDTO2.setId(2L);
        assertThat(buyurtmaRaqamDTO1).isNotEqualTo(buyurtmaRaqamDTO2);
        buyurtmaRaqamDTO1.setId(null);
        assertThat(buyurtmaRaqamDTO1).isNotEqualTo(buyurtmaRaqamDTO2);
    }
}
