package fr.maxlego08.jobs.zcore.enums;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {

    PREFIX("&8(#42f59bJobs&8) "),

    INVENTORY_CLONE_NULL("&cThe inventory clone is null!"),
    INVENTORY_OPEN_ERROR("&cAn error occurred with the opening of the inventory &6%id%&c."),
    TIME_DAY("%02d %day% %02d %hour% %02d %minute% %02d %second%"),
    TIME_HOUR("%02d %hour% %02d minute(s) %02d %second%"),
    TIME_MINUTE("%02d %minute% %02d %second%"),
    TIME_SECOND("%02d %second%"),

    FORMAT_SECOND("second"), FORMAT_SECONDS("seconds"),

    FORMAT_MINUTE("minute"), FORMAT_MINUTES("minutes"),

    FORMAT_HOUR("hour"), FORMAT_HOURS("hours"),

    FORMAT_DAY("d"), FORMAT_DAYS("days"),

    COMMAND_SYNTAXE_ERROR("&cYou must execute the command like this&7: &a%syntax%"),
    COMMAND_NO_PERMISSION("&cYou do not have permission to run this command."),
    COMMAND_NO_CONSOLE("&cOnly one player can execute this command."),
    COMMAND_NO_ARG("&cImpossible to find the command with its arguments."),
    COMMAND_SYNTAXE_HELP("&f%syntax% &7» &7%description%"),

    RELOAD("&aYou have just reloaded the configuration files."),

    DESCRIPTION_RELOAD("Reload configuration files"),
    DESCRIPTION_JOIN("Join a job"),
    DESCRIPTION_LEAVE("Leave a job"),
    DESCRIPTION_LEAVE_CONFIRM("Confirm that you are leaving a job"),

    DESCRIPTION_ADMIN("Show admin commands"),
    DESCRIPTION_ADMIN_LEVEL("Show admin level commands"),
    DESCRIPTION_ADMIN_LEVEL_ADD("Add level to a player's job"),
    DESCRIPTION_ADMIN_LEVEL_SET("Define level to a player's job"),
    DESCRIPTION_ADMIN_LEVEL_REMOVE("Remove level to a player's job"),
    DESCRIPTION_ADMIN_EXPERIENCE("Show admin experience commands"),
    DESCRIPTION_ADMIN_EXPERIENCE_ADD("Add experience to a player's job"),
    DESCRIPTION_ADMIN_EXPERIENCE_SET("Define experience to a player's job"),
    DESCRIPTION_ADMIN_EXPERIENCE_REMOVE("Remove experience to a player's job"),
    DESCRIPTION_ADMIN_PRESTIGE("Show admin prestige commands"),
    DESCRIPTION_ADMIN_PRESTIGE_ADD("Add prestige to a player's job"),
    DESCRIPTION_ADMIN_PRESTIGE_SET("Define prestige to a player's job"),
    DESCRIPTION_ADMIN_PRESTIGE_REMOVE("Remove prestige to a player's job"),

    PROGRESSION_BOSSBAR("#2fe082%job-name% #434343- #f7f725%job-experience%&8/#f78e25%job-max-experience% #434343- #2fe082P%job-prestige% lvl %job-level%"),

    DOESNT_EXIST("&cThe jobs &f%name% &c does not exist."),
    JOIN_ERROR_CANT("&cYou can’t join this job."),
    JOIN_ERROR_ALREADY("&cYou already have the jobs &f%name%&c."),
    JOIN_ERROR_LIMIT("&cYou have reached the job limit &8(&7%max%&8)&c, you cannot join a new job."),
    JOIN_SUCCESS("&aYou have just joined the job &2%name%&a."),

    LEAVE_ERROR_CANT("&cYou can’t leave this job."),
    LEAVE_ERROR("&cYou do not have the jobs &f%name%&c."),
    LEAVE_SUCCESS_CONFIRM(
            "",
            "<red>Do you really want to quit the job %name% ?",
            "<green><hover:show_text:'Click to confirm!'><click:run_command:'/job leaveconfirm %name%'>ᴄᴏɴғɪʀᴍ</click></hover>",
            ""
    ),

    LEAVE_SUCCESS("&aYou have just quit the job &2%name%&a."),

    ADMIN_PLAYER_JOB("&cThe &f%player% &cplayer does not have the &f%name% &cjob."),

    ADMIN_LEVEL_ADD("&aYou just added &a%value% &alevel to the &f%player%&a’s job #34ebb7%name%&a."),
    ADMIN_LEVEL_SET("&aYou just set the level of the job #34ebb7%name%&a to &a%value%&a for &f%player%&a."),
    ADMIN_LEVEL_REMOVE("&aYou have just removed &a%value%&a level from the &f%player%&a’s job #34ebb7%name%&a."),

    ADMIN_PRESTIGE_ADD("&aYou just added &a%value%&a prestige to the &f%player%&a’s job #34ebb7%name%&a."),
    ADMIN_PRESTIGE_SET("&aYou just set the prestige of the job #34ebb7%name%&a to &a%value%&a for &f%player%&a."),
    ADMIN_PRESTIGE_REMOVE("&aYou have just removed&a %value%&a prestige from the&f %player%&a’s job #34ebb7%name%&a."),

    ADMIN_EXPERIENCE_ADD("&aYou just added &a%value%&a experience to the &f%player%&a’s job #34ebb7%name%&a."),
    ADMIN_EXPERIENCE_SET("&aYou just set the experience of the job #34ebb7%name%&a to &a%value%&a for&f %player%&a."),
    ADMIN_EXPERIENCE_REMOVE("&aYou have just removed &a%value%&a experience from the &f%player%&a’s job #34ebb7%name%&a."),

    ;

    private List<String> messages;
    private String message;
    private Map<String, Object> titles = new HashMap<>();
    private boolean use = true;
    private MessageType type = MessageType.TCHAT;
    private ItemStack itemStack;

    /**
     * Constructs a new Message with the specified message string.
     *
     * @param message the message string.
     */
    Message(String message) {
        this.message = message;
        this.use = true;
    }

    /**
     * Constructs a new Message with the specified title, subtitle, and timings.
     *
     * @param title     the title string.
     * @param subTitle  the subtitle string.
     * @param a         the start time in ticks.
     * @param b         the display time in ticks.
     * @param c         the end time in ticks.
     */
    Message(String title, String subTitle, int a, int b, int c) {
        this.use = true;
        this.titles.put("title", title);
        this.titles.put("subtitle", subTitle);
        this.titles.put("start", a);
        this.titles.put("time", b);
        this.titles.put("end", c);
        this.titles.put("isUse", true);
        this.type = MessageType.TITLE;
    }

    /**
     * Constructs a new Message with multiple message strings.
     *
     * @param message the array of message strings.
     */
    Message(String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
    }

    /**
     * Constructs a new Message with a specific type and multiple message strings.
     *
     * @param type    the type of the message.
     * @param message the array of message strings.
     */
    Message(MessageType type, String... message) {
        this.messages = Arrays.asList(message);
        this.use = true;
        this.type = type;
    }

    /**
     * Constructs a new Message with a specific type and a single message string.
     *
     * @param type    the type of the message.
     * @param message the message string.
     */
    Message(MessageType type, String message) {
        this.message = message;
        this.use = true;
        this.type = type;
    }

    /**
     * Constructs a new Message with a single message string and a use flag.
     *
     * @param message the message string.
     * @param use     the use flag.
     */
    Message(String message, boolean use) {
        this.message = message;
        this.use = use;
    }

    /**
     * Gets the message string.
     *
     * @return the message string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Converts the message to a string.
     *
     * @return the message string.
     */
    public String toMsg() {
        return message;
    }

    /**
     * Gets the message string.
     *
     * @return the message string.
     */
    public String msg() {
        return message;
    }

    /**
     * Checks if the message is in use.
     *
     * @return true if the message is in use, false otherwise.
     */
    public boolean isUse() {
        return use;
    }

    /**
     * Gets the list of messages.
     *
     * @return the list of messages.
     */
    public List<String> getMessages() {
        return messages == null ? Collections.singletonList(message) : messages;
    }

    /**
     * Sets the list of messages.
     *
     * @param messages the list of messages.
     */
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    /**
     * Checks if the message contains multiple parts.
     *
     * @return true if the message contains multiple parts, false otherwise.
     */
    public boolean isMessage() {
        return messages != null && messages.size() > 1;
    }

    /**
     * Sets the message string.
     *
     * @param message the message string.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the title string.
     *
     * @return the title string.
     */
    public String getTitle() {
        return (String) titles.get("title");
    }

    /**
     * Gets the map of titles.
     *
     * @return the map of titles.
     */
    public Map<String, Object> getTitles() {
        return titles;
    }

    /**
     * Sets the map of titles and changes the message type to TITLE.
     *
     * @param titles the map of titles.
     */
    public void setTitles(Map<String, Object> titles) {
        this.titles = titles;
        this.type = MessageType.TITLE;
    }

    /**
     * Gets the subtitle string.
     *
     * @return the subtitle string.
     */
    public String getSubTitle() {
        return (String) titles.get("subtitle");
    }

    /**
     * Checks if the message has a title.
     *
     * @return true if the message has a title, false otherwise.
     */
    public boolean isTitle() {
        return titles.containsKey("title");
    }

    /**
     * Gets the start time in ticks.
     *
     * @return the start time in ticks.
     */
    public int getStart() {
        return ((Number) titles.get("start")).intValue();
    }

    /**
     * Gets the end time in ticks.
     *
     * @return the end time in ticks.
     */
    public int getEnd() {
        return ((Number) titles.get("end")).intValue();
    }

    /**
     * Gets the display time in ticks.
     *
     * @return the display time in ticks.
     */
    public int getTime() {
        return ((Number) titles.get("time")).intValue();
    }

    /**
     * Checks if the title is in use.
     *
     * @return true if the title is in use, false otherwise.
     */
    public boolean isUseTitle() {
        return (boolean) titles.getOrDefault("isUse", "true");
    }

    /**
     * Replaces a substring in the message with another string.
     *
     * @param a the substring to replace.
     * @param b the replacement string.
     * @return the modified message string.
     */
    public String replace(String a, String b) {
        return message.replace(a, b);
    }

    /**
     * Gets the type of the message.
     *
     * @return the type of the message.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Sets the type of the message.
     *
     * @param type the type of the message.
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Gets the item stack associated with the message.
     *
     * @return the item stack.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Sets the item stack associated with the message.
     *
     * @param itemStack the item stack.
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}

