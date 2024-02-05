package org.ds_bot.server_dis_bot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Metod1{
    public static int online(){
        if(Bukkit.getServer().getOnlinePlayers().isEmpty()){
            return 0;
        }
        return Bukkit.getServer().getOnlinePlayers().size();
    }
    public static String ip(){
        return Bukkit.getServer().getIp();
    }
    public static List<String> players(){
        List<String> players = new ArrayList<>();
        for(Player player:Bukkit.getOnlinePlayers()){
            players.add(player.getName());
        }
        if(players.isEmpty()){
            players.add("not players");
        }
        return players;
    }
    public static String need_licence(){
        if(Bukkit.getServer().getOnlineMode()) {
            return "Yes, Need minecraft account.";
        }
        return "No, Minecraft account is unnecessary.";
    }
    public static Player get_player(String name){
        for(Player player:Bukkit.getOnlinePlayers()){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }
    public static void send_message(String message,String autor){
        Bukkit.getServer().broadcastMessage(ChatColor.BOLD+"["+ChatColor.LIGHT_PURPLE+"DISCORD"+ChatColor.WHITE+ChatColor.BOLD+"] "+ChatColor.YELLOW+autor+": "+ChatColor.GRAY+""+message);
    }
    public static void send_private_message(String message,String autor,Player player){
        player.sendMessage(ChatColor.BOLD+"["+ChatColor.DARK_GRAY+"PRIVATE_MESSAGE_DISCORD"+ChatColor.WHITE+ChatColor.BOLD+"] "+ChatColor.YELLOW+autor+": "+ChatColor.GRAY+""+message);
    }


}
