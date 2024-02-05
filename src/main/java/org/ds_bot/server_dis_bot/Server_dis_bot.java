package org.ds_bot.server_dis_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public final class Server_dis_bot extends JavaPlugin {
    public static JDA bot;
    public static Dis_Events botE;
    public static PluginM PM;
    public static TextChannel tc = null;
    public static TextChannel tc2 = null;
    //void k(int... a){
    //    a.length
    //}
    private String[] paths = {
            String.join(".","discord_bot_token"),
            String.join(".","text_chanel_name(for the commands(!help))"),
            String.join(".","text_chanel_name(game chat copping to)")
    };
    @Override
    public void onEnable() {
        if(!(getDescription().getAuthors().contains(Dis_Events.author) && getDescription().getWebsite().equals(PluginM.site))){
            getServer().getPluginManager().disablePlugin(this);
        }

        //getLogger().warning("author: "+item_get.author);
        //getLogger().warning("website: "+ItemsManager.site);
        Bukkit.broadcast(new BaseComponent[]{hl_text(ChatColor.DARK_PURPLE+"[DiscordBot]: Welcome","Author: "+Dis_Events.author+"\n"+"click-Donation Link(paypal).",PluginM.site)});

        getServer().getPluginManager().registerEvents(new M_Events(),this);
        PM = new PluginM(this);



        if(getConfig().contains(paths[0])) {
            String token = this.getConfig().getString(paths[0]);
            if(!token.equals("null")) {
                try {
                    botE = new Dis_Events();
                    bot = JDABuilder.createDefault(token).setActivity(Activity.watching("Minecraft Server")).addEventListeners(botE).build();
                    bot.awaitReady();
                    if (getConfig().contains(paths[1]) && !getConfig().getString(paths[1]).equals("null")) {
                        String chanel = getConfig().getString(paths[1]);
                        tc = bot.getTextChannelsByName(chanel, true).get(0);
                    } else {
                        getLogger().warning("not founded special channel (users can use the commands(!help) in a channel)");
                    }
                    if (getConfig().contains(paths[2]) && !getConfig().getString(paths[2]).equals("null")) {
                        String chanel = getConfig().getString(paths[2]);
                        tc2 = bot.getTextChannelsByName(chanel, true).get(0);
                    } else {
                        getLogger().warning("not founded special channel (users not able see what happening in game chat)");
                    }
                    if(tc!=null) {
                        tc.sendMessage("plugin enabled").queue();
                    }
                } catch (LoginException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                getLogger().warning("not founded DS Bot token(add token and restart the server)");
                getServer().getPluginManager().disablePlugin(this);
            }
        }else{
            getConfig().set(paths[0],"null");
            getConfig().set(paths[1],"null");
            getConfig().set(paths[2],"null");
            saveConfig();
            getLogger().warning("not founded DS Bot token(add token and restart the server)");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if(bot != null && tc != null){
            tc.sendMessage("plugin disable").queue();
            bot.removeEventListener(botE);
            bot.shutdown();
        }
    }
    public static TextComponent hl_text(String text, String hover_text, String link){
        TextComponent text1 = new TextComponent(text);
        Text h_text = new Text(hover_text);
        text1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,h_text));
        text1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
        return text1;
    }
}
