package com.gemserk.animation4j.componentsengine.properties;

import com.gemserk.animation4j.componentsengine.SystemTimeProvider;
import com.gemserk.animation4j.componentsengine.TimeProvider;
import com.gemserk.animation4j.values.InterpolatedValue;
import com.gemserk.componentsengine.properties.Property;

/**
 * Provides an implementation of Property<T> which interpolates between the current value and the value you set when you call set(...) method.
 * @param <T> - The type of the property value. 
 * @author acoppes
 */
public class InterpolatedProperty<T> implements Property<T> {

	private final TimeProvider timeProvider;

	private final float speed;

	private final InterpolatedValue<T> interpolatedValue;

	private long lastTime = 0;

	public InterpolatedProperty(InterpolatedValue<T> interpolatedValue, float speed, TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
		this.speed = speed;
		this.interpolatedValue = interpolatedValue;
		lastTime = timeProvider.getTime();
	}
	
	public InterpolatedProperty(InterpolatedValue<T> interpolatedValue, float speed) {
		this(interpolatedValue, speed, new SystemTimeProvider());
	}

	@Override
	public T get() {
		long currentTime = timeProvider.getTime();
		float time = ((float) (currentTime - lastTime)) * speed;
		interpolatedValue.setAlpha(time);
		return interpolatedValue.getInterpolatedValue();
	}

	@Override
	public void set(T value) {
		lastTime = timeProvider.getTime();
		interpolatedValue.setA(interpolatedValue.getInterpolatedValue());
		interpolatedValue.setB(value);
		interpolatedValue.setAlpha(0f);
	}
}