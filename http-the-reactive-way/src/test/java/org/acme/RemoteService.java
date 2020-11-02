package org.acme;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class RemoteService {

    public static void main(String[] args) {

        WireMockServer server = new WireMockServer(options().port(8082).bindAddress("localhost"));
        server.start();

        configureFor("localhost", 8082);
        server.addStubMapping(stubFor(get(urlEqualTo("/greetings"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!"))));

        server.addStubMapping(stubFor(get(urlEqualTo("/delayed")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")
                        .withFixedDelay(2000))));

        server.addStubMapping(stubFor(get(urlEqualTo("/random-delay")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")
                        .withLogNormalRandomDelay(400, 0.5)))
        );

        server.addStubMapping(stubFor(get(urlEqualTo("/malformed")).willReturn(
                aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK))
        ));

        server.addStubMapping(stubFor(get(urlEqualTo("/empty")).willReturn(
                aResponse().withFault(Fault.EMPTY_RESPONSE))
        ));

        server.addStubMapping(stubFor(get(urlEqualTo("/random")).willReturn(
                aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE))
        ));

        server.addStubMapping(stubFor(get(urlEqualTo("/reset")).willReturn(
                aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER))
        ));



    }
}
