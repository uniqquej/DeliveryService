package org.delivery.storeadmin.domain.sse.connection;

import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.model.UserSessionConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSessionConnection> {
    private static final Map<String, UserSessionConnection> connectionPool = new ConcurrentHashMap<>();


    @Override
    public void addSession(String uniqueKey, UserSessionConnection userSessionConnection) {
        connectionPool.put(uniqueKey, userSessionConnection);
    }

    @Override
    public UserSessionConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    @Override
    public void onCompletionCallback(UserSessionConnection session) {
        connectionPool.remove(session.getUniqueKey());
    }
}
