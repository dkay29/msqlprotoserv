package com.dkay229.msql.server;

import com.dkay229.msql.common.MsqlException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dkay229.msql.common.MsqlErrorCode.BAD_LOGIN_ATTEMPT;
import static com.dkay229.msql.common.MsqlErrorCode.UNKNOWN_USER;

public class ConnectedUsers {
    private Map<String,String> registeredUsers = new ConcurrentHashMap<>();
    private Map<String,List<Integer>> connections = new ConcurrentHashMap<>();
    private AtomicInteger connectionSequence = new AtomicInteger();
    public ConnectedUsers() {
        registeredUsers.put("fred","123pass");
    }
    public int login(String user,String password) {
        if (!registeredUsers.containsKey(user)) {
            throw new MsqlException(UNKNOWN_USER,user);
        }
        if (registeredUsers.get(user).equals(password)) {
            throw new MsqlException(BAD_LOGIN_ATTEMPT,user);
        }
        int connectionId = connectionSequence.incrementAndGet();
        connections.putIfAbsent(user,new ArrayList<>());
        connections.get(user).add(connectionId);
        return connectionId;
    }
}
