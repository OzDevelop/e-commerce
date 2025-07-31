package backend.e_commerce.representaion.request.user;

import backend.e_commerce.application.command.user.RegisterUserCommand.AddressCommand;

public record AddressRequestDto(String address, String addressDetail, String zipcode, boolean isDefault) {
    public AddressCommand toCommand() {
        return new AddressCommand(address, addressDetail, zipcode, isDefault);
    }
}

