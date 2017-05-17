import org.junit.Test;

public class VeryBigIntegerTest {
    @Test
    public void testConstructor1() {
        String str = "1234567890987654321";
        VeryBigInteger i = new VeryBigInteger(str);
        assert str.equals(i.toString());
    }

    @Test
    public void testConstructor2() {
        VeryBigInteger i = new VeryBigInteger(123456789);
        assert "123456789".equals(i.toString());
    }

    @Test
    public void testEquals() {
        VeryBigInteger i1 = new VeryBigInteger("987654321");
        VeryBigInteger i2 = new VeryBigInteger(987654321);
        assert i1.compareTo(i2) == 0;
    }

    @Test
    public void testCompareLess() {
        VeryBigInteger i1 = new VeryBigInteger("987654321");
        VeryBigInteger i2 = new VeryBigInteger(987655321);
        assert i1.compareTo(i2) < 0;
    }

    @Test
    public void testCompareBigger() {
        VeryBigInteger i1 = new VeryBigInteger("987654322");
        VeryBigInteger i2 = new VeryBigInteger(987654321);
        assert i1.compareTo(i2) > 0;
    }

    @Test
    public void testCompareEqual() {
        VeryBigInteger i1 = new VeryBigInteger("987654321");
        VeryBigInteger i2 = new VeryBigInteger(987654321);
        assert i1.compareTo(i2) == 0;
    }

    @Test
    public void testAdd() {
        int a = 4352;
        int b = 353277;
        Integer c = a + b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.add(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void testAddWithPerenos() {
        Integer c = 1000000000;
        int a = 3345898;
        int b = c - a;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.add(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void testMinus() {
        int a = 4352987;
        int b = 353277;
        Integer c = a - b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.minus(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void testMinusWithLeadingZeros() {
        int a = 1234567;
        int b = 1234566;
        Integer c = a - b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.minus(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void tesMultiplication() {
        int a = 2345;
        int b = 453255;
        Integer c = a * b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.multiply(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void tesMultiplicationZero() {
        int a = 23459876;
        int b = 0;
        Integer c = 0;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.multiply(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void testDivision() {
        int a = 23459876;
        int b = 323454;
        Integer c = a / b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.divide(i2);
        assert c.toString().equals(i1.toString());
    }

    @Test
    public void testRemainder() {
        int a = 1024;
        int b = 4;
        Integer c = a % b;
        VeryBigInteger i1 = new VeryBigInteger(a);
        VeryBigInteger i2 = new VeryBigInteger(b);
        i1.remainder(i2);
        assert c.toString().equals(i1.toString());
    }
}
