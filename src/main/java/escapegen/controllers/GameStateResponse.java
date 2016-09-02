package escapegen.controllers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vita on 9/1/16.
 */
@Getter
@Setter
public class GameStateResponse {
    String response;
    String currentLocation;
    boolean gameEnded;
    List<String> commands;
}
