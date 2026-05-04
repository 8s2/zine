package com.eightsidedsquare.zine.common.level;

public final class ClientSideEvent<T> {
    public static <T> ClientSideEvent<T> create() {
        return new ClientSideEvent<>();
    }

    private ClientSideEvent() {
    }
}
