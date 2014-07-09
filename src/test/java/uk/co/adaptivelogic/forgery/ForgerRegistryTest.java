package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import uk.co.adaptivelogic.forgery.forger.RandomLongForger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ForgerRegistryTest {
	private ForgerRegistry registry;

	@Before
	public void setUp() {
		registry = new InMemoryForgerRegistry();
	}

	@Test
	public void shouldFindTypeForger() {
		// Given
		Forger<Long> expected = new RandomLongForger();
		registry.register(expected);

		// When
		Optional<Forger<Long>> actual = registry.lookup(Long.class);

		// Then
		assertThat(actual.get(), is(expected));
	}

	@Test
	public void shouldNotFindTypeForgerForInvalidType() {
		// When
		Optional<Forger<String>> actual = registry.lookup(String.class);

		// Then
		assertThat(actual.isPresent(), is(false));
	}

	@Test
	public void shouldFindPropertyForgerForProperty() {
		// Given
		Forger<String> expected = new FirstNameStringForger();
		registry.register(expected);

		// When
		Optional<Forger<String>> actual = registry.lookup(String.class, "firstName");

		// Then
		assertThat(actual.get(), is(expected));
	}

	@Test
	public void shouldNotFindPropertyForgerForInvalidProperty() {
		// Given
		Forger<String> expected = new FirstNameStringForger();
		registry.register(expected);

		// When
		Optional<Forger<String>> actual = registry.lookup(String.class, "lastName");

		// Then
		assertThat(actual.isPresent(), is(false));
	}
}