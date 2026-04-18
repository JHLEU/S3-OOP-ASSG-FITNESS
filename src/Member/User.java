package member;

public class User {
    private String username;
    private String password;
    private String tier;
    private double totalSpent;
    private boolean isDeleted;

    // Constructor
    public User(String u, String p, double s, String l, boolean deleted) {
        this.username = u;
        this.password = p;
        this.totalSpent = s;
        this.tier = l;
        this.isDeleted = deleted;
    }

    public void checkTier() {
        if (totalSpent >= 300) tier = "Gold";
        else if (totalSpent >= 200) tier = "Silver";
        else tier = "Bronze";
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    @Override
    public String toString() {
        return username + "," + password + "," + totalSpent + "," + tier + "," + isDeleted;
    }
}