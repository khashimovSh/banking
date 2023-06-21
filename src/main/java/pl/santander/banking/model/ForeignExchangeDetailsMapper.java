package pl.santander.banking.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Mapper
public interface ForeignExchangeDetailsMapper {

    ForeignExchangeDetailsMapper INSTANCE = Mappers.getMapper(ForeignExchangeDetailsMapper.class);

    @Mapping(source = "id", target = "id")
    ForeignExchangeDetailsEntity toEntity(ForeignExchangeDetails foreignExchangeDetails);

    @Mapping(source = "id", target = "id")
    ForeignExchangeDetails fromEntity(ForeignExchangeDetailsEntity foreignExchangeDetailsEntity);
}
