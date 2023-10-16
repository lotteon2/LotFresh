package com.bit.lotte.fresh.app.rest;

import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.service.UserApplicationServiceImpl;
import com.bit.lotte.fresh.service.UserCommandHandler;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
        @Valid @RequestBody CreateUserCommand createUserCommand) {
        log.info("userId: " + createUserCommand.getUserId());
        return ResponseEntity.ok(userApplicationService.createUser(createUserCommand));

    }

    @DeleteMapping("/users")
    public ResponseEntity<DeleteUserResponse> deleteUser(@Valid @RequestBody UserIdCommand userIdCommand) {
        log.info("userId:" + userIdCommand.getUserId());
        return ResponseEntity.ok(
            userApplicationService.deleteUser(userIdCommand));
    }

    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> updateUser(
        @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        return ResponseEntity.ok(userApplicationService.updateUser(updateUserCommand));
    }

    @GetMapping("/")
    public ResponseEntity<UserDataResponse> getUser(@Valid UserIdCommand userIdCommand) {
        return ResponseEntity.ok(userApplicationService.getUser(userIdCommand));
    }

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<AddUserAddressResponse> addAddress(@PathVariable UserIdCommand userId,
        @Valid AddAddressCommand addAddressCommand) {
        return ResponseEntity.ok(userApplicationService.addAddress(userId, addAddressCommand));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<DeleteAddressResponse> deleteAddress(@PathVariable UserIdCommand userId,
        @Valid @PathVariable AddressIdCommand addressId) {
        return ResponseEntity.ok(userApplicationService.deleteAddress(userId, addressId));

    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ChangeDefaultAddressResponse> changeDefaultAddress(
        @PathVariable UserIdCommand userId,
        @Valid @PathVariable AddressIdCommand addressId) {
        return ResponseEntity.ok(userApplicationService.updateDefaultAddress(userId, addressId));
    }

}





