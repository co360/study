package scratch;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BearingTest {

  @Test(expected = BearingOutOfRangeException.class)
  public void throwsOnNegativeNumber() {
    new Bearing(-1);
  }

  @Test(expected = BearingOutOfRangeException.class)
  public void throwsWhenBearingTooLarge() {
    new Bearing(Bearing.MAX + 1);
  }

  @Test
  public void answersValidBearing() {
    assertThat(new Bearing(Bearing.MAX).value(), equalTo(Bearing.MAX));
  }

  @Test
  public void answersAngleBetweenItAndAnotherBearing() {
    assertThat(new Bearing(15).angleBetween(new Bearing(12)), equalTo(3));
  }

  @Test
  public void angleBetweenIsNegativeWhenThisBearingSmaller() {
    assertThat(new Bearing(12).angleBetween(new Bearing(15)), equalTo(-3));
  }
}
