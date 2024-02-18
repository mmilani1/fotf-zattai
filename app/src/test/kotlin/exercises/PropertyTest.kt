package exercises

import io.kotest.assertions.assertSoftly
import io.kotest.common.runBlocking
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import org.junit.jupiter.api.Test
import io.kotest.property.forAll
import io.kotest.property.withAssumptions


class PropertyTest {

    @Test
    fun `zero identity`() {
        runBlocking {
            checkAll<Int> { x ->
                x + 0 shouldBeEqual x
            }
        }
    }

    @Test fun `commutative property`() {
        runBlocking {
            checkAll<Int,Int> { x, y ->
                x+y shouldBeEqual  y+x
            }
        }
    }

    @Test fun `associative property`() {
        runBlocking {
            checkAll<Int, Int, Int> { x, y, z ->
                assertSoftly {
                    x + y + z shouldBeEqual  x + (y + z)
                    x + y + z shouldBeEqual  (x + y) + z
                    x + y + z shouldBeEqual  (x + z) + y
                }
            }
        }
    }

    @Test fun `plus one property`() {
        runBlocking {
            checkAll(Arb.int(-100..100), Arb.int(0..1000)){ x, y ->
                withAssumptions(x < y) {
                    var cur = x
                    while(cur<y) {
                        cur += 1
                    }
                    cur shouldBeEqual y
                }
            }
        }
    }
}