package me.gw3.onigokko.game;

import me.gw3.onigokko.Main;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class GameScoreBoard {

    private Scoreboard scoreboard;
    private ScoreboardManager sbm = Bukkit.getScoreboardManager();
    private Map<String, Objective> objectives = new HashMap<>();

    public GameScoreBoard(){
        this.scoreboard = sbm.getNewScoreboard();
    }

    public Objective createObjective(String name, DisplaySlot slot, String displayName){
        Objective objective = scoreboard.registerNewObjective(name, "");
        objective.setDisplaySlot(slot);
        objective.setDisplayName(displayName);
        objectives.put(objective.getDisplayName(), objective);
        return objective;
    }

    public void sendScoreBoardAll(){
        for(GamePlayer player : Main.gameMain.players){
            player.getBukkitPlayer().setScoreboard(scoreboard);
        }
    }

    public void clear(DisplaySlot slot){
        scoreboard.clearSlot(slot);
    }

    public void createTeam(Objective objective, String name, String entry, int score){
        Team team = scoreboard.registerNewTeam(name);
        team.addEntry(entry);
        team.setSuffix("");
        team.setPrefix("");
        objective.getScore(entry).setScore(score);
    }

    public Scoreboard getScoreboard(){
        return scoreboard;
    }
}
