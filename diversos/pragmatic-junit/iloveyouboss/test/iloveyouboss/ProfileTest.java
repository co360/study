package iloveyouboss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProfileTest {

  private Profile  profile;
  private Criteria criteria;

  int[] ids(final Collection<Answer> answers) {
    return answers.stream()
                  .mapToInt(a -> a.getQuestion()
                                  .getId())
                  .toArray();
  }

  @Before
  public void createProfile() {
    this.profile = new Profile("Bull Hockey, Inc.");
  }

  @Before
  public void createCriteria() {
    this.criteria = new Criteria();
  }

  @Test
  public void findsAnswersBasedOnPredicate() {
    this.profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
    this.profile.add(
        new Answer(new PercentileQuestion(2, "2", new String[] {}), 0));
    this.profile.add(
        new Answer(new PercentileQuestion(3, "3", new String[] {}), 0));

    final List<Answer> answers = this.profile.find(a -> a.getQuestion()
                                                         .getClass() == PercentileQuestion.class);

    assertThat(ids(answers), equalTo(new int[] { 2, 3 }));

    final List<Answer> answersComplement = this.profile.find(
        a -> a.getQuestion()
              .getClass() != PercentileQuestion.class);

    final List<Answer> allAnswers = new ArrayList<Answer>();
    allAnswers.addAll(answersComplement);
    allAnswers.addAll(answers);

    assertThat(ids(allAnswers), equalTo(new int[] { 1, 2, 3 }));
  }

  @Test
  public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
    final BooleanQuestion question = new BooleanQuestion(1, "Got bonuses?");
    this.profile.add(new Answer(question, Bool.FALSE));
    this.criteria.add(
        new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

    final boolean matches = this.profile.matches(this.criteria);

    assertFalse(matches);
  }

  @Test
  public void matchAnswersTrueForAnyDontCareCriteria() {
    final BooleanQuestion question = new BooleanQuestion(1, "Got bonuses?");
    this.profile.add(new Answer(question, Bool.FALSE));
    this.criteria.add(
        new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

    final boolean matches = this.profile.matches(this.criteria);

    assertTrue(matches);
  }

  @Test
  public void findAnswers() {
    int dataSize = 5000;
    for (int i = 0; i < dataSize; i++) {
      profile.add(
          new Answer(new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
    }
    profile.add(new Answer(new PercentileQuestion(dataSize,
        String.valueOf(dataSize), new String[] {}), 0));
    int numberOfTimes = 1000;
    long elapseMs = run(numberOfTimes, () -> profile.find(a -> a.getQuestion()
                                                                .getClass() == PercentileQuestion.class));

    assertTrue(elapseMs < 1000);
  }

  private long run(int times, Runnable func) {
    long start = System.nanoTime();
    for (int i = 0; i < times; i++) {
      func.run();
    }
    long stop = System.nanoTime();
    return (stop - start) / 1_000_000;
  }
}
