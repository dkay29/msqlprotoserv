package com.dkay229.msql.server;

import java.time.Instant;
import java.util.Objects;

public class ConnectedUser {
    private String username;
    private int connectionId;
    private long connectionKey;
    Instant connectionTime = Instant.now();

    public Instant getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Instant connectionTime) {
        this.connectionTime = connectionTime;
    }

    public long getConnectionKey() {
        return connectionKey;
    }

    public void setConnectionKey(long connectionKey) {
        this.connectionKey = connectionKey;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ConnectedUser(String username, int connectionId,long connectionKey) {
        this.username = username;
        this.connectionId = connectionId;
        this.connectionKey = connectionKey;
    }

    public String getUsername() {
        return username;
    }

    public int getConnectionId() {
        return connectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectedUser that = (ConnectedUser) o;
        return connectionId == that.connectionId && connectionKey == that.connectionKey && Objects.equals(username, that.username) && Objects.equals(connectionTime, that.connectionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, connectionId, connectionKey, connectionTime);
    }

    @Override
    public String toString() {
        return "ConnectedUser{" +
                "username='" + username + '\'' +
                ", connectionId=" + connectionId +
                ", connectionKey=" + connectionKey +
                ", connectionTime=" + connectionTime +
                '}';
    }
}
