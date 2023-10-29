package com.bit.lotte.fresh.app.rest;

import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
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
import com.bit.lotte.fresh.service.dto.response.GetAllUserResponse;
import com.bit.lotte.fresh.service.dto.response.UpdateUserResponse;
import com.bit.lotte.fresh.service.dto.response.UserAddressListResponse;
import com.bit.lotte.fresh.service.dto.response.UserDataResponse;
import com.bit.lotte.fresh.service.dto.response.UserDefaultAddressProvinceResponse;
import com.bit.lotte.fresh.service.dto.response.UserDefaultAddressResponse;
import com.bit.lotte.fresh.service.mapper.UserDataMapper;
import com.bit.lotte.fresh.service.port.input.UserApplicationService;
import com.bit.lotte.fresh.service.port.output.CreateUserEventPublisher;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.time.ZonedDateTime;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;
    private final UserDataMapper dataMapper;
    private final CreateUserEventPublisher publisher;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
        @Valid @RequestBody CreateUserCommand createUserCommand) {
        log.info("userId: " +createUserCommand.getUserId());
        CreateUserResponse response = userApplicationService.createUser(createUserCommand);
        if (response != null) {
            publisher.publish(
                new CreateUserDomainEvent(dataMapper.createCommandUserToUser(createUserCommand),
                    ZonedDateTime.now()), createUserCommand.getAuthProvider());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/me/addresses/default")
    public ResponseEntity<UserDefaultAddressResponse> getUserDefaultAddress(
        @RequestHeader Long userIdCommand) {
        return ResponseEntity.ok(userApplicationService.getDefaultAddress(userIdCommand));
    }

    @GetMapping(value = "/users/me/addresses/default/province")
    public ResponseEntity<UserDefaultAddressProvinceResponse> getUserDefaultAddressProvince(
        @RequestHeader Long userId) {
        log.info("userId: "+ userId);
        return ResponseEntity.ok(userApplicationService.getDefaultAddressProvince(new UserIdCommand(new UserId(userId))));
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<DeleteUserResponse> deleteUser(
        @RequestHeader Long userId) {
        log.info("userId:" + userId);
        return ResponseEntity.ok(
            userApplicationService.deleteUser(new UserIdCommand(new UserId(userId))));
    }

    @PutMapping("/users/me")
    public ResponseEntity<UpdateUserResponse> updateUser(
         @RequestHeader Long userId) {
        return ResponseEntity.ok(userApplicationService.updateUser(new UserIdCommand(new UserId(userId))));
    }

    @GetMapping(value = "/users/me")
    public ResponseEntity<UserDataResponse> getUser(
       @RequestHeader Long userId) {
        return ResponseEntity.ok(userApplicationService.getUser(new UserIdCommand(new UserId(userId)));
    }

    @PostMapping("/users/me/addresses")
    public ResponseEntity<AddUserAddressResponse> addAddress(
        @Valid @RequestBody AddAddressCommand addAddressCommand, @RequestHeader Long userId) {
        return ResponseEntity.ok(
            userApplicationService.addAddress(new UserIdCommand(new UserId(userId)),
                addAddressCommand));
    }

    @DeleteMapping("/users/me/addresses/{addressId}")
    public ResponseEntity<DeleteAddressResponse> deleteAddress(@RequestHeader Long userId,
        @PathVariable Long addressId) {
        return ResponseEntity.ok(
            userApplicationService.deleteAddress(new UserIdCommand(new UserId(userId)),
                new AddressIdCommand(new AddressId(addressId))));

    }

    @PutMapping("/users/me/addresses/{addressId}")
    public ResponseEntity<ChangeDefaultAddressResponse> changeDefaultAddress(
        @RequestHeader Long userId,
        @Valid @PathVariable Long addressId) {
        return ResponseEntity.ok(
            userApplicationService.updateDefaultAddress(new UserIdCommand(new UserId(userId)),
                new AddressIdCommand(new AddressId(addressId))));
    }
     @GetMapping(value = "/users/me/addresses")
    public ResponseEntity<UserAddressListResponse> getAddressList(
        @RequestHeader Long userId) {

        return ResponseEntity.ok(
            userApplicationService.getAddressList(new UserIdCommand(new UserId(userId))));
    }

}





