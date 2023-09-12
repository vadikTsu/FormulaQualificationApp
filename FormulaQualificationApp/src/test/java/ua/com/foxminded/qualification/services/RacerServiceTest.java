package ua.com.foxminded.qualification.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.qualification.dao.RacerDao;

@ExtendWith(MockitoExtension.class)
class RacerServiceTest {

    @Mock
    RacerDao mockRacerDao;
    @InjectMocks
    private RacerService racerService;

    @Test
    public void testConstructorParameters() {
        //TODO add required tests when modifying the RaceService class.
    }
}
