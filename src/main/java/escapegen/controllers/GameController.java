package escapegen.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import escapegen.commands.CommandExecutor;
import escapegen.context.Game;
import escapegen.context.UserPrinterWeb;
import escapegen.context.configuration.ConfigRepository;
import escapegen.context.configuration.GameConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vita on 9/1/16.
 */
@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private UserPrinterWeb io;
    @Autowired
    private Game game;
    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    ConfigRepository repository;

    @RequestMapping("/execute-command/{command}")
    public @ResponseBody String executeCommand(@PathVariable("command") String command) {
        switch (game.getState()) {
            case WAITING_FOR_COMMAND:
                commandExecutor.executeCommand(command);
                break;
            case WAITING_FOR_INPUT:
                game.processInput(command);
                commandExecutor.invite();
                break;
            case GAME_OVER:
                break;
        }
        return objectToJson(createResponse());
    }

    @RequestMapping("/load/{id}")
    public @ResponseBody String loadGame(@PathVariable("id") String id) {
        GameConfig config = repository.findByConfigId(id);
        game.loadGame(config);
        commandExecutor.invite();
        return objectToJson(createResponse());
    }

    @RequestMapping("/save/{name}")
    public @ResponseBody String saveGame(@PathVariable("name") String name) {
        GameConfig config = game.getConfiguration();
        config.setName(name);
        repository.save(config);
        return objectToJson(true);
    }

    @RequestMapping("/generate")
    public @ResponseBody String generateGame() {
        game.loadGame();
        commandExecutor.invite();
        return objectToJson(createResponse());
    }

    @RequestMapping("/last-saved")
    public @ResponseBody String getLastSavedGames() {
        List<GameConfig> configs = repository.findAll();
        List<ConfigResponse> responses = configs.stream().map(c -> {
            ConfigResponse configResponse = new ConfigResponse();
            configResponse.setName(c.getName());
            configResponse.setId(c.getConfigId());
            return configResponse;
        }).collect(Collectors.toList());
        return objectToJson(responses);
    }

    private GameStateResponse createResponse() {
        GameStateResponse response = new GameStateResponse();
        response.setCurrentLocation(game.getPathToCurrentLocation());
        response.setResponse(io.retrieveBufferedData());
        response.setGameEnded(game.isGameOver());
        response.setCommands(commandExecutor.getAllCommands());
        return response;
    }

    @SneakyThrows
    private String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
