package com.nodo.retotecnico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "encryption.key=R4VhZzxNzz9gTs3CJ23LH0ZpCvCm74EScFsvgvtMOss=",
        "encryption.hmac-key=gO0Z1+VvgTxdqhARAM0lyHkHjrESyyiyxVuuXCdUe1Y="
})
class RetoTecnicoApplicationTests {

    @Test
    void contextLoads() {
    }

}
