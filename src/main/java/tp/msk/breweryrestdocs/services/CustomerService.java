package tp.msk.breweryrestdocs.services;

import tp.msk.breweryrestdocs.web.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID customerId);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void updateCustomerById(UUID customerId, CustomerDTO customerDTO);

    void deleteCustomerById(UUID customerId);
}
