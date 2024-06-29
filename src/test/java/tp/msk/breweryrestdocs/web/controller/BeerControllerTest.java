package tp.msk.breweryrestdocs.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import tp.msk.breweryrestdocs.domain.Beer;
import tp.msk.breweryrestdocs.repositories.BeerRepository;
import tp.msk.breweryrestdocs.services.BeerService;
import tp.msk.breweryrestdocs.web.model.BeerDTO;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;


import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;
    
    BeerDTO validBeerDto;

    @BeforeEach
    void setUp() {
        validBeerDto = BeerDTO.builder()
                .beerName("Beer1")
                .beerStyle("PALE_ALE")
                .upc(123123123123L)
                .build();
    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeerDto);

        mockMvc.perform(get("/api/v1/beer/{beerId}", validBeerDto.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeerDto.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")))
                .andDo(document("v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        queryParameters(
                                parameterWithName("isCold").description("Is Beer Cold Query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                fieldWithPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer")
                        )));
    }

    @Test
    void handlePost() throws Exception {
        BeerDTO beerDTO = validBeerDto;
        beerDTO.setId(null);
        BeerDTO savedBeerDto = BeerDTO.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        given(beerService.saveNewBeer(any())).willReturn(savedBeerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDTO.class);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Beer Name"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer")
                        )));
    }

    @Test
    void handleUpdate() throws Exception{
        BeerDTO beerDTO = validBeerDto;
        beerDTO.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                        .descriptionsForProperty(path), ". ")));
        }
    }
}