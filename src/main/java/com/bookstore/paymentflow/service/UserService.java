package com.bookstore.paymentflow.service;

import com.bookstore.paymentflow.model.Address;
import java.util.Collection;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final Map<String, Address> addresses = Map.of(
      "home", new Address("home", "123 Main St, Dallas, TX 75201"),
      "work", new Address("work", "456 Commerce Ave, Houston, TX 77002")
  );

  public Collection<Address> getAddresses() {
    return addresses.values();
  }

  public Address getAddress(String id) {
    return addresses.get(id);
  }
}
