package org.delivery.storeadmin.domain.sse.connection.ifs;

import org.delivery.storeadmin.domain.sse.connection.model.UserSessionConnection;

public interface ConnectionPoolIfs<T ,R > {
    void addSession(T uniqueKey, R session);
    R getSession(T uniqueKey);

    void onCompletionCallback(R session);

}
