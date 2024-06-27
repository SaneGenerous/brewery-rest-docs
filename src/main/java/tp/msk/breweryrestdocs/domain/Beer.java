package tp.msk.breweryrestdocs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tp.msk.breweryrestdocs.web.model.v2.BeerStyleEnum;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
    private UUID id;
    private String beerName;
    private BeerStyleEnum beerStyle;
    private Long upc;

    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
