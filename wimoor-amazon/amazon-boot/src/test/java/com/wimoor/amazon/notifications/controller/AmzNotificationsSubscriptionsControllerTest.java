package com.wimoor.amazon.notifications.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wimoor.amazon.notifications.service.IAmzNotificationsSubscriptionsService;
import com.wimoor.common.result.Result;

@ExtendWith(MockitoExtension.class)
class AmzNotificationsSubscriptionsControllerTest {

    @Mock
    private IAmzNotificationsSubscriptionsService subscriptionsService;

    @InjectMocks
    private AmzNotificationsSubscriptionsController controller;

    @Test
    void deleteActionShouldDelegateToService() {
        String destinationId = "dest-456";

        Result<?> result = controller.deleteAction(destinationId);

        verify(subscriptionsService).deleteSubscriptions(destinationId);
        verifyNoMoreInteractions(subscriptionsService);
        assertThat(Result.isSuccess(result)).isTrue();
    }
}
