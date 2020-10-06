			//import static org.junit.jupiter.api.Assertions.*;
			//
			//import org.junit.jupiter.api.BeforeEach;
			//import org.junit.jupiter.api.Test;
			//
			//import org.junit.jupiter.api.DisplayName;
			//
			//import org.junit.jupiter.params.ParameterizedTest;
			//import org.junit.jupiter.params.provider.ValueSource;
			//
			//class GuessTest {
			//
			//	Server obj;
			//	Server.ClientThread obj1;
			//	Server.TheServer tmp;
			//	gameinfo game;
			//
			//	@BeforeEach
			//	void init(){
			//		obj = new Server();
			//		game = new gameinfo();
			//	}
			//	@Test
			//	void test1()
			//	{
			//		assertEquals(game.getClass().getName(), "gameinfo", "Not initializing correctly");
			//	}
			//
			//	@Test
			//	void test2()
			//	{
			//		assertEquals(obj.getClass().getName(), "Server", "Not initializing correctly");
			//	}
			//}

			import static org.junit.jupiter.api.Assertions.*;

			import org.junit.jupiter.api.BeforeEach;
			import org.junit.jupiter.api.Test;

			import org.junit.jupiter.api.DisplayName;

			import org.junit.jupiter.params.ParameterizedTest;
			import org.junit.jupiter.params.provider.ValueSource;

			class GuessTest {

				Server svr = new Server();
				Server.ClientThread obj = svr.new ClientThread();
				@BeforeEach
				public void instance()
				{
					svr = new Server();
					Server.ClientThread obj = svr.new ClientThread();
				}

				@Test
				public void test_animal()
				{
					assertEquals(true,obj.animals(true),"not true");

				}

				@Test
				public void test_country()
				{
					assertEquals(true,obj.country(true),"not true");

				}


				@Test
				public void test_sports()
				{
					assertEquals(true,obj.sports(true),"not true");

				}




				@Test
				public void test_countriesarray()
				{
					assertEquals(3,svr.contains_Countries_name.length,"not true");

				}



				@Test
				public void test_sportarray()
				{
					assertEquals(3,svr.contains_sports_name.length,"not true");

				}


				@Test
				public void test_animalarray()
				{
					assertEquals(3,svr.contains_animal_name.length,"not true");

				}



				@Test
				public void test_countriesarray1()
				{
					assertEquals(1,svr.contains_Countries_name.length-2,"not true");

				}



				@Test
				public void test_sportarray1()
				{
					assertEquals(2,svr.contains_sports_name.length-1,"not true");

				}


				@Test
				public void test_animalarray1()
				{
					assertEquals(0,svr.contains_animal_name.length-3,"not true");

				}




			}