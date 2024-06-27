package tp.msk.breweryrestdocs.web.mappers;

import org.mapstruct.Mapper;
import tp.msk.breweryrestdocs.domain.Customer;
import tp.msk.breweryrestdocs.web.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    CustomerDTO CustomerToCustomerDto(Customer customer);
    Customer CustomerDtoToCustomer(CustomerDTO customerDTO);
}
