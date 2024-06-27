package tp.msk.breweryrestdocs.web.mappers;

import org.mapstruct.Mapper;
import tp.msk.breweryrestdocs.domain.Beer;
import tp.msk.breweryrestdocs.web.model.BeerDTO;

@Mapper
public interface BeerMapper {

    BeerDTO BeerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDTO beerDTO);
}
