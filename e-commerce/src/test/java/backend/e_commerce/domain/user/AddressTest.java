package backend.e_commerce.domain.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void 주소_생성_테스트() {
        // given
        Long id = 1L;
        Long userId = 10L;
        String address = "경상남도 거제시";
        String addressDetail = "101동 202호";
        String zipCode = "06235";
        boolean isDefault = true;

        // when
        Address addressObj = new Address(id, userId, address, addressDetail, zipCode, isDefault);

        // then
        assertEquals(id, addressObj.getId());
        assertEquals(userId, addressObj.getUserId());
        assertEquals(address, addressObj.getAddress());
        assertEquals(addressDetail, addressObj.getAddressDetail());
        assertEquals(zipCode, addressObj.getZipCode());
        assertTrue(addressObj.isDefault());
    }
}