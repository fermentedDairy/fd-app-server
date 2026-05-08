package org.fermented.dairy.app.server.utils.tuple;

import java.util.function.Function;

public record Pair<K, V>(K left, V right) {
    public <R> Pair<R, V> mapLeft(Function<? super K, ? extends R> mapper) {
        return new Pair<>(mapper.apply(left), right);
    }

    public <R> Pair<K, R> mapRight(Function<? super V, ? extends R> mapper) {
        return new Pair<>(left, mapper.apply(right));
    }
}
