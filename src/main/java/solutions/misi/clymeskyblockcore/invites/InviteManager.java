package solutions.misi.clymeskyblockcore.invites;

import lombok.Getter;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.HashMap;
import java.util.Map;

public class InviteManager {

    @Getter public Map<ClymePlayer, ClymePlayer> inviting = new HashMap<>();

    public void sendPlayerInvite(ClymePlayer clymePlayer, ClymePlayer clymeTarget) {
        //> Player already redeemed his reward
        if(clymePlayer.getPlayer().hasPermission("clymegames.invites.invited")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You've already claimed your invite reward!");
            return;
        }

        //> Target already received his reward
        if(clymeTarget.getPlayer().hasPermission("clymegames.invites.redeemed")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + clymeTarget.getUsername() + ClymeChatColor.ERROR() + " has already redeemed his Invite Reward!");
            return;
        }

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully sent an invite request to " + ClymeChatColor.SECONDARY() + clymeTarget.getUsername());
        clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have received an invite request from " + ClymeChatColor.SECONDARY() + clymePlayer.getUsername());
        clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Use " + ClymeChatColor.SECONDARY() + "/invite accept" + ClymeChatColor.INFO() + " to accept it!");
    }

    public ClymePlayer getInviter(ClymePlayer clymePlayer) {
        for(Map.Entry<ClymePlayer, ClymePlayer> entry : inviting.entrySet()) {
            if(entry.getValue() == clymePlayer) return entry.getKey();
        }

        return null;
    }
}
