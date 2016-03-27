package com.wotf.game.classes;

import com.badlogic.gdx.math.Vector2;
import java.util.Collections;
import java.util.List;

public class Game {
    private Player host;
    private List<Player> players;
    private List<Team> teams;
    private Map map;
    
    private GamePhysics gamePhysics;
    private GameSettings gameSettings;
    private TurnLogic turnLogic;

    public Game(GameSettings gameSettings, Map map, List<Player> players) {
        this.gameSettings = gameSettings;
        this.host = players.get(0);
        this.players = players;
        this.teams = this.gameSettings.getTeams();
        this.gamePhysics = new GamePhysics();
        this.turnLogic = new TurnLogic(this.teams.size());
        this.map = map;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getPlayer(String ip) {
        return players
                .stream()
                .filter(x -> x.getIp().equals(ip))
                .findFirst()
                .get();
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Player getHost() {
        return host;
    }

    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    public Team getTeam(int index) {
        return teams.get(index);
    }
    
    public Team getActiveTeam() {
        return teams.get(turnLogic.getActiveTeamIndex());
    }
    
    public Map getMap() {
        return map;
    }
    
    public void endTurn() {
        // TODO: Weet jip niet
    }
    
    public GameSettings getGameSettings() {
        return gameSettings;
    }
    
    public TurnLogic getTurnLogic() {
        return turnLogic;
    }
    // TODO: Function for handling new/next turn
}