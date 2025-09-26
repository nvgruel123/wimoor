package com.wimoor.amazon.notifications.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wimoor.amazon.auth.service.IAmazonAuthorityService;
import com.wimoor.amazon.notifications.service.IAmzNotificationsDestinationService;
import com.wimoor.amazon.notifications.service.IAmzNotificationsSubscriptionsService;
import com.wimoor.common.result.Result;

@ExtendWith(MockitoExtension.class)
class AmzNotificationsControllerTest {

    @Mock
    private IAmazonAuthorityService amazonAuthorityService;

    @Mock
    private IAmzNotificationsDestinationService destinationService;

    @Mock
    private IAmzNotificationsSubscriptionsService subscriptionsService;

    @InjectMocks
    private AmzNotificationsController controller;

    @Test
    void refreshDestinationActionShouldTriggerDestinationTask() {
        Result<?> result = controller.refreshDestinationAction();

        verify(destinationService).executTask();
        verifyNoMoreInteractions(destinationService, amazonAuthorityService, subscriptionsService);
        assertThat(Result.isSuccess(result)).isTrue();
    }

    @Test
    void refreshSubscriptionsActionShouldDelegateToAuthorityService() {
        Result<?> result = controller.refreshSubscriptionsAction();

        verify(amazonAuthorityService).executTask(subscriptionsService);
        verifyNoMoreInteractions(destinationService, amazonAuthorityService, subscriptionsService);
        assertThat(Result.isSuccess(result)).isTrue();
    }
}
