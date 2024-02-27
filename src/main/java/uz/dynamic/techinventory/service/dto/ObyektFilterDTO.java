package uz.dynamic.techinventory.service.dto;

public class ObyektFilterDTO {

    private final Long regionId;
    private final Long districtId;
    private final Long objectTasnifiId;
    private final Long objectTasnifiTuriId;
    private final Long loyihaId;
    private final Long buyurtmaRaqamId;

    public ObyektFilterDTO(Long regionId, Long districtId, Long objectTasnifiId, Long objectTasnifiTuriId, Long loyihaId,
                           Long buyurtmaRaqamId) {
        this.regionId = regionId;
        this.districtId = districtId;
        this.objectTasnifiId = objectTasnifiId;
        this.objectTasnifiTuriId = objectTasnifiTuriId;
        this.loyihaId = loyihaId;
        this.buyurtmaRaqamId = buyurtmaRaqamId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public Long getObjectTasnifiId() {
        return objectTasnifiId;
    }

    public Long getObjectTasnifiTuriId() {
        return objectTasnifiTuriId;
    }

    public Long getLoyihaId() {
        return loyihaId;
    }

    public Long getBuyurtmaRaqamId() {
        return buyurtmaRaqamId;
    }
}
