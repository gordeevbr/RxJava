package io.reactivex.java9;

import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.java9.internal.operators.flowable.FlowableFromStream;
import io.reactivex.plugins.RxJavaPlugins;

import java.util.stream.Stream;

/**
 * Flowable extension for Java 9. Provides modern API elements.
 * Factory methods return usual {@link io.reactivex.Flowable}.
 *
 * @see io.reactivex.Flowable
 *
 * @param <T>
 *            the type of the items emitted by the Flowable
 */
public abstract class Flowable<T> extends io.reactivex.Flowable<T> {

    /**
     * Converts an {@link Stream} into a Publisher that emits the items in the sequence.
     * The {@link Stream} must still be opened (not operated upon). Otherwise, only an error signal will be sent.
     * <p>
     * <img width="640" height="315" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/from.png" alt="">
     * <dl>
     *  <dt><b>Backpressure:</b></dt>
     *  <dd>The operator honors backpressure from downstream and iterates the {@link Iterable}
     *  extracted from the given {@code stream} on demand (i.e., when requested).</dd>
     *  <dt><b>Scheduler:</b></dt>
     *  <dd>{@code fromStream} does not operate by default on a particular {@link Scheduler}.</dd>
     * </dl>
     *
     * @param source
     *            the source {@link Stream}
     * @param <T>
     *            the type of items in the {@link Stream} and the type of items to be emitted by the
     *            resulting Publisher
     * @return a Flowable that emits each item in the {@link Iterable} extracted from the given {@link Stream}
     * @see <a href="http://reactivex.io/documentation/operators/from.html">ReactiveX operators documentation: From</a>
     */
    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.FULL)
    @SchedulerSupport(SchedulerSupport.NONE)
    public static <T> io.reactivex.Flowable<T> fromStream(Stream<? extends T> source) {
        ObjectHelper.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new FlowableFromStream<T>(source));
    }
}
