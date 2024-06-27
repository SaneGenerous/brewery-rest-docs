package tp.msk.breweryrestdocs.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tp.msk.breweryrestdocs.services.BeerService;
import tp.msk.breweryrestdocs.web.model.BeerDTO;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Ice")
                .beerStyle("Lemonade")
                .build();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Glass")
                .beerStyle("Pommes")
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beerDTO) {
        //todo - impl beer update
        log.debug("Updating......");
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        log.debug("Deleting a beer.....");
    }
}
