package com.bit.lotte.fresh.app.rest;

import com.bit.lotte.fresh.service.dto.command.AddAddressCommand;
import com.bit.lotte.fresh.service.dto.command.AddressIdCommand;
import com.bit.lotte.fresh.service.dto.command.CreateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UpdateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UserIdCommand;
import com.bit.lotte.fresh.service.dto.response.AddUserAddressResponse;
import com.bit.lotte.fresh.service.dto.response.ChangeDefaultAddressResponse;
import com.bit.lotte.fresh.service.dto.response.CreateUserResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteAddressResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteUserResponse;
import com.bit.lotte.fresh.service.dto.response.UpdateUserResponse;
import com.bit.lotte.fresh.service.dto.response.UserDataResponse;
import com.bit.lotte.fresh.service.port.input.UserApplicationService;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private final UserApplicationService userApplicationService;


    @PostMapping("/")
    public ResponseEntity<CreateUserResponse> createUser(
        @Valid CreateUserCommand createUserCommand) {
        return ResponseEntity.ok(userApplicationService.createUser(createUserCommand));

    }

    @DeleteMapping("/")
    public ResponseEntity<DeleteUserResponse> deleteUser(@Valid UserIdCommand userIdCommand) {
        return ResponseEntity.ok(userApplicationService.deleteUser(userIdCommand));
    }

    @PutMapping("/")
    public ResponseEntity<UpdateUserResponse> updateUser(
        @Valid UpdateUserCommand updateUserCommand) {
        return ResponseEntity.ok(userApplicationService.updateUser(updateUserCommand));
    }

    @GetMapping("/")
    public ResponseEntity<UserDataResponse> getUser(@Valid UserIdCommand userIdCommand) {
        return ResponseEntity.ok(userApplicationService.getUser(userIdCommand));
    }

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<AddUserAddressResponse> addAddress(@PathVariable UserId userId,
        @Valid AddAddressCommand addAddressCommand) {
        return ResponseEntity.ok(userApplicationService.addAddress(userId, addAddressCommand));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<DeleteAddressResponse> deleteAddress(@PathVariable UserId userId,
        @Valid @PathVariable AddressIdCommand addressId) {
        return ResponseEntity.ok(userApplicationService.deleteAddress(userId, addressId));

    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ChangeDefaultAddressResponse> changeDefaultAddress(
        @PathVariable UserId userId,
        @Valid @PathVariable AddressIdCommand addressId) {
        return ResponseEntity.ok(userApplicationService.updateDefaultAddress(userId, addressId));
    }

}





