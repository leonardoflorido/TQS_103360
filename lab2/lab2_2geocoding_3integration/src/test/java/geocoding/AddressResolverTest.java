package geocoding;

import connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {
    @Mock
    private TqsBasicHttpClient httpClient;

    @InjectMocks
    private AddressResolver resolver;

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {
        String expectedResult = "{\n" +
                "    \"info\": {\n" +
                "        \"statuscode\": 0,\n" +
                "        \"copyright\": {\n" +
                "            \"text\": \"© 2022 MapQuest, Inc.\",\n" +
                "            \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "            \"imageAltText\": \"© 2022 MapQuest, Inc.\"\n" +
                "        },\n" +
                "        \"messages\": []\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"maxResults\": 1,\n" +
                "        \"ignoreLatLngInput\": false\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"providedLocation\": {\n" +
                "                \"latLng\": {\n" +
                "                    \"lat\": 40.633116,\n" +
                "                    \"lng\": -8.658784\n" +
                "                }\n" +
                "            },\n" +
                "            \"locations\": [\n" +
                "                {\n" +
                "                    \"street\": \"Avenida João Jacinto de Magalhães\",\n" +
                "                    \"adminArea6\": \"Aveiro\",\n" +
                "                    \"adminArea6Type\": \"Neighborhood\",\n" +
                "                    \"adminArea5\": \"Aveiro\",\n" +
                "                    \"adminArea5Type\": \"City\",\n" +
                "                    \"adminArea4\": \"Aveiro\",\n" +
                "                    \"adminArea4Type\": \"County\",\n" +
                "                    \"adminArea3\": \"\",\n" +
                "                    \"adminArea3Type\": \"State\",\n" +
                "                    \"adminArea1\": \"PT\",\n" +
                "                    \"adminArea1Type\": \"Country\",\n" +
                "                    \"postalCode\": \"3810-149\",\n" +
                "                    \"geocodeQualityCode\": \"B1AAA\",\n" +
                "                    \"geocodeQuality\": \"STREET\",\n" +
                "                    \"dragPoint\": false,\n" +
                "                    \"sideOfStreet\": \"L\",\n" +
                "                    \"linkId\": \"0\",\n" +
                "                    \"unknownInput\": \"\",\n" +
                "                    \"type\": \"s\",\n" +
                "                    \"latLng\": {\n" +
                "                        \"lat\": 40.63312,\n" +
                "                        \"lng\": -8.65873\n" +
                "                    },\n" +
                "                    \"displayLatLng\": {\n" +
                "                        \"lat\": 40.63312,\n" +
                "                        \"lng\": -8.65873\n" +
                "                    },\n" +
                "                    \"mapUrl\": \"\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //todo
        when(httpClient.doHttpGet(anyString())).thenReturn(expectedResult);

        //e.g.
        Optional<Address> result = resolver.findAddressForLocation(40.633116, -8.658784);

        //return
        assertEquals(result.get(), new Address("Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null));
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
        String expectedResult = "{\n" +
                "    \"info\": {\n" +
                "        \"statuscode\": 400,\n" +
                "        \"copyright\": {\n" +
                "            \"text\": \"© 2022 MapQuest, Inc.\",\n" +
                "            \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "            \"imageAltText\": \"© 2022 MapQuest, Inc.\"\n" +
                "        },\n" +
                "        \"messages\": [\n" +
                "            \"Illegal argument from request: Invalid LatLng specified.\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"maxResults\": 1,\n" +
                "        \"ignoreLatLngInput\": false\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"providedLocation\": {},\n" +
                "            \"locations\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //todo
        when(httpClient.doHttpGet(anyString())).thenReturn(expectedResult);

        //e.g.
        Optional<Address> result = resolver.findAddressForLocation(-300, -810);

        assertThrows(NoSuchElementException.class, result::get);
    }
}