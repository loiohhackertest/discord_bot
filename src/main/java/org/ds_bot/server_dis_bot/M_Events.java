package org.ds_bot.server_dis_bot;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class M_Events implements Listener {
    @EventHandler
    public void onM(AsyncPlayerChatEvent e){
        String m = e.getMessage();
        String a_name = e.getPlayer().getName();
        if(Server_dis_bot.tc2 !=null) {
            Server_dis_bot.tc2.sendMessage("**[**Server**]** " + a_name + ": " + m).queue();
        }
    }
    @EventHandler
    public void onJ(PlayerJoinEvent e){
        String a_name = e.getPlayer().getName();
        if(Server_dis_bot.tc2 !=null) {
            Server_dis_bot.tc2.sendMessage("**[**Server**]** player "+a_name+" join to the server!").queue();
        }
    }
    @EventHandler
    public void onQ(PlayerQuitEvent e){
        String a_name = e.getPlayer().getName();
        if(Server_dis_bot.tc2 !=null) {
            Server_dis_bot.tc2.sendMessage("**[**Server**]** player "+a_name+" leave the server!").queue();
        }
    }


    public static Boolean isItem(ItemStack a){
        if( a != null && a.getType() != null && a.getType() != Material.AIR){
            return true;
        }
        return false;
    }
}