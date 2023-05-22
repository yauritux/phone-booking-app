package link.yauritux.phonebookingrestws.usecase;

import link.yauritux.domain.service.BookingEntryDomainService;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.PhoneDocumentRepository;
import link.yauritux.port.input.dto.request.BookingEntryRequest;
import link.yauritux.port.input.dto.response.BookingEntryResponse;
import link.yauritux.port.input.service.PhoneServicePort;
import link.yauritux.port.input.service.UserServicePort;
import link.yauritux.port.output.repository.BookingEntryRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Service
public class BookingPhoneService extends BookingEntryDomainService {

    private final PhoneDocumentRepository phoneDocumentRepository;

    public BookingPhoneService(
            @Autowired
            @Qualifier("bookingEntryRepository")
            BookingEntryRepositoryPort bookingEntryRepositoryPort,
            @Autowired
            @Qualifier("phoneServicePort")
            PhoneServicePort phoneServicePort,
            @Autowired
            @Qualifier("userServicePort")
            UserServicePort userServicePort,
            PhoneDocumentRepository phoneDocumentRepository) {
        super(bookingEntryRepositoryPort, phoneServicePort, userServicePort);
        this.phoneDocumentRepository = phoneDocumentRepository;
    }

    @Override
    @Transactional
    public Mono<BookingEntryResponse> bookingPhone(BookingEntryRequest request) {
        return super.bookingPhone(request).map(response -> {
            phoneDocumentRepository.findById(response.getPhoneId()).subscribe(p -> {
                p.setAvailable(false);
                phoneDocumentRepository.save(p).subscribe();
            });
            return response;
        });
    }

    @Override
    @Transactional
    public Mono<BookingEntryResponse> returnPhone(String bookingId) {
        return super.returnPhone(bookingId).map(response -> {
            phoneDocumentRepository.findById(response.getPhoneId()).subscribe(p -> {
                p.setAvailable(true);
                phoneDocumentRepository.save(p).subscribe();
            });
            return response;
        });
    }
}
