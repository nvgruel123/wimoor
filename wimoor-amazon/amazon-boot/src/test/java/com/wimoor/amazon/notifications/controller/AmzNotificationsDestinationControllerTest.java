package com.wimoor.amazon.notifications.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wimoor.amazon.notifications.service.IAmzNotificationsDestinationService;
import com.wimoor.common.result.Result;

@ExtendWith(MockitoExtension.class)
class AmzNotificationsDestinationControllerTest {

    @Mock
    private IAmzNotificationsDestinationService destinationService;

    @InjectMocks
    private AmzNotificationsDestinationController controller;

    @Test
    void deleteActionShouldDelegateToService() {
        String destinationId = "dest-123";

        Result<?> result = controller.deleteAction(destinationId);

        verify(destinationService).deleteDestination(destinationId);
        verifyNoMoreInteractions(destinationService);
        assertThat(Result.isSuccess(result)).isTrue();
    }
}
