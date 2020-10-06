import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuessTest {

	gameinfo obj1;

	@BeforeEach
	void init(){
		obj1 = new gameinfo();
	}
	@Test
	void test1()
	{
		assertEquals(obj1.getClass().getName(), "gameinfo", "Not initialising class correctly");
	}

//	@Test
//	void test2()
//	{
//		assertEquals(obj1., "gameinfo", "Not initialising class correctly");
//	}
}
