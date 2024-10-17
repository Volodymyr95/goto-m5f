package converter;

import com.codegym.jrugotom5.dto.AdvertDTO;
import com.codegym.jrugotom5.entity.Advert;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class AdvertToDtoConverter implements Converter<Advert, AdvertDTO> {
    @Override
    public AdvertDTO convert(MappingContext<Advert, AdvertDTO> context) {
        Advert advert = context.getSource();
        AdvertDTO dto = new AdvertDTO();
        dto.setId(advert.getId());
        dto.setTitle(advert.getTitle());
        dto.setDescription(advert.getDescription());
        dto.setUserCreatorId(advert.getCreatedBy().getId());
        dto.setCreatedDate(advert.getCreatedDate());
        dto.setEndDate(advert.getEndDate());
        dto.setIsActive(advert.getIsActive());
        return dto;
    }
}