package escapegen.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import escapegen.commands.CommandExecutor;
import escapegen.context.Game;
import escapegen.context.UserIOWeb;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vita on 9/1/16.
 */
@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private UserIOWeb io;
    @Autowired
    private Game game;
    @Autowired
    private CommandExecutor commandExecutor;

    @RequestMapping("/execute-command/{command}")
    public @ResponseBody String actions(@PathVariable("command") String command) {
        commandExecutor.executeCommand(command);
        GameStateResponse response = new GameStateResponse();
        response.setCurrentLocation(game.getPathToCurrentLocation());
        response.setResponse(io.retrieveBufferedData());
        return objectToJson(response);
    }

    @SneakyThrows
    private String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
