package tp.msk.breweryrestdocs.web.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerDtoV2 {
    private UUID id;
    private String beerName;
    private tp.msk.breweryrestdocs.web.model.v2.BeerStyleEnum beerStyle;
    private Long upc;
}
