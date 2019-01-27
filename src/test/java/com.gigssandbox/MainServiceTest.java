package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MainServiceTest {
    private MainService mainService;

    @Before
    public void setUp() {
        this.mainService = new MainService();
    }

    @Test
    public void shouldReturnRegistrationResponseWhenLastCommandWasRegistration() {
        String expectedResponse = "register";
        Whitebox.setInternalState(mainService, "response", expectedResponse);

        String actualResponse = mainService.response();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldSetResponseLogOutWhenUserHasEnteredLogOutCommand() {
        String expectedResponse = "log_out";

        mainService.process(new Command(CommandType.LOG_OUT, Collections.emptyMap()));

        assertEquals(expectedResponse, mainService.response());
    }

    @Test
    public void shouldSetResponseUnsupportedWhenEnteredCommandIsUnsupported() {
        String expectedResponse = "unsupported";

        mainService.process(new Command(CommandType.UNSUPPORTED, Collections.emptyMap()));

        assertEquals(expectedResponse, mainService.response());
    }

    @Test
    public void shouldSetResponseHelpWhenUserHasEnteredHelpCommand() {
        String expectedResponse = "help";

        mainService.process(new Command(CommandType.HELP, Collections.emptyMap()));

        assertEquals(expectedResponse, mainService.response());
    }

    @Test
    public void shouldSetResponseNotEnoughParametersWhenInputContainsNotAllParameters() {
        String expectedResponse = "not_enough_parameters";

        mainService.process(new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyMap()));

        assertEquals(expectedResponse, mainService.response());
    }
}
