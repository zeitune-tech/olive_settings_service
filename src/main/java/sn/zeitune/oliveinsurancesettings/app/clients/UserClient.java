package sn.zeitune.olive_insurance_administration.app.clients;

import sn.zeitune.olive_insurance_administration.app.dto.external.CreateUserRequest;


public interface UserClient {
    void createUser(CreateUserRequest request);
}
