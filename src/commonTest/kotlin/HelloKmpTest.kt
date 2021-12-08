import kotlin.test.Test
import kotlin.test.assertTrue

class HelloKmpTest {

    @Test
    fun fetchMessage() {
        assertTrue(HelloKmp.fetchMessage().startsWith("Hello"))
        assertTrue(HelloKmp.fetchMessage().endsWith("World!"))
    }
}