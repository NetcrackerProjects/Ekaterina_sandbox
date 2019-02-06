package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CommandTest {

    @Test
    public void shouldGetUsernameFromParametersWhenMethodIsCalledForTheFirstTime() {
        String expectedFirstParameter = "interesting_username";
        List<String> parameters = new ArrayList<>();
        parameters.add(expectedFirstParameter);
        parameters.add("adminadmin");
        Command loginCommand = new Command(CommandType.LOG_IN, parameters);

        String actualFirstParameter = loginCommand.nextParameter();

        Assert.assertEquals(expectedFirstParameter, actualFirstParameter);
    }
}
