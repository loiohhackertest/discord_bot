package org.ds_bot.server_dis_bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.bossbar.BossBar;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class Dis_Events extends ListenerAdapter{
    //public String[] server = {"!server","!Server"};
    public String[] online = {"!get_online","!Get_online","!Get_Online"};
    public String[] players = {"!get_players","!Get_players","!Get_Players"};
    public String[] ip = {"!get_ip","!Get_ip","!Get_Ip","!Get_IP"};
    public String[] help = {"!help","!Help","!HELP","! help","! Help"};
    public static final String author="loioh";
    //public String[] licence = {"!need_minecraft_account","!Need_minecraft_account","!Need_Minecraft_account","!Need_Minecraft_Account"};

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(!e.getAuthor().isBot()){
            if((Server_dis_bot.tc != null) && e.getChannel().getId().equals(Server_dis_bot.tc.getId())){
                if(use_command(e.getMessage().getContentRaw(), help)){
                    e.getChannel().sendMessage("!get_ip - get ip of minecraft server()").queue();
                    //e.getChannel().sendMessage("!server - ...").queue();
                    e.getChannel().sendMessage("!get_online - get amount of players at minecraft server").queue();
                    e.getChannel().sendMessage("!get_players - get names of players at minecraft server").queue();
                    //e.getChannel().sendMessage("!need_minecraft_account - answer").queue();
                    e.getChannel().sendMessage("!say a text - send |a text| to the server(with your discord name)").queue();
                    e.getChannel().sendMessage("!tell player_name a text - send |a text| private to the player_name(to the server with your discord name)").queue();
                    e.getChannel().sendMessage("!kick player_name kick message- kick the player(from the server) with |[Discord] Kick: kick message|").queue();
                    e.getChannel().sendMessage("!inv_get player_name - show image of player inventory(you can change/set textures at plugins/server_dis_bot/textures/item)").queue();
                    e.getChannel().sendMessage("!command ... - run command as server console(example: !command op loioh])").queue();
                }
                //if(use_command(e.getMessage().getContentRaw(), server)) {
                //    e.getChannel().sendMessage("Minecraft Server is open now").queue();
                //}
                if(use_command(e.getMessage().getContentRaw(), online)){
                    e.getChannel().sendMessage("At server: "+Metod1.online()+" players now is online.").queue();
                }
                if(use_command(e.getMessage().getContentRaw(),players)){
                    e.getChannel().sendMessage("At server now: ").queue();
                    for(String p : Metod1.players()){
                        e.getChannel().sendMessage("-"+p).queue();
                    }
                }
                if(use_command(e.getMessage().getContentRaw(),ip)){
                    e.getChannel().sendMessage("Server ip: "+Metod1.ip()).queue();
                }
                //if(use_command(e.getMessage().getContentRaw(),licence)){
                //    e.getChannel().sendMessage(Metod1.need_licence()).queue();
                //}
                //pro commands
                String message = e.getMessage().getContentRaw();
                if(message.startsWith("!say ")){
                    String text = message.substring(5).trim();
                    if(text.length() > 0) {
                        Metod1.send_message(text,e.getMessage().getAuthor().getName());
                        //e.getMessage().addReaction("\u2705").queue();
                        e.getChannel().sendMessage("**[**DISCORD**]** "+e.getMessage().getAuthor().getName()+": "+text).queue();
                        e.getMessage().delete().queue();
                    }
                }
                if(message.startsWith("!tell ")){
                    String text = message.substring(5).trim();
                    String[] parts = text.split(" ");
                    parts[0]= parts[0].replace(" ","");
                    Player player_s = Metod1.get_player(parts[0]);
                    if(player_s!=null) {
                        text = text.replace(parts[0]+" ","");
                        if (text.length() > 0) {
                            Metod1.send_private_message(text, e.getMessage().getAuthor().getName(),player_s);
                            e.getMessage().delete().queue();
                        }
                    }else{
                        e.getChannel().sendMessage("player not exist or offline").queue();
                    }
                }
                if(message.startsWith("!kick ")){
                    String text = message.substring(5).trim();
                    Server_dis_bot.PM.kick(e,text);
                }
                if(message.startsWith("!get_inv ")){
                    String text = message.substring(8).trim();
                    Server_dis_bot.PM.draw_inv(e,text);
                }
                if(message.startsWith("!command ")){
                    String text = message.substring(8).trim();
                    Server_dis_bot.PM.dc(e.getMessage(),text);
                }
            }
        }
        super.onMessageReceived(e);
    }


    public static Boolean use_command(String mess, String[] command){
        for(String c1:command){
            if(c1.equals(mess)){
                return true;
            }
        }
        return false;
    }
}
