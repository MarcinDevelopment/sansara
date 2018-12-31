package ru.enke.sansara.network.session;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRegistry {

    private final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    void addSession(final Session session) {
        sessions.add(session);
    }

    void removeSession(final Session session) {
        sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return Collections.unmodifiableSet(sessions);
    }

}
