package uc.mei.is.admin.command;


/**
 * ICommand
 */
public abstract class Command {
    private final String host;
    private final String basePath;
    private final String cmd;


    protected Command(String host, String basePath, String command){
        this.host = host;
        this.basePath = basePath;
        this.cmd = command;

    }


    /**
     * Retrieves Command path
     * @return
     */
    public final String getPath() {
        return cmd;
    }
    /**
     * Retrieves base path path for current Admin command
     * @return 
     */
    public final String getBasePath() {
        return basePath+getPath();
    }
    /**
     * Retrieves full path for command.
     * @return
     */
    public final String getFullPath() {
        return host+getBasePath();
    }

    public abstract boolean execute(String... args); //Args might not be needed
    public abstract String dump(String... args); //Args might not be needed
    public abstract String console();
}