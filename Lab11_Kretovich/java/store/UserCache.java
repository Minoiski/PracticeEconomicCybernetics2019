package store;

import models.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {
    private static final UserCache INSTANCE = new UserCache();

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<Integer, User>();

    public static UserCache getInstance(){
        return INSTANCE;
    }
    public Collection<User> values() {
        return this.users.values();
    }

    public void add(User user){
        this.users.put(user.getId(),user);
    }
    public void edit(User user){
        this.users.replace(user.getId(),user);
    }
    public void delete(int id){
        this.users.remove(id);
    }
    public User get(final int id){
        return this.users.get(id);
    }
}
