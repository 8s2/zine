package com.eightsidedsquare.zine.client.animation;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.util.Ease;
import net.minecraft.util.Mth;
import org.joml.Vector3fc;

/**
 * @author EightSidedSquare
 * @author Geckolib authors
 */
public class Interpolations {

    public static final AnimationChannel.Interpolation LINEAR = AnimationChannel.Interpolations.LINEAR;
    public static final AnimationChannel.Interpolation SPLINE = AnimationChannel.Interpolations.CATMULLROM;
    public static final InterpolationFactory STEP = steps -> easing(Interpolations.step(steps));
    public static final AnimationChannel.Interpolation EASE_IN_QUADRATIC = easing(Ease::inQuad);
    public static final AnimationChannel.Interpolation EASE_OUT_QUADRATIC = easing(Ease::outQuad);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_QUADRATIC = easing(Ease::inOutQuad);
    public static final AnimationChannel.Interpolation EASE_IN_CUBIC = easing(Ease::inCubic);
    public static final AnimationChannel.Interpolation EASE_OUT_CUBIC = easing(Ease::outCubic);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_CUBIC = easing(Ease::inOutCubic);
    public static final AnimationChannel.Interpolation EASE_IN_QUARTIC = easing(Ease::inQuart);
    public static final AnimationChannel.Interpolation EASE_OUT_QUARTIC = easing(Ease::outQuart);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_QUARTIC = easing(Ease::inOutQuart);
    public static final AnimationChannel.Interpolation EASE_IN_QUINTIC = easing(Ease::inQuint);
    public static final AnimationChannel.Interpolation EASE_OUT_QUINTIC = easing(Ease::outQuint);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_QUINTIC = easing(Ease::inOutQuint);
    public static final AnimationChannel.Interpolation EASE_IN_EXPO = easing(Ease::inExpo);
    public static final AnimationChannel.Interpolation EASE_OUT_EXPO = easing(Ease::outExpo);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_EXPO = easing(Ease::inOutExpo);
    public static final AnimationChannel.Interpolation EASE_IN_CIRCLE = easing(Ease::inCirc);
    public static final AnimationChannel.Interpolation EASE_OUT_CIRCLE = easing(Ease::outCirc);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_CIRCLE = easing(Ease::inOutCirc);
    public static final InterpolationFactory EASE_IN_BACK = overshoot -> easing(easeIn(Interpolations.back(overshoot)));
    public static final InterpolationFactory EASE_OUT_BACK = overshoot -> easing(easeOut(Interpolations.back(overshoot)));
    public static final InterpolationFactory EASE_IN_OUT_BACK = overshoot -> easing(easeInOut(Interpolations.back(overshoot)));
    public static final InterpolationFactory EASE_IN_BOUNCE = bounciness -> easing(easeIn(Interpolations.bounce(bounciness)));
    public static final InterpolationFactory EASE_OUT_BOUNCE = bounciness -> easing(easeOut(Interpolations.bounce(bounciness)));
    public static final InterpolationFactory EASE_IN_OUT_BOUNCE = bounciness -> easing(easeInOut(Interpolations.bounce(bounciness)));
    public static final AnimationChannel.Interpolation EASE_IN_SINE = easing(Ease::inSine);
    public static final AnimationChannel.Interpolation EASE_OUT_SINE = easing(Ease::outSine);
    public static final AnimationChannel.Interpolation EASE_IN_OUT_SINE = easing(Ease::inOutSine);
    public static final InterpolationFactory EASE_IN_ELASTIC = bounciness -> easing(easeIn(Interpolations.elastic(bounciness)));
    public static final InterpolationFactory EASE_OUT_ELASTIC = bounciness -> easing(easeOut(Interpolations.elastic(bounciness)));
    public static final InterpolationFactory EASE_IN_OUT_ELASTIC = bounciness -> easing(easeInOut(Interpolations.elastic(bounciness)));

    private static AnimationChannel.Interpolation easing(FloatUnaryOperator easing) {
        return (output, delta, keyframes, start, end, scale) -> {
            Vector3fc preTarget = keyframes[start].preTarget();
            Vector3fc postTarget = keyframes[end].postTarget();

            double eased = delta <= 0 ? 0 : delta >= 1 ? 1 : easing.apply(delta);
            return output.set(
                    Mth.lerp(eased, preTarget.x(), postTarget.x()) * scale,
                    Mth.lerp(eased, preTarget.y(), postTarget.y()) * scale,
                    Mth.lerp(eased, preTarget.z(), postTarget.z()) * scale
            );
        };
    }

    /**
     * Returns an easing function running forward in time
     */
    static FloatUnaryOperator easeIn(FloatUnaryOperator function) {
        return function;
    }

    /**
     * Returns an easing function running backwards in time
     */
    static FloatUnaryOperator easeOut(FloatUnaryOperator function) {
        return time -> 1 - function.apply(1 - time);
    }

    /**
     * Returns an easing function that runs equally both forwards and backwards in time based on the halfway point, generating a symmetrical curve
     */
    static FloatUnaryOperator easeInOut(FloatUnaryOperator function) {
        return time -> {
            if (time < 0.5d)
                return function.apply(time * 2f) / 2f;

            return 1 - function.apply((1 - time) * 2f) / 2f;
        };
    }

    // ---> Easing Curve Functions <--- //

    /**
     * An elastic function, equivalent to an oscillating curve
     * <p>
     * <i>n</i> defines the elasticity of the output
     * <p>
     * {@code f(t) = 1 - (cos(t * π) / 2))^3 * cos(t * n * π)}
     * <p>
     * <a href="http://easings.net/#easeInElastic">Easings.net#easeInElastic</a>
     */
    static FloatUnaryOperator elastic(Float n) {
        double n2 = n == null ? 1 : n;
        return t -> 1 - Mth.cube(Mth.cos(t * Math.PI / 2f)) * Mth.cos(t * n2 * Math.PI);
    }

    /**
     * A bouncing function, equivalent to a bouncing ball curve
     * <p>
     * <i>n</i> defines the bounciness of the output
     * <p>
     * Thanks to <b>Waterded#6455</b> for making the bounce adjustable, and <b>GiantLuigi4#6616</b> for additional cleanup
     * <p>
     * <a href="http://easings.net/#easeInBounce">Easings.net#easeInBounce</a>
     */
    static FloatUnaryOperator bounce(Float n) {
        final float n2 = n == null ? 0.5f : n;

        FloatUnaryOperator one = x -> 121f / 16f * x * x;
        FloatUnaryOperator two = x -> 121f / 4f * n2 * Mth.square(x - 6f / 11f) + 1 - n2;
        FloatUnaryOperator three = x -> 121 * n2 * n2 * Mth.square(x - 9f / 11f) + 1 - n2 * n2;
        FloatUnaryOperator four = x -> 484 * n2 * n2 * n2 * Mth.square(x - 10.5f / 11f) + 1 - n2 * n2 * n2;

        return t -> Math.min(Math.min(one.apply(t), two.apply(t)), Math.min(three.apply(t), four.apply(t)));
    }

    /**
     * A negative elastic function, equivalent to inverting briefly before increasing
     * <p>
     * <code>f(t) = t^2 * ((n * 1.70158 + 1) * t - n * 1.70158)</code>
     * <p>
     * <a href="https://easings.net/#easeInBack">Easings.net#easeInBack</a>
     */
    static FloatUnaryOperator back(Float n) {
        final float n2 = n == null ? 1.70158f : n * 1.70158f;

        return t -> t * t * ((n2 + 1) * t - n2);
    }

    // The MIT license notice below applies to the function step
    /**
     * The MIT License (MIT)
     *<br><br>
     * Copyright (c) 2015 Boris Chumichev
     *<br><br>
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *<br><br>
     * The above copyright notice and this permission notice shall be included in
     * all copies or substantial portions of the Software.
     *<br><br>
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     * <br><br>
     * Returns a stepped value based on the nearest step to the input value.<br>
     * The size (grade) of the steps depends on the provided value of {@code n}
     **/
    static FloatUnaryOperator step(Float n) {
        float n2 = n == null ? 2 : n;

        if (n2 < 2)
            throw new IllegalArgumentException("Steps must be >= 2, got: " + n2);

        final int steps = (int)n2;

        return t -> {
            float result = 0;

            if (t < 0)
                return result;

            float stepLength = (1 / (float)steps);

            if (t > (result = (steps - 1) * stepLength))
                return result;

            int testIndex;
            int leftBorderIndex = 0;
            int rightBorderIndex = steps - 1;

            while (rightBorderIndex - leftBorderIndex != 1) {
                testIndex = leftBorderIndex + (rightBorderIndex - leftBorderIndex) / 2;

                if (t >= testIndex * stepLength) {
                    leftBorderIndex = testIndex;
                }
                else {
                    rightBorderIndex = testIndex;
                }
            }

            return leftBorderIndex * stepLength;
        };
    }

    public interface InterpolationFactory {
        AnimationChannel.Interpolation configure(float value);
    }

}
