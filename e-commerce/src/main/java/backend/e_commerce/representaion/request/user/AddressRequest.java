package backend.e_commerce.representaion.request.user;

import backend.e_commerce.application.command.user.RegisterUserCommand.AddressCommand;

public record AddressRequest(String address, String addressDetail, String zipcode, boolean isDefault) {
    public AddressCommand toCommand() {
        return new AddressCommand(address, addressDetail, zipcode, isDefault);
    }
}

