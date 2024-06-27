package tp.msk.breweryrestdocs.web.controller.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tp.msk.breweryrestdocs.services.v2.BeerServiceV2;
import tp.msk.breweryrestdocs.web.model.BeerDTO;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {
    private final BeerServiceV2 beerServiceV2;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeer(@NotNull @PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createNewBeer(@Valid @NotNull @RequestBody BeerDTO beerDTO){

        log.debug("In the handle create new Beer (POST)....");

        val savedBeer = beerServiceV2.saveNewBeer(beerDTO);
        var headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v2/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handleUpdateBeer(@PathVariable UUID beerId,@Valid @RequestBody BeerDTO beerDTO){
        beerServiceV2.updateBeer(beerId, beerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable UUID beerId){
        beerServiceV2.deleteBeerById(beerId);
    }

}
