package com.gitHub.past.common;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DefOptional<T, R> {

    private static final DefOptional<?, ?> EMPTY = new DefOptional<>();

    private final T value;

    private final R defvalue;

    private DefOptional() {
        this.value = null;
        this.defvalue = null;
    }

    public static <T, R> DefOptional<T, R> empty() {
        @SuppressWarnings("unchecked")
        DefOptional<T, R> t = (DefOptional<T, R>) EMPTY;
        return t;
    }

    private DefOptional(T value) {
        this.value = Objects.requireNonNull(value);
        this.defvalue = null;
    }

    private DefOptional(T value, R defvalue) {
        this.value = value;
        this.defvalue = Objects.requireNonNull(defvalue);
    }

    public static <T, R> DefOptional<T, R> of(T value) {
        return new DefOptional<>(value);
    }

    public static <T, R> DefOptional<T, R> of(T value, R defvalue) {
        return new DefOptional<>(value, defvalue);
    }

    public static <T, R> DefOptional<T, R> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static <T, R> DefOptional<T, R> ofNullable(T value, R defvalue) {
        return defvalue == null ? empty() : of(value, defvalue);
    }

    public Optional get() {

        if (isValuePresent()) {
            return Optional.of(value);
        }

        if (isDefvaluePresent()) {
            return Optional.of(defvalue);
        }

        if (Objects.isNull(value) && Objects.isNull(defvalue)) {
            throw new NoSuchElementException("No value present");
        }
        return null;
    }

    public boolean isPresent() {
        return isValuePresent() && isDefvaluePresent();
    }

    public boolean isValuePresent() {
        return Objects.nonNull(value);
    }

    public boolean isDefvaluePresent() {
        return Objects.nonNull(defvalue);
    }


    public void ifPresent(Consumer<? super T> consumerT) {
        if (isValuePresent()) {
            consumerT.accept(value);
            return;
        }
    }

    public void ifPresent(Consumer<? super T> consumerT, Consumer<? super R> consumerR) {
        ifPresent(consumerT);
        if (isDefvaluePresent()) {
            consumerR.accept(defvalue);
            return;
        }
    }

//    /**
//     * If a value is present, and the value matches the given predicate,
//     * return an {@code DefOptional} describing the value, otherwise return an
//     * empty {@code DefOptional}.
//     *
//     * @param predicate a predicate to apply to the value, if present
//     * @return an {@code DefOptional} describing the value of this {@code DefOptional}
//     * if a value is present and the value matches the given predicate,
//     * otherwise an empty {@code DefOptional}
//     * @throws NullPointerException if the predicate is null
//     */
//    public DefOptional<T> filter(Predicate<? super T> predicate) {
//        Objects.requireNonNull(predicate);
//        if (!isPresent())
//            return this;
//        else
//            return predicate.test(value) ? this : empty();
//    }
//

    public <U> DefOptional<U, R> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isValuePresent())
            return DefOptional.ofNullable(mapper.apply(value));
        else if (isDefvaluePresent()) {
            return DefOptional.ofNullable(null, defvalue);
        } else {
            return empty();
        }
    }

    public <U, TR> DefOptional<U, TR> map(Function<? super T, ? extends U> umapper, Function<? super R, ? extends TR> rmapper) {
        Objects.requireNonNull(umapper);
        Objects.requireNonNull(rmapper);
        if (isValuePresent()) {
            return DefOptional.ofNullable(umapper.apply(value));
        } else if (isDefvaluePresent()) {
            return DefOptional.ofNullable(null, rmapper.apply(defvalue));
        }
        return empty();
    }

    //    /**
//     * If a value is present, apply the provided {@code DefOptional}-bearing
//     * mapping function to it, return that result, otherwise return an empty
//     * {@code DefOptional}.  This method is similar to {@link #map(Function)},
//     * but the provided mapper is one whose result is already an {@code DefOptional},
//     * and if invoked, {@code flatMap} does not wrap it with an additional
//     * {@code DefOptional}.
//     *
//     * @param <U>    The type parameter to the {@code DefOptional} returned by
//     * @param mapper a mapping function to apply to the value, if present
//     *               the mapping function
//     * @return the result of applying an {@code DefOptional}-bearing mapping
//     * function to the value of this {@code DefOptional}, if a value is present,
//     * otherwise an empty {@code DefOptional}
//     * @throws NullPointerException if the mapping function is null or returns
//     *                              a null result
//     */
//    public <U> DefOptional<U> flatMap(Function<? super T, DefOptional<U>> mapper) {
//        Objects.requireNonNull(mapper);
//        if (!isPresent())
//            return empty();
//        else {
//            return Objects.requireNonNull(mapper.apply(value));
//        }
//    }
//
//    /**
//     * Return the value if present, otherwise return {@code other}.
//     *
//     * @param other the value to be returned if there is no value present, may
//     *              be null
//     * @return the value, if present, otherwise {@code other}
//     */
//    public T orElse(T other) {
//        return value != null ? value : other;
//    }
//
//    /**
//     * Return the value if present, otherwise invoke {@code other} and return
//     * the result of that invocation.
//     *
//     * @param other a {@code Supplier} whose result is returned if no value
//     *              is present
//     * @return the value if present otherwise the result of {@code other.get()}
//     * @throws NullPointerException if value is not present and {@code other} is
//     *                              null
//     */
//    public T orElseGet(Supplier<? extends T> other) {
//        return value != null ? value : other.get();
//    }
//
//    /**
//     * Return the contained value, if present, otherwise throw an exception
//     * to be created by the provided supplier.
//     *
//     * @param <X>               Type of the exception to be thrown
//     * @param exceptionSupplier The supplier which will return the exception to
//     *                          be thrown
//     * @return the present value
//     * @throws X                    if there is no value present
//     * @throws NullPointerException if no value is present and
//     *                              {@code exceptionSupplier} is null
//     * @apiNote A method reference to the exception constructor with an empty
//     * argument list can be used as the supplier. For example,
//     * {@code IllegalStateException::new}
//     */
//    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
//        if (value != null) {
//            return value;
//        } else {
//            throw exceptionSupplier.get();
//        }
//    }
//
//    /**
//     * Indicates whether some other object is "equal to" this DefOptional. The
//     * other object is considered equal if:
//     * <ul>
//     * <li>it is also an {@code DefOptional} and;
//     * <li>both instances have no value present or;
//     * <li>the present values are "equal to" each other via {@code equals()}.
//     * </ul>
//     *
//     * @param obj an object to be tested for equality
//     * @return {code true} if the other object is "equal to" this object
//     * otherwise {@code false}
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//
//        if (!(obj instanceof DefOptional)) {
//            return false;
//        }
//
//        DefOptional<?> other = (DefOptional<?>) obj;
//        return Objects.equals(value, other.value);
//    }
//
//    /**
//     * Returns the hash code value of the present value, if any, or 0 (zero) if
//     * no value is present.
//     *
//     * @return hash code value of the present value or 0 if no value is present
//     */
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(value);
//    }
//
//    /**
//     * Returns a non-empty string representation of this DefOptional suitable for
//     * debugging. The exact presentation format is unspecified and may vary
//     * between implementations and versions.
//     *
//     * @return the string representation of this instance
//     * @implSpec If a value is present the result must include its string
//     * representation in the result. Empty and present DefOptionals must be
//     * unambiguously differentiable.
//     */
//    @Override
//    public String toString() {
//        return value != null
//                ? String.format("DefOptional[%s]", value)
//                : "DefOptional.empty";
//    }
    public static void main(String[] args) {
        DefOptional.ofNullable(null, new Integer(1)).map(e ->
                e.toString(), v -> {
            System.out.println(v.byteValue());
            return v.toString();
        }).get()/*.ifdefPresent(e->{
            System.out.println(e.toString());
            System.out.println(e);
            System.out.println(e);
        },de->{
            System.out.println(de);
        })*/;
    }
}
