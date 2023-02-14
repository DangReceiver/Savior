package de.tdf.language;

import de.tdf.PC;
import org.bukkit.entity.Player;

import java.io.File;

public class ReadOut {

    private String path, langPath;
    private Player p;
    private File lf;

    private float vol = 0.5f, pit = 1f;

    public ReadOut(String pLangPath) {
        langPath = pLangPath;
    }

    public String returnString(Player p, boolean play) {
        if (!playerInitialised()) initPlayer(p);

        if (play) play();

        return Language.getMessage(lf, langPath);
    }

    public boolean playerInitialised() {
        return (p != null && lf != null);
    }

    public boolean wasEdited() {
        return (path != null && path.charAt(0) == '/' && !path.contains("_"));
    }

    public void setPath() {
        if (wasEdited()) return;
        path = "/" + langPath.replaceAll("_", "/");
    }

    public void initPlayer(Player pP) {
        p = pP;
        lf = Language.getLang(p);
    }

    public boolean hasSettings() {
        return false;  // must be implemented later
    }

    public void toggle(boolean toggle) {
        PC pc = PC.loadConfig(p);
        pc.setReadOutEnabled(toggle);
        pc.savePCon();
    }

    public void applySettings() {
        PC pc = PC.loadConfig(p);
        pit = pc.getReadOutPitch();
        vol = pc.getReadOutVolume();
    }

    public boolean speedApplicable() {
        return true;  // must be implemented later
    }

    public void play() {
        p.playSound(p.getLocation(), lf.getName()
                .split(".yml")[0] + path, vol, pit);
    }

    public void quickPlay(String pPath, Player pP) {
        pP.playSound(pP.getLocation(), Language.getLang(pP).getName()
                .split(".yml")[0] + pPath, 0.5f, 1f);
    }

    public void setPath(String pPath) {
        path = pPath;
    }

    public String getPath() {
        return path;
    }

    public Player getPlayer() {
        return p;
    }

    public File getLang() {
        return lf;
    }

    public void test() {
        if (p == null) throw new RuntimeException(Language.getMessage(
                Language.getServerLang(), "test_run_player_required"));

        File lf = Language.getLang(p);
        p.sendMessage(Language.getMessage(lf, "tutorial_lennart_pre") +
                new ReadOut("tutorial_lennart_welcome").returnString(p, true));
    }

}
