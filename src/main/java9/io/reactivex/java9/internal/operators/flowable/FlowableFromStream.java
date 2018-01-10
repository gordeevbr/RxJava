package io.reactivex.java9.internal.operators.flowable;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.operators.flowable.FlowableFromIterable;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;

import java.util.Iterator;
import java.util.stream.Stream;

public final class FlowableFromStream<T> extends Flowable<T> {

    private final Stream<? extends T> source;

    public FlowableFromStream(Stream<? extends T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> s) {
        Iterator<? extends T> it;

        try {
            it = source.iterator();
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptySubscription.error(e, s);
            return;
        }

        FlowableFromIterable.subscribe(s, it);
    }
}
