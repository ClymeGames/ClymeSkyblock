package solutions.misi.clymeskyblockcore.data.vault.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.math.BigDecimal;
import java.util.List;

public class ClymeEconomy implements Economy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "ClymeEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public String format(double v) {
        return "$" + v;
    }

    @Override
    public String currencyNamePlural() {
        return "dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "dollar";
    }

    @Override
    public boolean hasAccount(String s) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    @Override
    public double getBalance(String s) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(offlinePlayer.getUniqueId())).doubleValue();
    }

    @Override
    public double getBalance(String s, String s1) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(offlinePlayer.getUniqueId())).doubleValue();
    }

    @Override
    public boolean has(String s, double v) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(offlinePlayer.getUniqueId())).doubleValue() >= v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(offlinePlayer.getUniqueId())).doubleValue() >= v;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() - v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(Bukkit.getPlayer(s), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(offlinePlayer.getPlayer()).doubleValue() - v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(offlinePlayer.getPlayer(), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() - v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(Bukkit.getPlayer(s), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(offlinePlayer.getPlayer()).doubleValue() - v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(offlinePlayer.getPlayer(), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() + v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(Bukkit.getPlayer(s), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(offlinePlayer.getPlayer()).doubleValue() + v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(offlinePlayer.getPlayer(), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(Bukkit.getPlayer(s)).doubleValue() + v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(Bukkit.getPlayer(s), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        double changedBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(offlinePlayer.getPlayer()).doubleValue() + v;
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(offlinePlayer.getPlayer(), new BigDecimal(changedBalance));
        return new EconomyResponse(v, changedBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
