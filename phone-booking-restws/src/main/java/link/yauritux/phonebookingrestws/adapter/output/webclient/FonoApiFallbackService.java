package link.yauritux.phonebookingrestws.adapter.output.webclient;

import link.yauritux.port.output.dto.response.PhoneSpecificationResponse;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A fallback service in case of <code>FONOAPI</code> is unavailable (and believe me, it's DOWN MOST OF THE TIME :-D ).
 * NB: <i>here we merely provide some of frequently accessed phone. Feel free to adjust this fallback according to your need.</i>
 */
@Service
public class FonoApiFallbackService {

    @Getter private final Map<String, PhoneSpecificationResponse> localStorages = new HashMap<>();

    public FonoApiFallbackService() {
        var galaxyS9 = new PhoneSpecificationResponse("Samsung", "Samsung Galaxy S9",
                "GSM / CDMA / HSPA / EVDO / LTE",
                "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM model only) CDMA 800 / 1900 - USA",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 - Global, USA CDMA2000 1xEV-DO - USA",
                """
        1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 32, 38, 39, 40, 41, 66 -
        Global 1, 2, 3, 4, 5, 7, 8, 12, 13, 14, 17, 18, 19, 20, 25, 26, 28, 29, 30, 38, 39, 40, 41, 46, 66, 71 - USA
        """);
        localStorages.put(galaxyS9.deviceName(), galaxyS9);
        var galaxyS8 = new PhoneSpecificationResponse("Samsung", "2x Samsung Galaxy S8",
                "GSM / HSPA / LTE", "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM model only)",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100",
                "1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 32, 66, 38, 39, 40, 41");
        localStorages.put(galaxyS8.deviceName(), galaxyS8);
        var nexus6 = new PhoneSpecificationResponse("Motorola", "Motorola Nexus 6",
                "GSM / CDMA / HSPA / LTE",
                "GSM 850 / 900 / 1800 / 1900 - all models CDMA 800 / 1900 - XT1103",
                """
    HSDPA 800 / 850 / 900 / 1700 / 1800 / 1900 / 2100 - XT1100 HSDPA 850 / 900 / 1700 / 1900 / 2100 -
    XT1103 HSDPA 850 / 900 / 1900 / 2100 - Verizon
    """,
                """
                        1, 3, 5, 7, 8, 9, 19, 20, 28, 41 -
                        XT1100 2, 3, 4, 5, 7, 12, 13, 17, 25, 26, 29, 41 - XT1103 4, 13 - Verizon
                        """);
        localStorages.put(nexus6.deviceName(), nexus6);
        var oneplus9 = new PhoneSpecificationResponse("Oneplus", "Oneplus 9",
                "GSM / CDMA / HSPA / LTE / 5G",
                "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 CDMA 800 / 1900",
                "HSDPA 800 / 850 / 900 / 1700(AWS) / 1800 / 1900 / 2100",
                """
                        1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 32, 38, 39, 40, 41, 66 -
                        EU 1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 30, 32, 38, 39, 40,
                        41, 46, 48, 66, 71 - NA 1, 2, 3, 4, 5, 7, 8, 12, 17, 18, 19, 20, 26, 34, 38, 39, 40, 41, 46 -
                        IN 1, 2, 3, 4, 5, 7, 8, 12, 17, 18, 19, 20, 26, 34, 38, 39, 40, 41 - CN
                        """);
        localStorages.put(oneplus9.deviceName(), oneplus9);
        var iphone13 = new PhoneSpecificationResponse("Apple iPhone13", "Apple iPhone 13",
                "GSM / CDMA / HSPA / EVDO / LTE / 5G",
                "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) CDMA 800 / 1900",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO",
                """
                        1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 30, 32, 34, 38, 39, 40,
                        41, 42, 46, 48, 66 -
                        A2633, A2634, A2635 1, 2, 3, 4, 5, 7, 8, 11, 12, 13, 14, 17, 18, 19, 20, 21, 25, 26, 28, 29,
                        30, 32, 34, 38, 39, 40, 41, 42, 46, 48, 66, 71 - A2482, A2631
                        """);
        localStorages.put(iphone13.deviceName(), iphone13);
        var iphone12 = new PhoneSpecificationResponse("Apple iPhone12", "Apple iPhone 12",
                "GSM / CDMA / HSPA / EVDO / LTE / 5G",
                "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) - for China CDMA 800 / 1900",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO",
                """
                            1, 2, 3, 4, 5, 7, 8, 12, 13, 14, 17, 18, 19, 20, 25, 26, 28, 29, 30,
                            32, 34, 38, 39, 40, 41, 42, 46, 48, 66, 71 -
                            A2172 1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 30,
                            32, 34, 38, 39, 40, 41, 42, 46, 48, 66 -
                            A2403, A2404 1, 2, 3, 4, 5, 7, 8, 12, 13, 14, 17, 18, 19, 20, 21, 25, 26, 28, 29, 30,
                            32, 34, 38, 39, 40, 41, 42, 46, 48, 66, 71 - A2402
                        """);
        localStorages.put(iphone12.deviceName(), iphone12);
        var iphone11 = new PhoneSpecificationResponse("Apple iPhone11", "Apple iPhone 11",
                "GSM / CDMA / HSPA / EVDO / LTE",
                "GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) - for China CDMA 800 / 1900",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO",
                """
                        1, 2, 3, 4, 5, 7, 8, 11, 12, 13, 17, 18, 19, 20, 21, 25, 26, 28, 29, 30,
                        32, 34, 38, 39, 40, 41, 42, 46, 48, 66 -
                        A2221 1, 2, 3, 4, 5, 7, 8, 12, 13, 14, 17, 18, 19, 20, 25, 26, 29, 30,
                        34, 38, 39, 40, 41, 42, 46, 48, 66, 71 - A2111, A2223
                        """);
        localStorages.put(iphone11.deviceName(), iphone11);
        var iphoneX = new PhoneSpecificationResponse("Apple iPhoneX", "iPhone X",
                "GSM / HSPA / LTE", "GSM 850 / 900 / 1800 / 1900",
                "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100",
                "1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19, 20, 25, 26, 28, 29, 30, 34, 38, 39, 40, 41, 66"
        );
        localStorages.put(iphoneX.deviceName(), iphoneX);
        var nokia3310 = new PhoneSpecificationResponse("Nokia", "Nokia 3310",
                "GSM", "GSM 900 / 1800", "N/A", "N/A");
        localStorages.put(nokia3310.deviceName(), nokia3310);
    }
}
