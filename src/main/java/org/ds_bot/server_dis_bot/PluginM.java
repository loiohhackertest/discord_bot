package org.ds_bot.server_dis_bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class PluginM {

    private FileConfiguration config;
    public JavaPlugin plugin;
    public PluginM(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void kick(MessageReceivedEvent e,String text){
        //msg(text);

        String[] parts = text.split(" ");
        parts[0] = parts[0].replace(" ","");
        Player p = Metod1.get_player(parts[0]);
        if(p!=null) {
            e.getMessage().addReaction("\u2705").queue();

            Bukkit.getScheduler().runTask(plugin, () -> {
                String text2 = text.replace(parts[0],"");
                if(text2.length() >0) {
                    p.kickPlayer("[Discord] Kick:"+text2);
                }else{
                    p.kickPlayer("[Discord] Kick");
                }
                //p.teleport(p.getLocation().clone().add(0, 10, 0));
            });

        }else{
            e.getChannel().sendMessage("player not exist or offline").queue();
        }
    }
    public void draw_inv(MessageReceivedEvent e,String text){
        //msg(text);

        text = text.replace(" ","");
        Player p = Metod1.get_player(text);
        if(p!=null) {
            e.getMessage().addReaction("\u2705").queue();
            File f_img = new File(plugin.getDataFolder(),"inv.png");

            String finalText = text;
            Bukkit.getScheduler().runTask(plugin, () -> {

                BufferedImage img = send_inventory(p.getInventory());
                try {
                    ImageIO.write(img, "png", f_img);
                    send_img(finalText + "'s Inventory", e.getChannel(), f_img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        }else{
            e.getChannel().sendMessage("player not exist or offline").queue();
        }
    }
    public void dc(Message m, String command){
        Bukkit.getScheduler().runTask(plugin,()->{
            Boolean c = Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
            if(c) {
                m.addReaction("\u2705").queue();
            }else{
                m.addReaction("\u274C").queue();
            }
        });
    }
    private void send_img(String name, MessageChannel chat, File f_img){
        EmbedBuilder em = new EmbedBuilder();
        em.setColor(Color.GREEN);
        em.setTitle(name);
        em.setImage("attachment://"+f_img.getName());

        chat.sendMessage(em.build()).addFile(f_img,f_img.getName()).queue();
    }
    public static final String site="https://www.paypal.com/paypalme/loioh";
    public BufferedImage send_inventory(PlayerInventory pi) {
        File f_img = new File(plugin.getDataFolder(), "slot.png");
        if (!f_img.getParentFile().exists()) {
            f_img.getParentFile().mkdirs();
        }
        if (!f_img.exists()) {
            draft(f_img, 32, 32);
        }
        try {

            BufferedImage s_img = ImageIO.read(f_img);
            int w = s_img.getWidth();
            int h = s_img.getHeight();
            BufferedImage i_img = new BufferedImage(w * (9 + 4), (h * 4) + (2 * (h / 16)), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graf = i_img.createGraphics();
            graf.setColor(new Color(198, 198, 198));
            graf.fillRect(0, 0, w * (9 + 4), h * 5);
            graf.drawRect(0, 0, w * (9 + 4), h * 5);
            int w3 = 2 * w;


            for (int i = 0; i < (27 + 9); i++) {
                int x = w3 + ((i % 9) * w);
                int y = (i / 9) * h;
                if (i >= 27) {
                    y += 2 * (h / 16);
                }
                graf.drawImage(s_img, x, y, null);

                if (i >= 27) {
                    if (M_Events.isItem(pi.getContents()[i%9])) {
                        ItemStack item = pi.getContents()[i%9].clone();
                        graf.drawImage(get_item_img(h,w,item), x, y, null);
                    }
                }else{
                    if (M_Events.isItem(pi.getContents()[i+9])) {
                        ItemStack item = pi.getContents()[i+9].clone();
                        graf.drawImage(get_item_img(h,w,item), x, y, null);
                    }
                }



            }
            for (int i = 0; i < 4; i++) {
                int x = (14 * (w / 16));
                int y = ((i % 9) * h);
                graf.drawImage(s_img, x, y, null);
                if (M_Events.isItem(pi.getArmorContents()[i])) {
                    ItemStack armor = pi.getArmorContents()[i].clone();
                    graf.drawImage(get_item_img(h,w,armor), x, y, null);
                }
            }
            int x = w3 + (9 * w) + 2 * (w / 16);
            int y = (3 * h) + 2 * (h / 16);
            graf.drawImage(s_img, x, y, null);


            if (M_Events.isItem(pi.getItemInOffHand())) {
                ItemStack i = pi.getItemInOffHand().clone();
                graf.drawImage(get_item_img(h,w,i), x, y, null);
            }


            graf.dispose();
            return i_img;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private BufferedImage get_item_img(int h,int w,ItemStack item) throws IOException {
        item = item.clone();
        String img_n;
        if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
            img_n = item.getItemMeta().getDisplayName().replace(" ","_").toLowerCase()+".png";
            File fi0 = new File(plugin.getDataFolder(),"textures\\item\\"+img_n);
            File fb0 = new File(plugin.getDataFolder(),"textures\\block\\"+img_n);
            if(!fi0.exists() && !fb0.exists()){
                msg("not founded image for item(by name):"+img_n);
                img_n = item.getType().toString().toLowerCase()+".png";
            }
        }else{
            img_n = item.getType().toString().toLowerCase()+".png";
        }
        msg("item img must be:"+img_n);
        File fi = new File(plugin.getDataFolder(),"textures\\item\\"+img_n);
        File fb = new File(plugin.getDataFolder(),"textures\\block\\"+img_n);
        if(fi.exists()) {
            return ISize(w,h,ImageIO.read(fi));
        }else if(fb.exists()){
            return ISize(w,h,ImageIO.read(fb));
        }

        BufferedImage im = new BufferedImage(32,32,BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graf = im.createGraphics();
        graf.setColor(new Color(10,10,10));
        graf.fillRect(0,0,32,32);
        graf.dispose();
        return im;
    }
    private void draft(File f,int w0,int h0){
        //int w0 = 32;
        //int h0 = 32;
        BufferedImage ni = new BufferedImage(w0,h0,BufferedImage.TYPE_3BYTE_BGR);
        Color b = new Color(55,55,55);
        Color g = new Color(139,139,139);
        Color w = new Color(255,255,255);
        Graphics2D graf = ni.createGraphics();
        graf.setColor(w);
        graf.fillRect(0,0,w0,h0);
        graf.setColor(b);
        graf.fillRect(0,0,(h0/16),h0);
        graf.fillRect(0,0,w0,(h0/16));
        graf.setColor(g);
        graf.fillRect((w0/16),(h0/16),w0-(2*(w0/16)),h0-(2*(h0/16)));
        graf.fillRect(w0-(w0/16),0,(w0/16),(h0/16));
        graf.fillRect(0,h0-(h0/16),(w0/16),(h0/16));
        graf.dispose();
        try{
            ImageIO.write(ni,"png",f);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private BufferedImage ISize(int w,int h, BufferedImage img){
        BufferedImage sized = new BufferedImage(w,h,img.getType());
        sized.getGraphics().drawImage(img,0,0,w,h,null);
        return sized;
    }
    public void msg(String text){
        plugin.getLogger().warning(text);
    }
}


//class KickT implements Runnable{
//    private int m;
//    private Player p;
//    public KickT(int m,Player p) {
//        this.m = m;
//        this.p = p;
//    }
//
//    @Override
//    public void run(){
//        if(m==0) {
//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + p.getName());
//        }else{
//            p.kickPlayer("Discord kick");
//        }
//    }
//}
