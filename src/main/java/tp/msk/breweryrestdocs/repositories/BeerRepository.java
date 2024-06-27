package tp.msk.breweryrestdocs.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tp.msk.breweryrestdocs.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID>, CrudRepository<Beer, UUID> {
}
